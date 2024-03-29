package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.open.pcss.controllers.*;
import ca.bc.gov.open.pcss.exceptions.BadRequestException;
import ca.bc.gov.open.pcss.exceptions.ORDSException;
import ca.bc.gov.open.wsdl.pcss.report.two.GetJustinAdobeReport;
import ca.bc.gov.open.wsdl.pcss.report.two.GetJustinReport;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetCodeValuesSecure;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetCourtCalendarDetailByDaySecure;
import ca.bc.gov.open.wsdl.pcss.secure.two.GetFileSearchSecure;
import ca.bc.gov.open.wsdl.pcss.two.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrdsErrorTests {
    @Autowired private MockMvc mockMvc;

    @Mock private ObjectMapper objectMapper;
    @Mock private RestTemplate restTemplate;
    @Mock private RestTemplate restTemplateOracle;
    @Mock private HealthController healthController;
    @Mock private AppearanceController appearanceController;
    @Mock private CourtController courtController;
    @Mock private ReportController reportController;
    @Mock private ResourceController resourceController;
    @Mock private SecureEndpointController secureEndpointController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        healthController = Mockito.spy(new HealthController(restTemplate, objectMapper));
        appearanceController = Mockito.spy(new AppearanceController(restTemplate, objectMapper));
        courtController = Mockito.spy(new CourtController(restTemplate, objectMapper));
        reportController = Mockito.spy(new ReportController(restTemplate, restTemplateOracle, objectMapper));
        resourceController = Mockito.spy(new ResourceController(restTemplate, objectMapper));
        secureEndpointController = Mockito.spy(new SecureEndpointController(restTemplate, objectMapper));
    }

    @Test
    public void testHealthPingOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            healthController.getPing(new GetPing());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testHealthHealthOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            healthController.getHealth(new GetHealth());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testsetAppearanceUpdateOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            appearanceController.setAppearanceUpdate(new SetAppearanceUpdate());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetAppearanceMoveOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            appearanceController.setAppearanceMove(new SetAppearanceMove());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetAppearanceStatusOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            appearanceController.setAppearanceStatus(new SetAppearanceStatus());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetCourtListMoveOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            courtController.setCourtListMove(new SetCourtListMove());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetCourtCalendarDetailByDayOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            courtController.getCourtCalendarDetailByDay(new GetCourtCalendarDetailByDay());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetCourtCalendarOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            courtController.setCourtCalendar(new SetCourtCalendar());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetReservedJudgmentOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            courtController.getReservedJudgment(new GetReservedJudgment());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetFileSearchOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            courtController.getFileSearch(new GetFileSearch());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetJustinAdobeReportOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            reportController.getJustinAdobeReport(new GetJustinAdobeReport());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetJustinReportOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            reportController.getJustinReportNameSpaceOne(new GetJustinReport());
        } catch (ORDSException | BadRequestException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetOperationReportOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            reportController.getOperationReport(new GetOperationReport());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetOperationReportLovOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            reportController.getOperationReportLov(new GetOperationReportLov());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetResourceAvailabilityOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            resourceController.getResourceAvailability(new GetResourceAvailability());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetResourceBookingOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            resourceController.setResourceBooking(new SetResourceBooking());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetResourceCancelOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            resourceController.setResourceCancel(new SetResourceCancel());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetCodeValuesOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            resourceController.getCodeValues(new GetCodeValues());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testSetSyncCompleteOrdsFail() throws JsonProcessingException {
          // Set up to mock ords response
        setUpRestTemplate();

        try {
            resourceController.setSyncComplete(new SetSyncComplete());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetUserLoginOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            resourceController.getUserLogin(new GetUserLogin());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetFileSearchSecureOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            secureEndpointController.getFileSearchSecure(new GetFileSearchSecure());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetCodeValuesSecureOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            secureEndpointController.getCodesValuesSecure(new GetCodeValuesSecure());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testGetCourtCalendarDetailByDaySecureOrdsFail() throws JsonProcessingException {
        // Set up to mock ords response
        setUpRestTemplate();

        try {
            secureEndpointController.getCourtCalendarDetailByDaySecure(
                    new GetCourtCalendarDetailByDaySecure());
        } catch (ORDSException ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    private void setUpRestTemplate() {
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<Object>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(URI.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<Object>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<ParameterizedTypeReference<Map<String, String>>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<Object>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<Object>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.DELETE),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<Object>>any()))
                .thenThrow(new RestClientException("BAD"));
    }

    @Test
    public void securityTestFail_Then401() throws Exception {
        mockMvc.perform(post("/ws").contentType(MediaType.TEXT_XML))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
