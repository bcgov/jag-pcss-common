package ca.bc.gov.open.pcss.controllers;

import ca.bc.gov.open.pcss.configuration.SoapConfig;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.pcss.models.OrdsErrorLog;
import ca.bc.gov.open.pcss.models.RequestSuccessLog;
import ca.bc.gov.open.pcss.models.serializers.InstantSerializer;
import ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityRequest;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Valid;
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
public class ResourceController {

    @Value("${pcss.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ResourceController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getResourceAvailability")
    @ResponsePayload
    public GetResourceAvailabilityResponse getResourceAvailability(
            @RequestPayload GetResourceAvailability search) throws JsonProcessingException {

        var inner =
                search.getGetResourceAvailabilityRequest() != null
                                && search.getGetResourceAvailabilityRequest()
                                                .getGetResourceAvailabilityRequest()
                                        != null
                        ? search.getGetResourceAvailabilityRequest()
                                .getGetResourceAvailabilityRequest()
                        : new GetResourceAvailabilityRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "resource")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", inner.getRequestDtm())
                        .queryParam("bookingDt", inner.getBookingDt())
                        .queryParam("modeCd", inner.getModeCd())
                        .queryParam("assetTypeCd", inner.getAssetTypeCd())
                        .queryParam("bookingForRoleCd", inner.getBookingForRoleCd())
                        .queryParam("bookingFromTm", inner.getBookingFromTm())
                        .queryParam("bookToTm", inner.getBookingToTm())
                        .queryParam("primaryAgencyId", inner.getPrimaryAgencyId())
                        .queryParam("primaryCourtRoomCd", inner.getPrimaryCourtRoomCd())
                        .queryParam("secondaryAgencyId", inner.getSecondaryAgencyId())
                        .queryParam("secondaryCourtRoomCd", inner.getSecondaryCourtRoomCd());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityResponse.class);

            var out = new GetResourceAvailabilityResponse();
            var one = new GetResourceAvailabilityResponse2();
            one.setGetResourceAvailabilityResponse(resp.getBody());
            out.setGetResourceAvailabilityResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getResourceAvailability")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getResourceAvailability",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setResourceBooking")
    @ResponsePayload
    public SetResourceBookingResponse setResourceBooking(@RequestPayload SetResourceBooking search)
            throws JsonProcessingException {

        var inner =
                search.getSetResourceBookingRequest() != null
                                && search.getSetResourceBookingRequest()
                                                .getSetResourceBookingRequest()
                                        != null
                        ? search.getSetResourceBookingRequest().getSetResourceBookingRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "resource");

        var body = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingResponse.class);

            var out = new SetResourceBookingResponse();
            var one = new SetResourceBookingResponse2();
            one.setSetResourceBookingResponse(resp.getBody());
            out.setSetResourceBookingResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "setResourceBooking")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setResourceBooking",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setResourceCancel")
    @ResponsePayload
    public SetResourceCancelResponse setResourceCancel(@RequestPayload SetResourceCancel search)
            throws JsonProcessingException {

        var inner =
                search.getSetResourceCancelRequest() != null
                                && search.getSetResourceCancelRequest()
                                                .getSetResourceCancelRequest()
                                        != null
                        ? search.getSetResourceCancelRequest().getSetResourceCancelRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "/resource/cancel");

        var body = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.PUT,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelResponse.class);

            var out = new SetResourceCancelResponse();
            var one = new SetResourceCancelResponse2();
            one.setSetResourceCancelResponse(resp.getBody());
            out.setSetResourceCancelResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "setResourceCancel")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setResourceCancel",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCodeValues")
    @ResponsePayload
    public GetCodeValuesResponse getCodeValues(@RequestPayload @Valid GetCodeValues search)
            throws JsonProcessingException {

        var inner =
                search.getGetCodeValuesRequest() != null
                                && search.getGetCodeValuesRequest().getGetCodeValuesRequest()
                                        != null
                        ? search.getGetCodeValuesRequest().getGetCodeValuesRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "code-values")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert(inner.getRequestDtm()))
                        .queryParam(
                                "lastRetrievedDate",
                                InstantSerializer.convert(inner.getLastRetrievedDate()));

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse> resp =
                    restTemplate.exchange(
                            builder.build().toUri(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse.class);

            var out = new GetCodeValuesResponse();
            var one = new GetCodeValuesResponse2();
            one.setGetCodeValuesResponse(resp.getBody());
            out.setGetCodeValuesResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getCodeValues")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCodeValues",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "setSyncComplete")
    @ResponsePayload
    public SetSyncCompleteResponse setSyncComplete(@RequestPayload SetSyncComplete search)
            throws JsonProcessingException {

        var inner =
                search.getSetSyncCompleteRequest() != null
                                && search.getSetSyncCompleteRequest().getSetSyncCompleteRequest()
                                        != null
                        ? search.getSetSyncCompleteRequest().getSetSyncCompleteRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "sync-complete");

        var body = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            body,
                            ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteResponse.class);

            var out = new SetSyncCompleteResponse();
            var one = new SetSyncCompleteResponse2();
            one.setSetSyncCompleteResponse(resp.getBody());
            out.setSetSyncCompleteResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "setSyncComplete")));
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "setSyncComplete",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getUserLogin")
    @ResponsePayload
    public GetUserLoginResponse getUserLogin(@RequestPayload GetUserLogin search)
            throws JsonProcessingException {
        var inner =
                search.getGetUserLoginRequest() != null
                                && search.getGetUserLoginRequest().getGetUserLoginRequest() != null
                        ? search.getGetUserLoginRequest().getGetUserLoginRequest()
                        : new ca.bc.gov.open.wsdl.pcss.one.GetUserLoginRequest();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "login")
                        .queryParam("requestDtm", inner.getRequestDtm())
                        .queryParam("domainNm", inner.getDomainNm())
                        .queryParam("domainUserGuid", inner.getDomainUserGuid())
                        .queryParam("domainUserId", inner.getDomainUserId())
                        .queryParam("tempAccessGuid", inner.getTemporaryAccessGuid());

        try {
            HttpEntity<ca.bc.gov.open.wsdl.pcss.one.GetUserLoginResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.wsdl.pcss.one.GetUserLoginResponse.class);

            var out = new GetUserLoginResponse();
            var one = new GetUserLoginResponse2();
            one.setGetUserLoginResponse(resp.getBody());
            out.setGetUserLoginResponse(one);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getUserLogin")));
            return out;
        } catch (Exception ex) {
            inner.setDomainUserId("");
            inner.setTemporaryAccessGuid("");
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getUserLogin",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
