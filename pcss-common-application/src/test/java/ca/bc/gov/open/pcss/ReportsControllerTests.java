package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.pcss.controllers.ReportController;
import ca.bc.gov.open.pcss.exceptions.BadRequestException;
import ca.bc.gov.open.wsdl.pcss.one.Lov;
import ca.bc.gov.open.wsdl.pcss.one.Parm;
import ca.bc.gov.open.wsdl.pcss.one.ParmValue;
import ca.bc.gov.open.wsdl.pcss.one.Report;
import ca.bc.gov.open.wsdl.pcss.report.one.Parameters;
import ca.bc.gov.open.wsdl.pcss.report.two.*;
import ca.bc.gov.open.wsdl.pcss.three.YesNoType;
import ca.bc.gov.open.wsdl.pcss.two.GetOperationReport;
import ca.bc.gov.open.wsdl.pcss.two.GetOperationReportLov;
import ca.bc.gov.open.wsdl.pcss.two.GetOperationReportLovRequest;
import ca.bc.gov.open.wsdl.pcss.two.GetOperationReportRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReportsControllerTests {
    @Autowired private ObjectMapper objectMapper;
    @Mock private RestTemplate restTemplateOracle;
    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getOperationReportTest() throws JsonProcessingException {
        var req = new GetOperationReport();
        var one = new GetOperationReportRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetOperationReportRequest();

        two.setRequestDtm(Instant.now());
        two.setRequestPartId("A");
        two.setRequestAgencyIdentifierId("A");

        one.setGetOperationReportRequest(two);
        req.setGetOperationReportRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetOperationReportResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        Report r = new Report();
        r.setReportNm("A");
        r.setReportDscTxt("A");

        Parm p = new Parm();
        p.setParmNm("A");
        p.setVisibleYN(YesNoType.Y);
        p.setMandatoryYN(YesNoType.Y);
        p.setLovYN(YesNoType.Y);
        p.setPromptNm("A");
        p.setSeqNo("A");
        p.setDefaultValTxt("A");
        p.setDataTypeTxt("A");
        p.setFormatMaskTxt("A");
        p.setBindVariableYN(YesNoType.Y);

        r.getParm().add(p);
        out.getReport().add(r);

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                Mockito.any(String.class),
                Mockito.eq(HttpMethod.GET),
                Mockito.<HttpEntity<String>>any(),
                Mockito
                        .<Class<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportResponse>>
                                any()))
                .thenReturn(responseEntity);

        ReportController resourceController =
                new ReportController(restTemplate, restTemplateOracle, objectMapper);
        var resp = resourceController.getOperationReport(req);
        assert resp != null;
    }

    @Test
    public void getOperationReportLovTest() throws JsonProcessingException {
        var req = new GetOperationReportLov();
        var one = new GetOperationReportLovRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setReportNm("A");
        two.setParmNm("A");

        ParmValue pv = new ParmValue();
        pv.setValTxt("A");
        two.getParmValue().add(pv);

        one.setGetOperationReportLovRequest(two);
        req.setGetOperationReportLovRequest(one);

        var out = new ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovResponse();
        out.setResponseCd("A");
        out.setResponseMessageTxt("A");
        Lov lv = new Lov();
        lv.setCd("A");
        lv.setVal("A");
        out.getLov().add(lv);

        ResponseEntity<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                Mockito.any(String.class),
                Mockito.eq(HttpMethod.POST),
                Mockito.<HttpEntity<String>>any(),
                Mockito
                        .<Class<ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovResponse>>
                                any()))
                .thenReturn(responseEntity);

        ReportController resourceController =
                new ReportController(restTemplate, restTemplateOracle, objectMapper);
        var resp = resourceController.getOperationReportLov(req);
        assert resp != null;
    }

    @Test
    public void getJustinReportTestOne() throws JsonProcessingException, BadRequestException {
        var req = new GetJustinReport();
        var one = new GetJustinReportRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.report.one.GetJustinReportRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setReportName("A");
        Parameters p = new Parameters();
        p.setParmNm("A");
        p.setParmValue("A");
        two.getParameters().add(p);

        one.setGetJustinReportRequest(two);
        req.setGetJustinReportRequest(one);

        var out = "Report";

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(out.getBytes(), HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                Mockito.any(URI.class),
                Mockito.eq(HttpMethod.GET),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<Class<byte[]>>any()))
                .thenReturn(responseEntity);

        when(restTemplateOracle.exchange(
                Mockito.any(URI.class),
                Mockito.eq(HttpMethod.GET),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<Class<byte[]>>any()))
                .thenReturn(responseEntity);

        ReportController resourceController =
                new ReportController(restTemplate, restTemplateOracle, objectMapper);
        var resp = resourceController.getJustinReportNameSpaceOne(req);
        assert resp != null;
    }

    @Test
    public void getJustinReportTestTwo() throws JsonProcessingException, BadRequestException {
        var req = new ca.bc.gov.open.wsdl.pcss.report.five.GetJustinReport();
        var one = new ca.bc.gov.open.wsdl.pcss.report.five.GetJustinReportRequest();
        var two = new ca.bc.gov.open.wsdl.pcss.report.one.GetJustinReportRequest();

        two.setRequestAgencyIdentifierId("A");
        two.setRequestPartId("A");
        two.setRequestDtm(Instant.now());
        two.setReportName("A");
        Parameters p = new Parameters();
        p.setParmNm("A");
        p.setParmValue("A");
        two.getParameters().add(p);

        one.setGetJustinReportRequest(two);
        req.setGetJustinReportRequest(one);

        var out = "Report";

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(out.getBytes(), HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                Mockito.any(URI.class),
                Mockito.eq(HttpMethod.GET),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<Class<byte[]>>any()))
                .thenReturn(responseEntity);

        when(restTemplateOracle.exchange(
                Mockito.any(URI.class),
                Mockito.eq(HttpMethod.GET),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<Class<byte[]>>any()))
                .thenReturn(responseEntity);

        ReportController resourceController =
                new ReportController(restTemplate, restTemplateOracle, objectMapper);
        var resp = resourceController.getJustinReportNameSpaceTwo(req);
        assert resp != null;
    }

    @Test
    public void getJustinAdobeReportTest() throws JsonProcessingException {
        var req = new GetJustinAdobeReport();

        //    Create the 15 parameters
        StringBuilder s = new StringBuilder("{");
        for (int i = 1; i < 16; i++) {
            s.append("\"param").append(i).append("\": \"A\",");
        }
        s = new StringBuilder(s.substring(0, s.length() - 1) + "}");
        var one = objectMapper.readValue(s.toString(), GetJustinReportAdobeRequest.class);
        one.setFormNm("A");
        one.setRequestDtm(Instant.now());
        one.setFormNm("A");
        one.setPrintYn("A");
        one.setRequestAgencyIdentifierId("A");

        req.setGetJustinAdobeReportRequest(one);

        var out = "A";
        ResponseEntity<byte[]> responseEntity =
                new ResponseEntity<>(out.getBytes(StandardCharsets.UTF_8), HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                Mockito.any(String.class),
                Mockito.eq(HttpMethod.GET),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<Class<byte[]>>any()))
                .thenReturn(responseEntity);

        Map<String, String> m = new HashMap<>();
        m.put("url", "A");
        ResponseEntity<Map<String, String>> responseEntity2 =
                new ResponseEntity<>(m, HttpStatus.OK);
        when(restTemplate.exchange(
                Mockito.any(String.class),
                Mockito.eq(HttpMethod.POST),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<Map<String, String>>>any()))
                .thenReturn(responseEntity2);

        ReportController resourceController =
                new ReportController(restTemplate, restTemplateOracle, objectMapper);
        var resp = resourceController.getJustinAdobeReport(req);
        assert resp != null;
    }
}
