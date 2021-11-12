package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.pcss.controllers.ResourceController;
import ca.bc.gov.open.wsdl.pcss.one.CeisCodeValues;
import ca.bc.gov.open.wsdl.pcss.one.CodeValue;
import ca.bc.gov.open.wsdl.pcss.one.CodeValue2;
import ca.bc.gov.open.wsdl.pcss.one.JustinCodeValues;
import ca.bc.gov.open.wsdl.pcss.three.*;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ResourceControllerTests {

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getResourceAvailabilityTest() throws JsonProcessingException {
        var req = new GetResourceAvailability();
        var one = new GetResourceAvailabilityRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm("A");
        two.setModeCd(OperationModeRscAvType.D);
        two.setBookingDt("A");
        two.setAssetTypeCd("A");
        two.setBookingForRoleCd("A");
        two.setBookingFromTm("A");
        two.setBookingToTm("A");
        two.setPrimaryAgencyId("A");
        two.setPrimaryCourtRoomCd("A");
        two.setSecondaryAgencyId("A");
        two.setSecondaryCourtRoomCd("A");

        one.setGetResourceAvailabilityRequest(two);
        req.setGetResourceAvailabilityRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResourceType r1 = new ResourceType();
        r1.setAgencyId("A");
        r1.setResourceId("A");
        r1.setResourceNm("A");
        r1.setAssetTypeCd("A");
        r1.setAssetUsageRuleCd(AssetUsageRuleType.FIX);
        r1.setPhoneNumberTxt("A");
        r1.setCommentTxt("A");
        AvailableRoom ar = new AvailableRoom();
        ar.setCourtAgencyId("A");
        ar.setCourtRoomCd("A");
        r1.setAvailableRoom(Collections.singletonList(ar));

        Day d = new Day();
        d.setAvailabilityDt("A");
        d.setBookedHours("A");
        d.setDayOfMonth("A");
        d.setDayOfWeek(DayOfWeekType.MO);

        BookedTime bt = new BookedTime();
        bt.setBookedFromTm("A");
        bt.setBookedToTm("A");
        bt.setCourtAgencyId("A");
        bt.setCourtRoomCd("A");
        bt.setBookedByNm("A");
        bt.setCourtFileNumberTxt("A");
        d.setBookedTime(Collections.singletonList(bt));

        r1.setDay(Collections.singletonList(d));

        ResourceType r2 = new ResourceType();
        r2.setAgencyId("A");
        r2.setResourceId("A");
        r2.setResourceNm("A");
        r2.setAssetTypeCd("A");
        r2.setAssetUsageRuleCd(AssetUsageRuleType.FIX);
        r2.setPhoneNumberTxt("A");
        r2.setCommentTxt("A");
        r2.setAvailableRoom(Collections.singletonList(ar));
        r2.setDay(Collections.singletonList(d));

        out.setPrimaryResource(Collections.singletonList(r1));
        out.setSecondaryResource(Collections.singletonList(r2));

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityResponse>
                responseEntity = new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<
                                                ca.bc.gov.open.wsdl.pcss.one
                                                        .GetResourceAvailabilityResponse>>
                                        any()))
                .thenReturn(responseEntity);

        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);
        var resp = resourceController.getResourceAvailability(req);
        assert resp != null;
    }

    @Test
    public void setResourceBookingTest() throws JsonProcessingException {
        var req = new SetResourceBooking();
        var one = new SetResourceBookingRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setOperationModeCd(OperationModeBookType.BOOK);
        two.setAppearanceId("A");
        two.setCourtDivisionCd(CourtDivisionType.I);
        two.setResourceId("A");
        two.setBookingDt("A");
        two.setBookingFromTm("A");
        two.setBookingToTm("A");
        two.setCourtAgencyId("A");
        two.setCourtRoomCd("A");
        two.setBookingCommentTxt("A");
        two.setBookingId("A");
        two.setBookingCcn("A");

        req.setSetResourceBookingRequest(one);
        one.setSetResourceBookingRequest(two);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        out.setBookingId("A");
        out.setBookingCcn("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetResourceBookingResponse>>
                                        any()))
                .thenReturn(responseEntity);

        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);
        var resp = resourceController.setResourceBooking(req);
        assert resp != null;
    }

    @Test
    public void setResourceCancelTest() throws JsonProcessingException {
        var req = new SetResourceCancel();
        var one = new SetResourceCancelRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelRequest();

        two.setBookingCcn("A");
        two.setBookingId("A");
        two.setRequestDtm("A");
        two.setRequestPartId("A");
        two.setRequestAgencyIdentifierId("A");

        one.setSetResourceCancelRequest(two);
        req.setSetResourceCancelRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetResourceCancelResponse>>
                                        any()))
                .thenReturn(responseEntity);

        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);
        var resp = resourceController.setResourceCancel(req);
        assert resp != null;
    }

    @Test
    public void getCodeValuesTest() throws JsonProcessingException {
        var req = new GetCodeValues();
        var one = new GetCodeValuesRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm("A");
        two.setLastRetrievedDate("A");

        one.setGetCodeValuesRequest(two);
        req.setGetCodeValuesRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        CeisCodeValues cv = new CeisCodeValues();
        CodeValue codeValue = new CodeValue();
        codeValue.setCodeType("A");
        codeValue.setCode("A");
        codeValue.setShortDesc("A");
        codeValue.setLongDesc("A");
        codeValue.setFlex("A");

        JustinCodeValues jv = new JustinCodeValues();
        CodeValue2 cv2 = new CodeValue2();
        cv2.setCode("A");
        cv2.setCodeType("A");
        cv2.setFlex("A");
        cv2.setLongDesc("A");
        cv2.setShortDesc("A");
        jv.setCodeValue(Collections.singletonList(cv2));

        cv.setCodeValue(Collections.singletonList(codeValue));
        out.setJustinCodeValues(jv);
        out.setCeisCodeValues(cv);

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse>>any()))
                .thenReturn(responseEntity);

        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);
        var resp = resourceController.getCodeValues(req);
        assert resp != null;
    }

    @Test
    public void setSyncCompleteTest() throws JsonProcessingException {
        var req = new SetSyncComplete();
        var one = new SetSyncCompleteRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm("A");
        two.setProcessUpToDtm("A");
        two.setCriminalAppearanceYn(YesNoType.Y);
        two.setCivilAppearanceYn(YesNoType.Y);
        two.setCivilHearingRestrictionYn(YesNoType.Y);
        two.setCivilHearingRestrictionYn(YesNoType.Y);

        one.setSetSyncCompleteRequest(two);
        req.setSetSyncCompleteRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<ca.bc.gov.open.wsdl.pcss.one.SetSyncCompleteResponse>>any()))
                .thenReturn(responseEntity);

        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);
        var resp = resourceController.setSyncComplete(req);
        assert resp != null;
    }

    @Test
    public void getUserLoginTest() throws JsonProcessingException {
        var req = new GetUserLogin();
        var one = new GetUserLoginRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetUserLoginRequest();
        two.setRequestDtm("A");

        two.setDomainNm(DomainNmType.IDIR);
        two.setDomainUserGuid("A");
        two.setDomainUserId("A");
        two.setTemporaryAccessGuid("A");

        one.setGetUserLoginRequest(two);
        req.setGetUserLoginRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetUserLoginResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        out.setUserPartId("A");
        out.setUserLastNm("A");
        out.setUserGivenNm("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetUserLoginResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<ca.bc.gov.open.wsdl.pcss.one.GetUserLoginResponse>>any()))
                .thenReturn(responseEntity);

        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);
        var resp = resourceController.getUserLogin(req);
        assert resp != null;
    }
}
