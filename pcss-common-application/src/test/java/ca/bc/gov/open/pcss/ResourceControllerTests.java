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
import java.net.URI;
import java.time.Instant;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceControllerTests {
    @Mock private ObjectMapper objectMapper;
    @Mock private RestTemplate restTemplate;
    @Mock private ResourceController resourceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceController = Mockito.spy(new ResourceController(restTemplate, objectMapper));
    }

    @Test
    public void getResourceAvailabilityTest() throws JsonProcessingException {
        var req = new GetResourceAvailability();
        var one = new GetResourceAvailabilityRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setModeCd(OperationModeRscAvType.D);
        two.setBookingDt(Instant.now());
        two.setAssetTypeCd("A");
        two.setBookingForRoleCd("A");
        two.setBookingFromTm(Instant.now());
        two.setBookingToTm(Instant.now());
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
        r1.getAvailableRoom().add(ar);

        Day d = new Day();
        d.setAvailabilityDt(Instant.now());
        d.setBookedHours("A");
        d.setDayOfMonth("A");
        d.setDayOfWeek(DayOfWeekType.MO);

        BookedTime bt = new BookedTime();
        bt.setBookedFromTm(Instant.now());
        bt.setBookedToTm(Instant.now());
        bt.setCourtAgencyId("A");
        bt.setCourtRoomCd("A");
        bt.setBookedByNm("A");
        bt.setCourtFileNumberTxt("A");
        d.getBookedTime().add(bt);

        r1.getDay().add(d);

        ResourceType r2 = new ResourceType();
        r2.setAgencyId("A");
        r2.setResourceId("A");
        r2.setResourceNm("A");
        r2.setAssetTypeCd("A");
        r2.setAssetUsageRuleCd(AssetUsageRuleType.FIX);
        r2.setPhoneNumberTxt("A");
        r2.setCommentTxt("A");
        r2.getAvailableRoom().add(ar);
        r2.getDay().add(d);

        out.getPrimaryResource().add(r1);
        out.getSecondaryResource().add(r2);

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
        two.setBookingDt(Instant.now());
        two.setBookingFromTm(Instant.now());
        two.setBookingToTm(Instant.now());
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
        two.setRequestDtm(Instant.now());
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
        two.setRequestDtm(Instant.now());
        two.setLastRetrievedDate(Instant.now());

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
        jv.getCodeValue().add(cv2);

        cv.getCodeValue().add(codeValue);
        out.setJustinCodeValues(jv);
        out.setCeisCodeValues(cv);

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(URI.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesResponse>>any()))
                .thenReturn(responseEntity);

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
        two.setRequestDtm(Instant.now());
        two.setProcessUpToDtm(Instant.now());
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

        var resp = resourceController.setSyncComplete(req);
        assert resp != null;
    }

    @Test
    public void getUserLoginTest() throws JsonProcessingException {
        var req = new GetUserLogin();
        var one = new GetUserLoginRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetUserLoginRequest();
        two.setRequestDtm(Instant.now());

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

        var resp = resourceController.getUserLogin(req);
        assert resp != null;
    }
}
