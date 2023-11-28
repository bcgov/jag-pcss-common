package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.pcss.models.RequestSuccessLog;
import ca.bc.gov.open.pcss.models.serializers.InstantSerializer;
import ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest;
import ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayResponse;
import ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveRequest;
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
public class CourtController {

    @Value("${pcss.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CourtController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setCourtListMove")
    @ResponsePayload
    public SetCourtListMoveResponse setCourtListMove(@RequestPayload SetCourtListMove search)
            throws JsonProcessingException {

        var inner =
                search.getSetCourtListMoveRequest() != null
                                && search.getSetCourtListMoveRequest().getSetCourtListMoveRequest()
                                        != null
                        ? search.getSetCourtListMoveRequest().getSetCourtListMoveRequest()
                        : new SetCourtListMoveRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "courtlist/move");
        HttpEntity<SetCourtListMoveRequest> body = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.PUT,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveResponse.class);

            var out = new SetCourtListMoveResponse();
            var one = new SetCourtListMoveResponse2();
            one.setSetCourtListMoveResponse(resp.getBody());
            out.setSetCourtListMoveResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "setCourtListMove")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setCourtListMove",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCourtCalendarDetailByDay")
    @ResponsePayload
    public ca.bc.gov.open.wsdl.pcss.two.GetCourtCalendarDetailByDayResponse
            getCourtCalendarDetailByDay(@RequestPayload GetCourtCalendarDetailByDay search)
                    throws JsonProcessingException {
        var inner =
                search.getGetCourtCalendarDetailByDayRequest() != null
                                && search.getGetCourtCalendarDetailByDayRequest()
                                                .getGetCourtCalendarDetailByDayRequest()
                                        != null
                        ? search.getGetCourtCalendarDetailByDayRequest()
                                .getGetCourtCalendarDetailByDayRequest()
                        : new GetCourtCalendarDetailByDayRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "calendar-detail")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert(inner.getRequestDtm()))
                        .queryParam("applicationCd", inner.getApplicationCd())
                        .queryParam(
                                "appearanceDt", InstantSerializer.convert(inner.getAppearanceDt()))
                        .queryParam("courtRoomCd", inner.getCourtRoomCd())
                        .queryParam("courtAgencyId", inner.getCourtAgencyId());

        try {
            HttpEntity<GetCourtCalendarDetailByDayResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetCourtCalendarDetailByDayResponse.class);

            var out = new ca.bc.gov.open.wsdl.pcss.two.GetCourtCalendarDetailByDayResponse();
            var one = new GetCourtCalendarDetailByDayResponse2();
            out.setGetCourtCalendarDetailByDayResponse(one);
            one.setGetCourtCalendarDetailByDayResponse(resp.getBody());
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog(
                                    "Request Success", "getCourtCalendarDetailByDay")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCourtCalendarDetailByDay",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setCourtCalendar")
    @ResponsePayload
    public SetCourtCalendarResponse setCourtCalendar(@RequestPayload SetCourtCalendar search)
            throws JsonProcessingException {
        var inner =
                search.getSetCourtCalendarRequest() != null
                                && search.getSetCourtCalendarRequest().getSetCourtCalendarRequest()
                                        != null
                        ? search.getSetCourtCalendarRequest().getSetCourtCalendarRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "calendar");
        HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarRequest> body =
                new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarResponse.class);

            var out = new SetCourtCalendarResponse();
            var one = new SetCourtCalendarResponse2();
            one.setSetCourtCalendarResponse(resp.getBody());
            out.setSetCourtCalendarResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "setCourtCalendar")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setCourtCalendar",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getReservedJudgment")
    @ResponsePayload
    public GetReservedJudgmentResponse getReservedJudgment(
            @RequestPayload GetReservedJudgment search) throws JsonProcessingException {
        var inner =
                search.getGetReservedJudgmentRequest() != null
                                && search.getGetReservedJudgmentRequest()
                                                .getGetReservedJudgmentRequest()
                                        != null
                        ? search.getGetReservedJudgmentRequest().getGetReservedJudgmentRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "reserved-judgement")
                        .queryParam("requestDt", inner.getRequestDtm())
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentResponse.class);

            var out = new GetReservedJudgmentResponse();
            var one = new GetReservedJudgmentResponse2();
            one.setGetReservedJudgmentResponse(resp.getBody());
            out.setGetReservedJudgmentResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getReservedJudgment")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getReservedJudgment",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getFileSearch")
    @ResponsePayload
    public GetFileSearchResponse getFileSearch(@RequestPayload GetFileSearch search)
            throws JsonProcessingException {
        var inner =
                search.getGetFileSearchRequest() != null
                                && search.getGetFileSearchRequest().getGetFileSearchRequest()
                                        != null
                        ? search.getGetFileSearchRequest().getGetFileSearchRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "file-search");

        var body = new HttpEntity<>(inner, new HttpHeaders());
        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetFileSearchResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.GetFileSearchResponse.class);

            var out = new GetFileSearchResponse();
            var one = new GetFileSearchResponse2();
            one.setGetFileSearchResponse(resp.getBody());
            out.setGetFileSearchResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getFileSearch")));
            return out;
        } catch (Exception ex) {
            inner.setGivenNm("");
            inner.setLastNm("");
            inner.setBirthDt("");
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getFileSearch",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
