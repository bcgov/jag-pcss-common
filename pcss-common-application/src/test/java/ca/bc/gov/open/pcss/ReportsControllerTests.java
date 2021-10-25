package ca.bc.gov.open.pcss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.pcss.controllers.ReportController;
import ca.bc.gov.open.wsdl.pcss.one.Parm;
import ca.bc.gov.open.wsdl.pcss.one.Report;
import ca.bc.gov.open.wsdl.pcss.report.two.*;
import ca.bc.gov.open.wsdl.pcss.three.YesNoType;
import ca.bc.gov.open.wsdl.pcss.two.GetOperationReport;
import ca.bc.gov.open.wsdl.pcss.two.GetOperationReportRequest;
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
public class ReportsControllerTests {
    @Autowired private ObjectMapper objectMapper;

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

        r.setParm(Collections.singletonList(p));
        out.setReport(Collections.singletonList(r));

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

        ReportController resourceController = new ReportController(restTemplate, objectMapper);
        var resp = resourceController.getOperationReport(req);
        assert resp != null;
    }

    @Test
    public void getJustinAdobeReportTest() throws JsonProcessingException {
        var req = new GetJustinAdobeReport();

        //    Create the 15 parameters
        String s = "{";
        for (int i = 1; i < 16; i++) {
            s += "\"param" + i + "\": \"A\",";
        }
        s = s.substring(0, s.length() - 1) + "}";
        var one = objectMapper.readValue(s, GetJustinReportAdobeRequest.class);
        one.setFormNm("A");
        one.setRequestDtm("A");
        one.setFormNm("A");
        one.setPrintYn("A");
        one.setRequestAgencyIdentifierId("A");

        req.setGetJustinAdobeReportRequest(one);

        var out = new GetJustinReportAdobeResponse();
        out.setReportContent("A");
        out.setResponseMessageTxt("A");
        out.setResponseCd("A");

        ResponseEntity<GetJustinReportAdobeResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetJustinReportAdobeResponse>>any()))
                .thenReturn(responseEntity);

        ReportController resourceController = new ReportController(restTemplate, objectMapper);
        var resp = resourceController.getJustinAdobeReport(req);
        assert resp != null;
    }
}
