package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.pcss.models.serializers.InstantSerializer;
import ca.bc.gov.open.wsdl.pcss.secure.two.*;
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
public class SecureEndpointController {

    @Value("${pcss.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SecureEndpointController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getFileSearchSecure")
    @ResponsePayload
    public GetFileSearchSecureResponse getFileSearchSecure(
            @RequestPayload GetFileSearchSecure search) throws JsonProcessingException {

        var inner =
                search.getGetFileSearchSecureRequest() != null
                                && search.getGetFileSearchSecureRequest().getGetFileSearchRequest()
                                        != null
                        ? search.getGetFileSearchSecureRequest().getGetFileSearchRequest()
                        : new ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "secure/file-search");

        HttpEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchRequest> body =
                new HttpEntity<>(inner, new HttpHeaders());
        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
                            ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchResponse.class);

            var out = new GetFileSearchSecureResponse();
            var one = new GetFileSearchResponse();
            one.setGetFileSearchResponse(resp.getBody());
            out.setGetFileSearchResponse(one);
            return out;
        } catch (Exception ex) {
            inner.setGivenNm("");
            inner.setLastNm("");
            inner.setBirthDt("");
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getFileSearchSecure",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCodeValuesSecure")
    @ResponsePayload
    public GetCodeValuesSecureResponse getCodesValuesSecure(
            @RequestPayload GetCodeValuesSecure search) throws JsonProcessingException {
        var inner =
                search.getGetCodeValuesSecureRequest() != null
                                && search.getGetCodeValuesSecureRequest()
                                                .getGetGetCodeValueSecureRequest()
                                        != null
                        ? search.getGetCodeValuesSecureRequest().getGetGetCodeValueSecureRequest()
                        : new ca.bc.gov.open.wsdl.pcss.secure.one.GetGetCodeValueSecureRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "secure/code-values")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert(inner.getRequestDtm()))
                        .queryParam("applicationCd", inner.getApplicationCd())
                        .queryParam(
                                "lastRetrievedDate",
                                InstantSerializer.convert(inner.getLastRetrievedDate()));

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetCodeValuesResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.secure.one.GetCodeValuesResponse.class);

            var out = new GetCodeValuesSecureResponse();
            var one = new GetCodeValuesResponse();
            one.setGetCodeValuesResponse(resp.getBody());
            out.setGetCodeValuesResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCodesValuesSecure",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(
            namespace = SoapConfig.SOAP_NAMESPACE,
            localPart = "getCourtCalendarDetailByDaySecure")
    @ResponsePayload
    public GetCourtCalendarDetailByDaySecureResponse getCourtCalendarDetailByDaySecure(
            @RequestPayload GetCourtCalendarDetailByDaySecure search)
            throws JsonProcessingException {
        var inner =
                search.getGetCourtCalendarDetailByDaySecureRequest() != null
                                && search.getGetCourtCalendarDetailByDaySecureRequest()
                                                .getGetCourtCalendarDetailByDaySecureRequest()
                                        != null
                        ? search.getGetCourtCalendarDetailByDaySecureRequest()
                                .getGetCourtCalendarDetailByDaySecureRequest()
                        : new ca.bc.gov.open.wsdl.pcss.secure.one
                                .GetCourtCalendarDetailByDaySecureRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "secure/calendar-detail")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert3(inner.getRequestDtm()))
                        .queryParam(
                                "appearanceDt", InstantSerializer.convert(inner.getAppearanceDt()))
                        .queryParam("applicationCd", inner.getApplicationCd())
                        .queryParam("courtRoomCd", inner.getCourtRoomCd())
                        .queryParam("courtAgencyId", inner.getCourtAgencyId());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetCourtCalendarDetailByDayResponse>
                    resp =
                            restTemplate.exchange(
                                    builder.toUriString(),
                                    HttpMethod.GET,
                                    new HttpEntity<>(new HttpHeaders()),
                                    ca.bc.gov.open.wsdl.pcss.secure.one
                                            .GetCourtCalendarDetailByDayResponse.class);

            var out = new GetCourtCalendarDetailByDaySecureResponse();
            var one = new GetCourtCalendarDetailByDayResponse();
            one.setGetCourtCalendarDetailByDayResponse(resp.getBody());
            out.setGetCourtCalendarDetailByDayResponse(one);
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCourtCalendarDetailByDaySecure",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
