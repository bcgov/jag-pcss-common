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
    public void healthPingOrdsFail() {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getPing(
                        new GetPing()));
    }

    @Test
    public void healthHealthOrdsFail() {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getHealth(
                        new GetHealth()));
    }

    @Test
    public void appearanceUpdateOrdsFail() {
        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> appearanceController.setAppearanceUpdate(
                        new SetAppearanceUpdate()));
    }

    @Test
    public void setAppearanceMoveOrdsFail()  {
        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> appearanceController.setAppearanceMove(
                        new SetAppearanceMove()));
    }

    @Test
    public void setAppearanceStatusOrdsFail()  {
        AppearanceController appearanceController =
                new AppearanceController(restTemplate, objectMapper);

    Assertions.assertThrows(
        ORDSException.class, () -> appearanceController.setAppearanceStatus(
                new SetAppearanceStatus()));
    }

    @Test
    public void setCourtListMoveOrdsFail()  {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
            ORDSException.class, () -> courtController.setCourtListMove(
                new SetCourtListMove()));
    }

    @Test
    public void getCourtCalendarDetailByDayOrdsFail()  {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getCourtCalendarDetailByDay(
                        new GetCourtCalendarDetailByDay()));
    }

    @Test
    public void setCourtCalendarOrdsFail()  {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.setCourtCalendar(
                        new SetCourtCalendar()));
    }

    @Test
    public void getReservedJudgmentOrdsFail()  {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getReservedJudgment(
                        new GetReservedJudgment()));
    }

    @Test
    public void getFileSearchOrdsFail()  {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getFileSearch(
                        new GetFileSearch()));
    }

    @Test
    public void getJustinAdobeReportOrdsFail()  {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getJustinAdobeReport(
                        new GetJustinAdobeReport()));
    }

    @Test
    public void getJustinReportOrdsFail()  {
        ReportController reportController = new ReportController(restTemplate, objectMapper);
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
                ORDSException.class, () -> reportController.getJustinReport(req));
    }

    @Test
    public void getJustinReportBadRequestFail()  {
        ReportController reportController = new ReportController(restTemplate, objectMapper);
        var req = new GetJustinReport();

        Assertions.assertThrows(
                BadRequestException.class, () -> reportController.getJustinReport(req));
    }

    @Test
    public void getOperationReportOrdsFail()  {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getOperationReport(
                        new GetOperationReport()));
    }

    @Test
    public void getOperationReportLovOrdsFail()  {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getOperationReportLov(
                        new GetOperationReportLov()));
    }

    @Test
    public void getResourceAvailabilityOrdsFail()  {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getResourceAvailability(
                        new GetResourceAvailability()));
    }

    @Test
    public void setResourceBookingOrdsFail()  {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.setResourceBooking(
                        new SetResourceBooking()));
    }

    @Test
    public void setResourceCancelOrdsFail()  {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.setResourceCancel(
                        new SetResourceCancel()));
    }

    @Test
    public void getCodeValuesOrdsFail()  {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getCodeValues(
                        new GetCodeValues()));
    }

    @Test
    public void setSyncCompleteOrdsFail()  {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.setSyncComplete(
                        new SetSyncComplete()));
    }

    @Test
    public void getUserLoginOrdsFail()  {
        ResourceController resourceController = new ResourceController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> resourceController.getUserLogin(
                        new GetUserLogin()));
    }

    @Test
    public void getFileSearchSecureOrdsFail()  {
        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> secureEndpointController.getFileSearchSecure(
                        new GetFileSearchSecure()));
    }

    @Test
    public void getCodeValuesSecureOrdsFail()  {
        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> secureEndpointController.getCodesValuesSecure(
                        new GetCodeValuesSecure()));
    }

    @Test
    public void getCourtCalendarDetailByDaySecureOrdsFail() {
        SecureEndpointController secureEndpointController =
                new SecureEndpointController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> secureEndpointController.getCourtCalendarDetailByDaySecure(
                        new GetCourtCalendarDetailByDaySecure()));
    }

    @Test
    public void securityTestFail_Then401() throws Exception {
        var response =
                mockMvc.perform(post("/ws").contentType(MediaType.TEXT_XML))
                        .andExpect(status().is4xxClientError())
                        .andReturn();
        Assertions.assertEquals(
                HttpStatus.UNAUTHORIZED.value(), response.getResponse().getStatus());
    }
}
