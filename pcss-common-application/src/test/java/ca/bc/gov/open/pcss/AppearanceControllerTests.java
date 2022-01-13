package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.pcss.controllers.AppearanceController;
import ca.bc.gov.open.wsdl.pcss.one.Detail;
import ca.bc.gov.open.wsdl.pcss.one.Detail2;
import ca.bc.gov.open.wsdl.pcss.three.CourtDivisionType;
import ca.bc.gov.open.wsdl.pcss.three.OperationModeSchdType;
import ca.bc.gov.open.wsdl.pcss.two.*;
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
public class AppearanceControllerTests {

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void setAppearanceUpdateTest() throws JsonProcessingException {
        var req = new SetAppearanceUpdate();
        var one = new SetAppearanceUpdateRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setFileDivisionCd(CourtDivisionType.I);
        two.setAppearanceId("A");
        two.setAppearanceReasonCd("A");
        two.setAppearanceTm("A");
        two.setEstimatedTimeHour("A");
        two.setEstimatedTimeMin("A");
        two.setSupplementalEquipmentTxt("A");
        two.setSecurityRestrictionTxt("A");
        two.setOutOfTownJudgeTxt("A");
        two.setAppearanceCcn("A");

        one.setSetAppearanceUpdateRequest(two);
        req.setSetAppearanceUpdateRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateResponse();
        out.setResponseCd("A");
        out.setAppearanceCcn("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceUpdateResponse>>
                                        any()))
                .thenReturn(responseEntity);

        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);
        var resp = appearanceController.setAppearanceUpdate(req);
        assert resp != null;
    }

    @Test
    public void setAppearanceMoveTest() throws JsonProcessingException {
        var req = new SetAppearanceMove();
        var one = new SetAppearanceMoveRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveRequest();
        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());

        Detail2 dt = new Detail2();
        dt.setFileDivisionCd(CourtDivisionType.I);
        dt.setAppearanceId("A");
        dt.setCourtRoomCd("A");

        two.setDetail(Collections.singletonList(dt));
        one.setSetAppearanceMoveRequest(two);
        req.setSetAppearanceMoveRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceMoveResponse>>
                                        any()))
                .thenReturn(responseEntity);

        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);
        var resp = appearanceController.setAppearanceMove(req);
        assert resp != null;
    }

    @Test
    public void setAppearanceStatusTest() throws JsonProcessingException {
        var req = new SetAppearanceStatus();
        var one = new SetAppearanceStatusRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        Detail dt = new Detail();
        dt.setAppearanceCcn("A");
        dt.setAppearanceId("A");
        dt.setFileDivisionCd(CourtDivisionType.I);
        dt.setOperationModeCd(OperationModeSchdType.CANCEL);
        two.setDetail(Collections.singletonList(dt));

        one.setSetAppearanceStatusRequest(two);
        req.setSetAppearanceStatusRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<ca.bc.gov.open.wsdl.pcss.one.SetAppearanceStatusResponse>>
                                        any()))
                .thenReturn(responseEntity);

        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);
        var resp = appearanceController.setAppearanceStatus(req);
        assert resp != null;
    }
}
