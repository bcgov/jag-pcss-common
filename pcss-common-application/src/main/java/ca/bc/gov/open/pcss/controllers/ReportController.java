package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.BadRequestException;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.pcss.models.RequestSuccessLog;
import ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovRequest;
import ca.bc.gov.open.wsdl.pcss.one.GetOperationReportRequest;
import ca.bc.gov.open.wsdl.pcss.report.two.*;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Locale;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
    private String ordsHost = "https://127.0.0.1/";

    @Value("${pcss.oracle-host}")
    private String oracleHost = "https://127.0.0.1/";

    @Value("${pcss.adobe-host}")
    private String adobeHost = "https://127.0.0.1/";

    @Value("${pcss.oracle-name}")
    private String oracleServerName;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(
            namespace = "http://courts.gov.bc.ca/xml/ns/pcss/report/v1",
            localPart = "getJustinAdobeReport")
    @ResponsePayload
    public GetJustinAdobeReportResponse getJustinAdobeReport(
            @RequestPayload GetJustinAdobeReport search) throws JsonProcessingException {

        var inner =
                search.getGetJustinAdobeReportRequest() != null
                        ? search.getGetJustinAdobeReportRequest()
                        : new GetJustinReportAdobeRequest();

        inner.setFormNm(
                inner.getFormNm() != null ? inner.getFormNm().toUpperCase(Locale.ROOT) : "");
        inner.setPrintYn(
                inner.getPrintYn() != null ? inner.getPrintYn().toUpperCase(Locale.ROOT) : "");

        inner.setParam1(
                inner.getParam1() != null ? inner.getParam1().toUpperCase(Locale.ROOT) : "");
        inner.setParam2(
                inner.getParam2() != null ? inner.getParam2().toUpperCase(Locale.ROOT) : "");
        inner.setParam3(
                inner.getParam3() != null ? inner.getParam3().toUpperCase(Locale.ROOT) : "");
        inner.setParam4(
                inner.getParam4() != null ? inner.getParam4().toUpperCase(Locale.ROOT) : "");
        inner.setParam5(
                inner.getParam5() != null ? inner.getParam5().toUpperCase(Locale.ROOT) : "");
        inner.setParam6(
                inner.getParam6() != null ? inner.getParam6().toUpperCase(Locale.ROOT) : "");
        inner.setParam7(
                inner.getParam7() != null ? inner.getParam7().toUpperCase(Locale.ROOT) : "");
        inner.setParam8(
                inner.getParam8() != null ? inner.getParam8().toUpperCase(Locale.ROOT) : "");
        inner.setParam9(
                inner.getParam9() != null ? inner.getParam9().toUpperCase(Locale.ROOT) : "");
        inner.setParam10(
                inner.getParam10() != null ? inner.getParam10().toUpperCase(Locale.ROOT) : "");

        inner.setParam11("N");
        inner.setParam12("N");
        inner.setParam13("N");
        inner.setParam14("N");
        inner.setParam15("N");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ordsHost + "adobe-report");

        var body = new HttpEntity<>(inner, new HttpHeaders());
        try {
            HttpEntity<Map<String, String>> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
                            new ParameterizedTypeReference<>() {});

            assert resp.getBody().containsKey("url");
            String url = resp.getBody().get("url");
            String adobe_url =
                    (url.indexOf('?') == -1) ? "" : adobeHost + url.substring(url.indexOf('?'));

            HttpEntity<byte[]> resp2 =
                    restTemplate.exchange(
                            adobe_url,
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
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog(
                                    "Request Success",
                                    "getJustinAdobeReport"
                                            + " - request: "
                                            + objectMapper.writeValueAsString(inner))));
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
            namespace = "http://courts.gov.bc.ca/xml/ns/pcss/report/v1",
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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(oracleHost);

        builder.queryParam("cmdKey", "pcss_" + inner.getReportName().toLowerCase());
        builder.queryParam("server", oracleServerName);

        for (var param : inner.getParameters()) {
            builder.queryParam(param.getParmNm(), param.getParmValue());
        }
        try {
            HttpEntity<byte[]> resp =
                    restTemplate.exchange(
                            builder.build().toUri(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            byte[].class);

            String b64EncodedReport =
                    resp.getBody() != null ? Base64Utils.encodeToString(resp.getBody()) : "";

            var out = new GetJustinReportResponse();
            var one = new GetJustinReportResponse2();
            var two = new ca.bc.gov.open.wsdl.pcss.report.one.GetJustinReportResponse();
            one.setGetJustinReportResponse(two);
            two.setReportContent(b64EncodedReport);
            two.setResponseCd("0");
            out.setGetJustinReportResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog(
                                    "Request Success",
                                    "getJustinReport"
                                            + " - request: "
                                            + objectMapper.writeValueAsString(inner))));
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
                UriComponentsBuilder.fromHttpUrl(ordsHost + "operation-report")
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
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog(
                                    "Request Success",
                                    "getOperationReport"
                                            + " - request: "
                                            + objectMapper.writeValueAsString(inner))));
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
                UriComponentsBuilder.fromHttpUrl(ordsHost + "operation-report-lov");

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
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog(
                                    "Request Success",
                                    "getOperationReportLov"
                                            + " - request: "
                                            + objectMapper.writeValueAsString(inner))));
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
