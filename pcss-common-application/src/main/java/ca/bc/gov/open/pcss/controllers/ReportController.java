package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovRequest;
import ca.bc.gov.open.wsdl.pcss.one.GetOperationReportRequest;
import ca.bc.gov.open.wsdl.pcss.secure.two.*;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
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
public class ReportController {

    @Value("${pcss.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getJustinAdobeReport")
    @ResponsePayload
    public GetJustinAdobeReportResponse getJustinAdobeReport(
            @RequestPayload GetJustinAdobeReport search) throws JsonProcessingException {

        var inner =
                search.getGetJustinAdobeReportRequest() != null
                        ? search.getGetJustinAdobeReportRequest()
                        : new GetJustinReportAdobeRequest();

        Map<String, String> paramMap = objectMapper.convertValue(inner, Map.class);

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("requestAgenId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", inner.getRequestDtm())
                        .queryParam("formNm", inner.getFormNm())
                        .queryParam("printYn", inner.getPrintYn());

        for (int i = 1; i < 16; i++) {
            builder.queryParam("param" + i, paramMap.get("Param" + i));
        }

        try {
            HttpEntity<GetJustinReportAdobeResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetJustinReportAdobeResponse.class);

            var out = new GetJustinAdobeReportResponse();
            out.setGetJustinAdobeReportResponse(resp.getBody());

            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "getJustinAdobeReport", inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getJustinReport")
    @ResponsePayload
    public GetJustinReportResponse getJustinReport(@RequestPayload GetJustinReport search)
            throws JsonProcessingException {

        var inner =
                search.getGetJustinReportRequest() != null
                                && search.getGetJustinReportRequest().getGetJustinReportRequest()
                                        != null
                        ? search.getGetJustinReportRequest().getGetJustinReportRequest()
                        : new ca.bc.gov.open.wsdl.pcss.secure.one.GetJustinReportRequest();

        //    TODO Add parameter list handling
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("requestAgenId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", inner.getRequestDtm())
                        .queryParam("reportName", inner.getReportName());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetJustinReportResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.secure.one.GetJustinReportResponse.class);

            var out = new GetJustinReportResponse();
            var one = new GetJustinReportResponse2();
            one.setGetJustinReportResponse(resp.getBody());
            out.setGetJustinReportResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "getJustinReport", inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getOperationReport")
    @ResponsePayload
    public GetOperationReportResponse getOperationReport(@RequestPayload GetOperationReport search)
            throws JsonProcessingException {
        var inner =
                search.getGetOperationReportRequest() != null
                                && search.getGetOperationReportRequest()
                                                .getGetOperationReportRequest()
                                        != null
                        ? search.getGetOperationReportRequest().getGetOperationReportRequest()
                        : new GetOperationReportRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("requestAgenId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", inner.getRequestDtm());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.one.GetOperationReportResponse.class);

            var out = new GetOperationReportResponse();
            var one = new GetOperationReportResponse2();
            one.setGetOperationReportResponse(resp.getBody());
            out.setGetOperationReportResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "getJustinReport", inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getOperationReportLov")
    @ResponsePayload
    public GetOperationReportLovResponse getOperationReportLov(
            @RequestPayload GetOperationReportLov search) throws JsonProcessingException {

        var inner =
                search.getGetOperationReportLovRequest() != null
                                && search.getGetOperationReportLovRequest()
                                                .getGetOperationReportLovRequest()
                                        != null
                        ? search.getGetOperationReportLovRequest().getGetOperationReportLovRequest()
                        : new GetOperationReportLovRequest();

        // TODO figure out how to send parameters
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("requestAgenId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", inner.getRequestDtm());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovResponse.class);

            var out = new GetOperationReportLovResponse();
            var one = new GetOperationReportLovResponse2();
            one.setGetOperationReportLovResponse(resp.getBody());
            out.setGetOperationReportLovResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "getJustinReport", inner)));
            throw new ORDSException();
        }
    }
}
