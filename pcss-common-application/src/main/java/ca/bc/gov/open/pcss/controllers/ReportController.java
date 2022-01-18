package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.BadRequestException;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovRequest;
import ca.bc.gov.open.wsdl.pcss.one.GetOperationReportRequest;
import ca.bc.gov.open.wsdl.pcss.report.two.*;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Base64Utils;
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
    private String ords_host = "https://127.0.0.1/";

    @Value("${pcss.oracle_host}")
    private String oracle_host = "http://orafr-1.dev.ag.bcgov:8080/reports/rwservlet";

    @Value("${pcss.oracle_name}")
    private String oracleServerName;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(
            namespace = "http://reeks.bcgov/JusticePCSSCommon.wsProvider:pcssReport",
            localPart = "getJustinAdobeReport")
    @ResponsePayload
    public GetJustinAdobeReportResponse getJustinAdobeReport(
            @RequestPayload GetJustinAdobeReport search) throws JsonProcessingException {

        var inner =
                search.getGetJustinAdobeReportRequest() != null
                        ? search.getGetJustinAdobeReportRequest()
                        : new GetJustinReportAdobeRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ords_host + "adobe-report");

        var body = new HttpEntity<>(inner, new HttpHeaders());
        try {
            HttpEntity<Map> resp =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.POST, body, Map.class);

            HttpEntity<byte[]> resp2 =
                    restTemplate.exchange(
                            (String) resp.getBody().get("url"),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            byte[].class);

            String bs64 =
                    resp2.getBody() != null ? Base64Utils.encodeToString(resp2.getBody()) : "";

            var out = new GetJustinAdobeReportResponse();

            var one = new ca.bc.gov.open.wsdl.pcss.report.two.GetJustinReportAdobeResponse();
            one.setReportContent(bs64);
            one.setResponseCd("0");

            out.setGetJustinAdobeReportResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getJustinAdobeReport",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(
            namespace = "http://reeks.bcgov/JusticePCSSCommon.wsProvider:pcssReport",
            localPart = "getJustinReport")
    @ResponsePayload
    public GetJustinReportResponse getJustinReport(@RequestPayload @Valid GetJustinReport search)
            throws JsonProcessingException, BadRequestException {

        var inner =
                search.getGetJustinReportRequest() != null
                                && search.getGetJustinReportRequest().getGetJustinReportRequest()
                                        != null
                        ? search.getGetJustinReportRequest().getGetJustinReportRequest()
                        : new ca.bc.gov.open.wsdl.pcss.report.one.GetJustinReportRequest();

        if (inner.getReportName() == null || inner.getReportName().isBlank()) {
            throw new BadRequestException();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(oracle_host);

        builder.queryParam("cmdKey", "pcss_" + inner.getReportName().toLowerCase());
        builder.queryParam("server", oracleServerName);

        for (var param : inner.getParameters()) {
            builder.queryParam(param.getParmNm(), param.getParmValue());
        }
        try {
            HttpEntity<String> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            String.class);

            String b64EncodedReport =
                    Base64.getEncoder()
                            .encodeToString(resp.getBody().getBytes(StandardCharsets.UTF_8));

            var out = new GetJustinReportResponse();
            var one = new GetJustinReportResponse2();
            var two = new ca.bc.gov.open.wsdl.pcss.report.one.GetJustinReportResponse();
            two.setReportContent(b64EncodedReport);
            two.setResponseCd("0");
            out.setGetJustinReportResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getJustinReport",
                                    ex.getMessage(),
                                    inner)));
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
                UriComponentsBuilder.fromHttpUrl(ords_host + "operation-report")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
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
                                    "Error received from ORDS",
                                    "getOperationReport",
                                    ex.getMessage(),
                                    inner)));
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

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(ords_host + "operation-report-lov");

        var body = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
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
                                    "Error received from ORDS",
                                    "getOperationReportLov",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
