package ca.bc.gov.open.pcss.common.comparison.services;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import ca.bc.gov.open.pcss.common.comparison.config.DualProtocolSaajSoapMessageFactory;
import ca.bc.gov.open.pcss.common.comparison.config.WebServiceSenderWithAuth;
import ca.bc.gov.open.pcss.models.serializers.InstantSoapConverter;
import ca.bc.gov.open.wsdl.pcss.two.GetCourtCalendarDetailByDay;
import ca.bc.gov.open.wsdl.pcss.two.GetCourtCalendarDetailByDayRequest;
import ca.bc.gov.open.wsdl.pcss.two.GetCourtCalendarDetailByDayResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Service
public class TestService {
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    private final WebServiceSenderWithAuth webServiceSenderWithAuth;

    private final Javers javers = JaversBuilder.javers().build();

    @Autowired
    public TestService(WebServiceSenderWithAuth webServiceSenderWithAuth) {
        this.webServiceSenderWithAuth = webServiceSenderWithAuth;
    }

    @Value("${host.api_host}")
    private String apiHost;

    @Value("${host.wm_host}")
    private String wmHost;

    @Value("${host.username}")
    private String username;

    @Value("${host.password}")
    private String password;

    private String RAID = "83.0001";
    private String partId = RAID;
    private Instant dtm = Instant.now();

    private PrintWriter fileOutput;
    private static String outputDir = "comparison-tool/results/";

    private int overallDiff = 0;

    public void runCompares() throws IOException {
        System.out.println("INFO: PCSS Common Diff testing started");

        getCourtCalendarCompare();

        //getFileSearchCompare();
    }

    private void getFileSearchCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetCourtCalendarDetailByDay request = new GetCourtCalendarDetailByDay();
        GetCourtCalendarDetailByDayRequest two = new GetCourtCalendarDetailByDayRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest one
                = new ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest();
        one.setRequestDtm(dtm);
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestPartId(partId);
        two.setGetCourtCalendarDetailByDayRequest(one);
        request.setGetCourtCalendarDetailByDayRequest(two);

