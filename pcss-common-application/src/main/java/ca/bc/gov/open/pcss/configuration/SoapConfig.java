package ca.bc.gov.open.pcss.configuration;

import ca.bc.gov.open.pcss.models.serializers.InstantDeserializer;
import ca.bc.gov.open.pcss.models.serializers.InstantSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import jakarta.xml.soap.SOAPMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

@EnableWs
@Configuration
@Slf4j
public class SoapConfig extends WsConfigurerAdapter {
    @Value("${pcss.username}")
    private String username;

    @Value("${pcss.password}")
    private String password;

    @Value("${pcss.ords-read-timeout}")
    private String ordsReadTimeout;

    public static final String SOAP_NAMESPACE = "http://courts.gov.bc.ca/xml/ns/pcss/common/v1";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/common/*");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        var restTemplate =
                restTemplateBuilder
                        .basicAuthentication(username, password)
                        .setReadTimeout(Duration.ofSeconds(Integer.parseInt(ordsReadTimeout)))
                        .build();
        return restTemplate;
    }

    @Bean(name = "restTemplateOracle")
    public RestTemplate restTemplateOracle(RestTemplateBuilder restTemplateBuilder) {
        var restTemplate =
                restTemplateBuilder
                        .setReadTimeout(Duration.ofSeconds(Integer.parseInt(ordsReadTimeout)))
                        .build();
        return restTemplate;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Instant.class, new InstantDeserializer());
        module.addSerializer(Instant.class, new InstantSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(SOAPMessage.WRITE_XML_DECLARATION, "true");
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setMessageProperties(props);
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return messageFactory;
    }

    @Bean(name = "JusticePCSSCommon.wsProvider:pcssCommon")
    public Wsdl11Definition JusticePCSSCommonWSDL() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("xsdSchemas/pcss-common.wsdl"));
        return wsdl11Definition;
    }

    @Bean(name = "JusticePCSSCommon.wsProvider:pcssCommonSecure")
    public Wsdl11Definition JusticeSecurePCSSCommonWSDL() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("xsdSchemas/pcss-common-secure.wsdl"));
        return wsdl11Definition;
    }

    @Bean(name = "JusticePCSSCommon.wsProvider:pcssReport")
    public Wsdl11Definition JusticePCSSReportsWSDL() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("xsdSchemas/pcss-reports.wsdl"));
        return wsdl11Definition;
    }
}
