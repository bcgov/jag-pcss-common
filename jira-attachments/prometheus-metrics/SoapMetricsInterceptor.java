package ca.bc.gov.open.pcss.observability;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.w3c.dom.NodeList;

@Component
public final class SoapMetricsInterceptor implements EndpointInterceptor {

    private static final String TIMER_STATE =
            SoapMetricsInterceptor.class.getName() + ".timerState";
    private static final String METRIC_NAME = "pcss.soap.server.requests";
    private static final String UNSPECIFIED = "UNSPECIFIED";

    private final MeterRegistry registry;

    public SoapMetricsInterceptor(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public boolean handleRequest(MessageContext context, Object endpoint) {
        context.setProperty(
                TIMER_STATE,
                new TimerState(
                        Timer.start(registry),
                        operationName(endpoint),
                        callerApplication(context)));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext context, Object endpoint) {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext context, Object endpoint) {
        return true;
    }

    @Override
    public void afterCompletion(
            MessageContext context, Object endpoint, Exception exception) {

        TimerState state = (TimerState) context.getProperty(TIMER_STATE);
        if (state == null) {
            return;
        }

        boolean error = exception != null || hasSoapFault(context);

        state.sample()
                .stop(
                        Timer.builder(METRIC_NAME)
                                .description("SOAP server request duration")
                                .tag("operation", state.operation())
                                .tag("caller_application", state.callerApplication())
                                .tag("outcome", error ? "ERROR" : "SUCCESS")
                                .register(registry));
    }

    private static String operationName(Object endpoint) {
        if (!(endpoint instanceof MethodEndpoint methodEndpoint)) {
            return "unmapped";
        }

        PayloadRoot payloadRoot =
                AnnotatedElementUtils.findMergedAnnotation(
                        methodEndpoint.getMethod(), PayloadRoot.class);

        if (payloadRoot != null && !payloadRoot.localPart().isBlank()) {
            return payloadRoot.localPart();
        }

        return methodEndpoint.getMethod().getName();
    }

    private static String callerApplication(MessageContext context) {
        if (!(context.getRequest() instanceof SaajSoapMessage message)) {
            return UNSPECIFIED;
        }

        try {
            SOAPBody body = message.getSaajMessage().getSOAPBody();
            if (body == null) {
                return UNSPECIFIED;
            }

            NodeList nodes = body.getElementsByTagNameNS("*", "ApplicationCd");
            Set<String> values = new HashSet<>();

            for (int index = 0; index < nodes.getLength(); index++) {
                String value = nodes.item(index).getTextContent();
                if (value != null && !value.isBlank()) {
                    values.add(normalizeApplicationCode(value));
                }
            }

            // Multiple identical values are acceptable. Missing or conflicting
            // values are reported as UNSPECIFIED.
            return values.size() == 1 ? values.iterator().next() : UNSPECIFIED;
        } catch (RuntimeException | SOAPException exception) {
            return UNSPECIFIED;
        }
    }

    private static String normalizeApplicationCode(String value) {
        return switch (value.trim().toUpperCase(Locale.ROOT)) {
            case "PCSS" -> "PCSS";
            case "CCSS" -> "CCSS";
            default -> "OTHER";
        };
    }

    private static boolean hasSoapFault(MessageContext context) {
        return context.hasResponse()
                && context.getResponse() instanceof SoapMessage message
                && message.getSoapBody().hasFault();
    }

    private record TimerState(
            Timer.Sample sample, String operation, String callerApplication) {}
}
