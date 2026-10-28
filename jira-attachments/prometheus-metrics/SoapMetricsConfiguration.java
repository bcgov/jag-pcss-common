package ca.bc.gov.open.pcss.observability;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;

@Configuration
public class SoapMetricsConfiguration extends WsConfigurerAdapter {

    private final SoapMetricsInterceptor soapMetricsInterceptor;

    public SoapMetricsConfiguration(SoapMetricsInterceptor soapMetricsInterceptor) {
        this.soapMetricsInterceptor = soapMetricsInterceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(soapMetricsInterceptor);
    }
}
