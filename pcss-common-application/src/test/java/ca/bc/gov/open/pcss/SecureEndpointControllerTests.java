package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.pcss.controllers.SecureEndpointController;
import ca.bc.gov.open.wsdl.pcss.secure.one.*;
import ca.bc.gov.open.wsdl.pcss.secure.three.*;
import ca.bc.gov.open.wsdl.pcss.secure.two.*;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetCourtCalendarDetailByDaySecureRequest;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetFileSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.Instant;
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
public class SecureEndpointControllerTests {

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getFileSecureTest() throws JsonProcessingException {

        var req = new GetFileSearchSecure();
        var one = new GetFileSearchRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setSearchMode(SearchModeType.CROWN);
        two.setFileDivisionCd(CourtDivisionType.I);
        two.setFileHomeAgencyId("A");
        two.setFileHomeAgencyId("A");
        two.setFileNumberTxt("A");
        two.setFilePrefixTxt("A");
        two.setFileSuffixNo("A");
        two.setMdocRefTypeCd("A");
        two.setCourtClassCd(CourtClassType.Y);
        two.setCourtLevelCd(CourtLevelType.S);
        two.setNameSearchTypeCd(NameSearchType.S);
        two.setLastNm("A");
        two.setOrgNm("A");
        two.setGivenNm("A");
        two.setGivenNm("A");
        two.setBirthDt("A");

        SearchByCrown c = new SearchByCrown();
        c.setCrownPartId("A");
        c.setFileDesignationCd(FileComplexityType.ALL);
        c.setActiveOnlyYN(YesNoType.Y);

        Permission p = new Permission();
        p.setCourtClassCd(CourtClassType.Y);

        two.setPermission(Collections.singletonList(p));
        two.setSearchByCrown(c);
        two.setMdocJustinNoSet("A");
        two.setPhysicalFileIdSet("A");
        two.setApplicationCd("A");

        one.setGetFileSearchRequest(two);
        req.setGetFileSearchSecureRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        out.setRecCount("A");
        FileDetail fd = new FileDetail();
        fd.setMdocJustinNo("A");
        fd.setPhysicalFileId("A");
        fd.setFileHomeAgencyId("A");
        fd.setFileNumberTxt("A");
        fd.setMdocSeqNo("A");
        fd.setTicketSeriesTxt("A");
        fd.setMdocRefTypeCd("A");
        fd.setCourtLevelCd(CourtLevelType.S);
        fd.setCourtClassCd(CourtClassType.S);
        fd.setWarrantYN(YesNoType.Y);
        fd.setInCustodyYN(YesNoType.Y);
        fd.setNextApprDt(Instant.now());
        fd.setPcssCourtDivisionCd("A");
        fd.setSealStatusCd("A");
        fd.setApprovalCrownAgencyTypeCd("A");

        Participant par = new Participant();
        par.setFullNm("A");
        Charge chr = new Charge();

        chr.setSectionTxt("A");
        chr.setSectionDscTxt("A");

        par.setCharge(Collections.singletonList(chr));
        fd.setParticipant(Collections.singletonList(par));

        out.setFileDetail(Collections.singletonList(fd));

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.secure.one.GetFileSearchResponse>>
                                        any()))
                .thenReturn(responseEntity);

        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);
        var res = secureEndpointController.getFileSearchSecure(req);
        assert res != null;
    }

    @Test
    public void getCodesValuesSecureTest() throws JsonProcessingException {
        var req = new GetCodeValuesSecure();
        var one = new GetCodeValuesSecureRequest();
        var two = new GetGetCodeValueSecureRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setApplicationCd("A");
        two.setLastRetrievedDate(Instant.now());

        one.setGetGetCodeValueSecureRequest(two);
        req.setGetCodeValuesSecureRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.secure.one.GetCodeValuesResponse();
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

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetCodeValuesResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        when(restTemplate.exchange(
                        Mockito.any(URI.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.secure.one.GetCodeValuesResponse>>
                                        any()))
                .thenReturn(responseEntity);

        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);
        var res = secureEndpointController.getCodesValuesSecure(req);
        assert res != null;
    }

    @Test
    public void getCourtCalendarDetailByDaySecureTest() throws JsonProcessingException {
        var req = new GetCourtCalendarDetailByDaySecure();
        var one = new GetCourtCalendarDetailByDaySecureRequest();
        var two =
                new ca.bc.gov.open.wsdl.pcss.secure.one.GetCourtCalendarDetailByDaySecureRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setApplicationCd("A");
        two.setCourtAgencyId("A");
        two.setAppearanceDt(Instant.now());
        two.setCourtRoomCd("A");

        one.setGetCourtCalendarDetailByDaySecureRequest(two);
        req.setGetCourtCalendarDetailByDaySecureRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.secure.one.GetCourtCalendarDetailByDayResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        Appearance ap = new Appearance();

        ap.setAppearanceId("A");
        ap.setCourtDivisionCd(CourtDivisionType.I);
        ap.setCourtClassCd(CourtClassType.Y);
        ap.setCourtRoomCd("A");
        ap.setAppearanceDt(Instant.now());
        ap.setAppearanceTm(Instant.now());
        ap.setAppearanceReasonCd("A");
        ap.setDurationHour("A");
        ap.setDurationMin("A");
        ap.setCourtFileNumber("A");
        ap.setStyleOfCause("A");
        ap.setAdjudicatorInitials("A");
        ap.setAdjudicatorNm("A");
        ap.setHearingRestrictionCd(HearingRestrictionType.S);
        ap.setCaseAgeDays("A");
        ap.setVideoYn(YesNoType.Y);
        ap.setInCustodyYn(YesNoType.Y);
        ap.setApprId("A");
        ap.setAccusedNm("A");
        ap.setAccusedCounselNm("A");
        ap.setProfPartId("A");
        ap.setProfSeqNo("A");
        ap.setAppearanceStatusCd(AppearanceStatusType.CNCL);
        ap.setRemoteVideoYn(YesNoType.Y);
        ap.setHomeLocationAgenId("A");
        ap.setCourtlistRefNumber("A");
        ap.setMdocJustinNo("A");
        ap.setPhysicalFileId("A");
        ap.setCourtLevelCd(CourtLevelType.S);
        ap.setNoteTxt("A");

        Party party = new Party();
        party.setPartyId("A");
        party.setPartyNm("A");
        party.setCounselNm("A");

        ap.setParty(Collections.singletonList(party));

        out.setAppearance(Collections.singletonList(ap));

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.secure.one.GetCourtCalendarDetailByDayResponse>
                responseEntity = new ResponseEntity<>(out, HttpStatus.OK);

        when(restTemplate.exchange(
                        Mockito.any(URI.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<
                                                ca.bc.gov.open.wsdl.pcss.secure.one
                                                        .GetCourtCalendarDetailByDayResponse>>
                                        any()))
                .thenReturn(responseEntity);

        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);
        var res = secureEndpointController.getCourtCalendarDetailByDaySecure(req);
        assert res != null;
    }
}
