package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Slf4j
public class AppearanceController {

    @Value("${pcss.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AppearanceController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setAppearanceUpdate")
    @ResponsePayload
    public SetAppearanceUpdateResponse setAppearanceUpdate(
            @RequestPayload SetAppearanceUpdate search) throws JsonProcessingException {
        var inner =
                search.getSetAppearanceUpdateRequest() != null
                                && search.getSetAppearanceUpdateRequest()
                                                .getSetAppearanceUpdateRequest()
                                        != null
                        ? search.getSetAppearanceUpdateRequest().getSetAppearanceUpdateRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateRequest> body =
                new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.PUT,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateResponse.class);

            var out = new SetAppearanceUpdateResponse();
            var one = new SetAppearanceUpdateResponse2();
            one.setSetAppearanceUpdateResponse(resp.getBody());
            out.setSetAppearanceUpdateResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setAppearanceUpdate",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setAppearanceMove")
    @ResponsePayload
    public SetAppearanceMoveResponse setAppearanceMove(@RequestPayload SetAppearanceMove search)
            throws JsonProcessingException {
        var inner =
                search.getSetAppearanceMoveRequest() != null
                                && search.getSetAppearanceMoveRequest()
                                                .getSetAppearanceMoveRequest()
                                        != null
                        ? search.getSetAppearanceMoveRequest().getSetAppearanceMoveRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "court-list-move");

        HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveRequest> body =
                new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.PUT,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveResponse.class);

            var out = new SetAppearanceMoveResponse();
            var one = new SetAppearanceMoveResponse2();
            one.setSetAppearanceMoveResponse(resp.getBody());
            out.setSetAppearanceMoveResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setAppearanceMove",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setAppearanceStatus")
    @ResponsePayload
    public SetAppearanceStatusResponse setAppearanceStatus(
            @RequestPayload SetAppearanceStatus search) throws JsonProcessingException {
        var inner =
                search.getSetAppearanceStatusRequest() != null
                                && search.getSetAppearanceStatusRequest()
                                                .getSetAppearanceStatusRequest()
                                        != null
                        ? search.getSetAppearanceStatusRequest().getSetAppearanceStatusRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance-status");

        HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusRequest> body =
                new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusResponse.class);

            var out = new SetAppearanceStatusResponse();
            var one = new SetAppearanceStatusResponse2();
            one.setSetAppearanceStatusResponse(resp.getBody());
            out.setSetAppearanceStatusResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setAppearanceStatus",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
