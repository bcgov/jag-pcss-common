package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.pcss.controllers.CourtController;
import ca.bc.gov.open.wsdl.pcss.one.*;
import ca.bc.gov.open.wsdl.pcss.three.*;
import ca.bc.gov.open.wsdl.pcss.two.*;
import ca.bc.gov.open.wsdl.pcss.two.GetCourtCalendarDetailByDayRequest;
import ca.bc.gov.open.wsdl.pcss.two.GetFileSearchRequest;
import ca.bc.gov.open.wsdl.pcss.two.GetReservedJudgmentRequest;
import ca.bc.gov.open.wsdl.pcss.two.SetCourtCalendarRequest;
import ca.bc.gov.open.wsdl.pcss.two.SetCourtListMoveRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CourtControllerTests {

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void setCourtListMoveTest() throws JsonProcessingException {
        var req = new SetCourtListMove();
        var one = new SetCourtListMoveRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setApplicationCd("A");
        two.setCourtListDate(Instant.now());
        two.setOldAgencyIdentifierId("A");
        two.setOldCourtRoomCd("A");
        two.setFileDivisionCd(CourtDivisionType.I);
        two.setNewAgencyIdentifierId("A");
        two.setNewCourtRoomCd("A");

        one.setSetCourtListMoveRequest(two);
        req.setSetCourtListMoveRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetCourtListMoveResponse>>
                                        any()))
                .thenReturn(responseEntity);

        CourtController courtController = new CourtController(restTemplate, objectMapper);
        var resp = courtController.setCourtListMove(req);
        assert resp != null;
    }

    @Test
    public void getCourtCalendarDetailByDayTest() throws JsonProcessingException {
        var req = new GetCourtCalendarDetailByDay();
        var one = new GetCourtCalendarDetailByDayRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest();

        two.setCourtAgencyId("A");
        two.setAppearanceDt(Instant.now());
        two.setCourtRoomCd("A");
        two.setRequestDtm(Instant.now());
        two.setRequestPartId("A");
        two.setRequestAgencyIdentifierId("A");

        one.setGetCourtCalendarDetailByDayRequest(two);
        req.setGetCourtCalendarDetailByDayRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        Appearance app = new Appearance();

        app.setCourtDivisionCd(CourtDivisionType.I);
        app.setCourtClassCd(CourtClassType.Y);
        app.setCourtRoomCd("A");
        app.setAppearanceDt(Instant.now());
        app.setAppearanceTm(Instant.now());
        app.setAppearanceReasonCd("A");
        app.setDurationHour("A");
        app.setDurationMin("A");
        app.setCourtFileNumber("A");
        app.setStyleOfCause("A");
        app.setAdjudicatorInitials("A");
        app.setAdjudicatorNm("A");
        app.setHearingRestrictionCd(HearingRestrictionType.D);
        app.setCaseAgeDays("A");
        app.setVideoYn(YesNoType.Y);
        app.setInCustodyYn(YesNoType.Y);
        app.setApprId("A");
        app.setAccusedNm("A");
        app.setAccusedCounselNm("A");
        app.setAppearanceId("A");
        app.setProfPartId("A");
        app.setProfSeqNo("A");
        app.setAppearanceStatusCd(AppearanceStatusType.CNCL);
        app.setRemoteVideoYn(YesNoType.Y);
        app.setHomeLocationAgenId("A");
        app.setCourtlistRefNumber("A");
        app.setMdocJustinNo("A");
        app.setPhysicalFileId("A");
        app.setCourtLevelCd(CourtLevelType.S);
        app.setNoteTxt("A");

        Party party = new Party();
        party.setPartyId("A");
        party.setPartyNm("A");
        party.setCounselNm("A");
        app.setParty(Collections.singletonList(party));

        out.setAppearance(Collections.singletonList(app));

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayResponse>
                responseEntity = new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<
                                                ca.bc.gov.open.wsdl.pcss.one
                                                        .GetCourtCalendarDetailByDayResponse>>
                                        any()))
                .thenReturn(responseEntity);

        CourtController courtController = new CourtController(restTemplate, objectMapper);
        var resp = courtController.getCourtCalendarDetailByDay(req);
        assert resp != null;
    }

    @Test
    public void setCourtCalendarTest() throws JsonProcessingException {
        var req = new SetCourtCalendar();
        var one = new SetCourtCalendarRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarRequest();
        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        Detail3 dt = new Detail3();
        dt.setCourtAgencyId("A");
        dt.setCourtRoomCd("A");
        dt.setAvailableDt(Instant.now());
        dt.setCourtRoomSittingCd(CourtRoomSittingType.D);
        dt.setAdjudicatorPartId("A");
        dt.setJudgeSeizedYn(YesNoType.Y);
        dt.setActivityCd("A");
        dt.setRoomAssignedYn(YesNoType.Y);
        dt.setPcssCourtActivityId("A");

        two.setDetail(Collections.singletonList(dt));
        one.setSetCourtCalendarRequest(two);
        req.setSetCourtCalendarRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetCourtCalendarResponse>>
                                        any()))
                .thenReturn(responseEntity);

        CourtController courtController = new CourtController(restTemplate, objectMapper);
        var resp = courtController.setCourtCalendar(req);
        assert resp != null;
    }

    @Test
    public void getReservedJudgmentTest() throws JsonProcessingException {
        var req = new GetReservedJudgment();
        var one = new GetReservedJudgmentRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentRequest();
        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());

        one.setGetReservedJudgmentRequest(two);
        req.setGetReservedJudgmentRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        ReservedJudgment rj = new ReservedJudgment();
        rj.setAdjudicatorPartId("A");
        rj.setCourtDivisionCd(CourtDivisionType.I);
        rj.setCourtClassCd(CourtClassType.Y);
        rj.setFileNumberTxt("A");
        rj.setNextAppearancetDt(Instant.now());
        rj.setNextAppearancetAgencyId("A");
        rj.setAccusedNm("A");
        rj.setSocTxt("A");
        rj.setJustinNo("A");
        rj.setProfPartId("A");
        rj.setProfSeqNo("A");
        rj.setPhysicalFileId("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentResponse>>
                                        any()))
                .thenReturn(responseEntity);

        CourtController courtController = new CourtController(restTemplate, objectMapper);
        var resp = courtController.getReservedJudgment(req);
        assert resp != null;
    }

    @Test
    public void getFileSearchTest() throws JsonProcessingException {
        var req = new GetFileSearch();
        var one = new GetFileSearchRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setSearchMode(SearchModeType.FILENO);
        two.setFileDivisionCd(CourtDivisionType.I);
        two.setFileHomeAgencyId("A");
        two.setFileNumberTxt("A");
        two.setFilePrefixTxt("A");
        two.setFileSuffixNo("A");
        two.setMdocRefTypeCd("A");
        two.setCourtClassCd(CourtClassType.C);
        two.setCourtLevelCd(CourtLevelType.S);
        two.setNameSearchTypeCd(NameSearchType.S);
        two.setLastNm("A");
        two.setOrgNm("A");
        two.setGivenNm("A");
        two.setBirthDt("A");
        two.setMdocJustinNoSet("A");
        two.setPhysicalFileIdSet("A");
        two.setApplicationCd("A");

        SearchByCrown sc = new SearchByCrown();
        sc.setActiveOnlyYN(YesNoType.Y);
        sc.setFileDesignationCd(FileComplexityType.ALL);
        sc.setCrownPartId("A");

        Permission p = new Permission();
        p.setCourtClassCd(CourtClassType.C);

        two.setPermission(Collections.singletonList(p));
        two.setSearchByCrown(sc);
        one.setGetFileSearchRequest(two);
        req.setGetFileSearchRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchResponse();
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
        fd.setCourtClassCd(CourtClassType.C);
        fd.setWarrantYN(YesNoType.Y);
        fd.setInCustodyYN(YesNoType.Y);
        fd.setNextApprDt(Instant.now());
        fd.setPcssCourtDivisionCd("A");
        fd.setSealStatusCd("A");
        fd.setApprovalCrownAgencyTypeCd("A");

        Participant par = new Participant();
        par.setFullNm("A");
        Charge c = new Charge();
        c.setSectionTxt("A");
        c.setSectionDscTxt("A");
        par.setCharge(Collections.singletonList(c));

        out.setFileDetail(Collections.singletonList(fd));

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetFileSearchResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<ca.bc.gov.open.wsdl.pcss.one.GetFileSearchResponse>>any()))
                .thenReturn(responseEntity);

        CourtController courtController = new CourtController(restTemplate, objectMapper);
        var resp = courtController.getFileSearch(req);
        assert resp != null;
    }
}
