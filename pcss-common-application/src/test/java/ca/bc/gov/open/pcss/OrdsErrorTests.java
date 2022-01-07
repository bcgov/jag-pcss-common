package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.open.pcss.controllers.*;
import ca.bc.gov.open.pcss.exceptions.BadRequestException;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.wsdl.pcss.report.one.Parameters;
import ca.bc.gov.open.wsdl.pcss.report.two.GetJustinAdobeReport;
import ca.bc.gov.open.wsdl.pcss.report.two.GetJustinReport;
import ca.bc.gov.open.wsdl.pcss.report.two.GetJustinReportRequest;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetCodeValuesSecure;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetCourtCalendarDetailByDaySecure;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetFileSearchSecure;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrdsErrorTests {
    @Mock private RestTemplate restTemplate;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private MockMvc mockMvc;

    @Test
    public void testHealthPingOrdsFail() throws JsonProcessingException {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getPing(
                        new GetPing()));
    }

    @Test
    public void testHealthHealthOrdsFail() throws JsonProcessingException {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getHealth(
                        new GetHealth()));
    }

    @Test
    public void testsetAppearanceUpdateOrdsFail() throws JsonProcessingException {
        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> appearanceController.setAppearanceUpdate(
                        new SetAppearanceUpdate()));
    }

    @Test
    public void testSetAppearanceMoveOrdsFail() throws JsonProcessingException {
        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> appearanceController.setAppearanceMove(
                        new SetAppearanceMove()));
    }

    @Test
    public void testSetAppearanceStatusOrdsFail() throws JsonProcessingException {
        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);

    Assertions.assertThrows(
        ORDSException.class, () -> appearanceController.setAppearanceStatus(
                new SetAppearanceStatus()));
    }

    @Test
    public void testSetCourtListMoveOrdsFail() throws JsonProcessingException {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
            ORDSException.class, () -> courtController.setCourtListMove(
                new SetCourtListMove()));
    }

    @Test
    public void testGetCourtCalendarDetailByDayOrdsFail() throws JsonProcessingException {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getCourtCalendarDetailByDay(
                        new GetCourtCalendarDetailByDay()));
    }

    @Test
    public void testSetCourtCalendarOrdsFail() throws JsonProcessingException {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.setCourtCalendar(
                        new SetCourtCalendar()));
    }

    @Test
    public void testGetReservedJudgmentOrdsFail() throws JsonProcessingException {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getReservedJudgment(
                        new GetReservedJudgment()));
    }

    @Test
    public void testGetFileSearchOrdsFail() throws JsonProcessingException {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getFileSearch(
                        new GetFileSearch()));
    }

    @Test
    public void testGetJustinAdobeReportOrdsFail() throws JsonProcessingException {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getJustinAdobeReport(
                        new GetJustinAdobeReport()));
    }

    @Test
    public void testGetJustinReportOrdsFail() throws JsonProcessingException {
        var resourceController = new ReportController(restTemplate, objectMapper);
        var req = new GetJustinReport();
        var reqInner = new GetJustinReportRequest();
        var reqInnest = new ca.bc.gov.open.wsdl.pcss.report.one.GetJustinReportRequest();

        req.setGetJustinReportRequest(reqInner);
        reqInner.setGetJustinReportRequest(reqInnest);
        reqInnest.setReportName("A");
        reqInnest.setParameters(new ArrayList<Parameters>());
        reqInnest.setRequestDtm("A");
        reqInnest.setRequestAgencyIdentifierId("A");
        reqInnest.setRequestDtm("A");

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getJustinReport(req));
    }

    @Test
    public void testGetOperationReportOrdsFail() throws JsonProcessingException {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getOperationReport(
                        new GetOperationReport()));
    }

    @Test
    public void testGetOperationReportLovOrdsFail() throws JsonProcessingException {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getOperationReportLov(
                        new GetOperationReportLov()));
    }

    @Test
    public void testGetResourceAvailabilityOrdsFail() throws JsonProcessingException {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getResourceAvailability(
                        new GetResourceAvailability()));
    }

    @Test
    public void testSetResourceBookingOrdsFail() throws JsonProcessingException {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.setResourceBooking(
                        new SetResourceBooking()));
    }

    @Test
    public void testSetResourceCancelOrdsFail() throws JsonProcessingException {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.setResourceCancel(
                        new SetResourceCancel()));
    }

    @Test
    public void testGetCodeValuesOrdsFail() throws JsonProcessingException {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getCodeValues(
                        new GetCodeValues()));
    }

    @Test
    public void testSetSyncCompleteOrdsFail() throws JsonProcessingException {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.setSyncComplete(
                        new SetSyncComplete()));
    }

    @Test
    public void testGetUserLoginOrdsFail() throws JsonProcessingException {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getUserLogin(
                        new GetUserLogin()));
    }

    @Test
    public void testGetFileSearchSecureOrdsFail() throws JsonProcessingException {
        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> secureEndpointController.getFileSearchSecure(
                        new GetFileSearchSecure()));
    }

    @Test
    public void testGetCodeValuesSecureOrdsFail() throws JsonProcessingException {
        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> secureEndpointController.getCodesValuesSecure(
                        new GetCodeValuesSecure()));
    }

    @Test
    public void testGetCourtCalendarDetailByDaySecureOrdsFail() throws JsonProcessingException {
        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> secureEndpointController.getCourtCalendarDetailByDaySecure(
                        new GetCourtCalendarDetailByDaySecure()));
    }
}