        InputStream inputIds =
                getClass().getResourceAsStream("/getCourtCalendar.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetCourtCalendarDetailByDay.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] params = line.split(",");

            System.out.println("\nINFO: GetCourtCalendarDetailByDay with CourtAgencyId: "
                    + params[0]
                    + " CourtRoomCd: " + params[1]
                    + " AppearanceDt: " + params[2]);
            one.setCourtAgencyId(params[0]);
            one.setCourtRoomCd(params[1]);
            one.setAppearanceDt(InstantSoapConverter.parse(params[2]));

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.three",
                    "ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetCourtCalendarDetailByDayResponse(), request, contextPath)) {
                fileOutput.println("INFO: GetCourtCalendarDetailByDay with CourtAgencyId: "
                        + params[0]
                        + " CourtRoomCd: " + params[1]
                        + " AppearanceDt: " + params[2] + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetCourtCalendarDetailByDay Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetCourtCalendarDetailByDay Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getCourtCalendarCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetCourtCalendarDetailByDay request = new GetCourtCalendarDetailByDay();
        GetCourtCalendarDetailByDayRequest two = new GetCourtCalendarDetailByDayRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest one
                = new ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest();
        one.setRequestDtm(dtm);
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestPartId(partId);
        two.setGetCourtCalendarDetailByDayRequest(one);
        request.setGetCourtCalendarDetailByDayRequest(two);

        InputStream inputIds =
                getClass().getResourceAsStream("/getCourtCalendar.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetCourtCalendarDetailByDay.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] params = line.split(",");

            System.out.println("\nINFO: GetCourtCalendarDetailByDay with CourtAgencyId: "
                    + params[0]
                    + " CourtRoomCd: " + params[1]
                    + " AppearanceDt: " + params[2]);
            one.setCourtAgencyId(params[0]);
            one.setCourtRoomCd(params[1]);
            one.setAppearanceDt(InstantSoapConverter.parse(params[2]));

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.three",
                    "ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetCourtCalendarDetailByDayResponse(), request, contextPath)) {
                fileOutput.println("INFO: GetCourtCalendarDetailByDay with CourtAgencyId: "
                    + params[0]
                    + " CourtRoomCd: " + params[1]
                    + " AppearanceDt: " + params[2] + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetCourtCalendarDetailByDay Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetCourtCalendarDetailByDay Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    public <T, G> boolean compare(T response, G request, String[] contextPath) {

        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();

        DualProtocolSaajSoapMessageFactory saajSoapMessageFactory =
                new DualProtocolSaajSoapMessageFactory();
        saajSoapMessageFactory.setSoapVersion(SoapVersion.SOAP_12);
        saajSoapMessageFactory.afterPropertiesSet();

        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setCredentials(
                new UsernamePasswordCredentials(username, password));

        webServiceTemplate.setMessageSender(webServiceSenderWithAuth);
        webServiceTemplate.setMessageFactory(saajSoapMessageFactory);
        jaxb2Marshaller.setContextPaths(contextPath);
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.afterPropertiesSet();

        T resultObjectWM = null;
        T resultObjectAPI = null;

        try {
            resultObjectWM = (T) webServiceTemplate.marshalSendAndReceive(wmHost, request);
            resultObjectAPI = (T) webServiceTemplate.marshalSendAndReceive(apiHost, request);

        } catch (Exception e) {
            System.out.println("ERROR: Failed to send request... " + e);
            fileOutput.println("ERROR: Failed to send request... " + e);
        }

        Diff diff = javers.compare(resultObjectAPI, resultObjectWM);

        String responseClassName = response.getClass().getName();
        if (diff.hasChanges()) {
            printDiff(diff);
            return false;
        } else {
            if (resultObjectAPI == null && resultObjectWM == null)
                System.out.println(
                        "WARN: "
                                + responseClassName.substring(
                                        responseClassName.lastIndexOf('.') + 1)
                                + ": NULL responses");
            else
                System.out.println(
                        "INFO: "
                                + responseClassName.substring(
                                        responseClassName.lastIndexOf('.') + 1)
                                + ": No Diff Detected");
            return true;
        }
    }

    private void printDiff(Diff diff) {
        int diffSize = diff.getChanges().size();
        if (diffSize == 0) {
            return;
        }

        String[] header = new String[] {"Property", "API Response", "WM Response"};
        String[][] table = new String[diffSize + 1][3];
        table[0] = header;

        for (int i = 0; i < diffSize; ++i) {
            Change ch = diff.getChanges().get(i);

            if (ch instanceof ListChange) {
                String apiVal =
                        ((ListChange) ch).getLeft() == null
                                ? "null"
                                : ((ListChange) ch).getLeft().toString();
                String wmVal =
                        ((ListChange) ch).getRight() == null
                                ? "null"
                                : ((ListChange) ch).getRight().toString();
                table[i + 1][0] = ((ListChange) ch).getPropertyNameWithPath();
                table[i + 1][1] = apiVal;
                table[i + 1][2] = wmVal;
            } else if (ch instanceof ValueChange) {
                String apiVal =
                        ((ValueChange) ch).getLeft() == null
                                ? "null"
                                : ((ValueChange) ch).getLeft().toString();
                String wmVal =
                        ((ValueChange) ch).getRight() == null
                                ? "null"
                                : ((ValueChange) ch).getRight().toString();
                table[i + 1][0] = ((ValueChange) ch).getPropertyNameWithPath();
                table[i + 1][1] = apiVal;
                table[i + 1][2] = wmVal;
            }
        }

        boolean leftJustifiedRows = false;
        int totalColumnLength = 10;
        /*
         * Calculate appropriate Length of each column by looking at width of data in
         * each column.
         *
         * Map columnLengths is <column_number, column_length>
         */
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(table)
                .forEach(
                        a ->
                                Stream.iterate(0, (i -> i < a.length), (i -> ++i))
                                        .forEach(
                                                i -> {
                                                    if (columnLengths.get(i) == null) {
                                                        columnLengths.put(i, 0);
                                                    }
                                                    if (columnLengths.get(i) < a[i].length()) {
                                                        columnLengths.put(i, a[i].length());
                                                    }
                                                }));

        for (Map.Entry<Integer, Integer> e : columnLengths.entrySet()) {
            totalColumnLength += e.getValue();
        }
        fileOutput.println("=".repeat(totalColumnLength));
        System.out.println("=".repeat(totalColumnLength));

        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream()
                .forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");

        Stream.iterate(0, (i -> i < table.length), (i -> ++i))
                .forEach(
                        a -> {
                            fileOutput.printf(formatString.toString(), table[a]);
                            System.out.printf(formatString.toString(), table[a]);
                        });

        fileOutput.println("=".repeat(totalColumnLength));
        System.out.println("=".repeat(totalColumnLength));
    }
}
