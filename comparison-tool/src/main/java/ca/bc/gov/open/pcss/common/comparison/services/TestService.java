package ca.bc.gov.open.pcss.common.comparison.services;

import ca.bc.gov.open.pcss.common.comparison.config.DualProtocolSaajSoapMessageFactory;
import ca.bc.gov.open.pcss.common.comparison.config.WebServiceSenderWithAuth;
import ca.bc.gov.open.pcss.models.serializers.InstantSoapConverter;
import ca.bc.gov.open.wsdl.pcss.one.Permission;
import ca.bc.gov.open.wsdl.pcss.one.SearchByCrown;
import ca.bc.gov.open.wsdl.pcss.three.*;
import ca.bc.gov.open.wsdl.pcss.two.*;
import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;
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

    @Value("${host.api-host}")
    private String apiHost;

    @Value("${host.wm-host}")
    private String wmHost;

    @Value("${host.username}")
    private String username;

    @Value("${host.password}")
    private String password;

    private String RAID = "83.0001";
    private String partId = RAID;
    private DomainNmType domainNm = DomainNmType.PROVJUDGE;
    private Instant dtm = Instant.now();

    private PrintWriter fileOutput;
    private static String outputDir = "comparison-tool/results/";

    private int overallDiff = 0;

    public void runCompares() throws IOException {
        System.out.println("INFO: PCSS Common Diff testing started");

        getCourtCalendarCompare();

        getResourceAvailabilityCompare();

        getOperationLovReportCompare();

        getOperationReportCompare();

        getUserLoginCompare();

        getCodeValuesCompare();

        getReservedJudgementCompare();

        // getFileSearch compare is tested with five search modes
        getFileSearchFILENOCompare();
        getFileSearchPARTNAMECompare();
        getFileSearchCROWNCompare();
        getFileSearchJUSTINNOCompare();
        getFileSearchPHYSIDCompare();
    }

    private void getFileSearchPHYSIDCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetFileSearch request = new GetFileSearch();
        GetFileSearchRequest two = new GetFileSearchRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        one.setRequestAgencyIdentifierId(RAID);
        one.setSearchMode(SearchModeType.PHYSID);
        one.setFileDivisionCd(CourtDivisionType.valueOf("I"));
        two.setGetFileSearchRequest(one);
        request.setGetFileSearchRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getFileSearchPHYSID.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetFileSearchPHYSID.txt", "UTF-8");

        for (int idx = 0; scanner.hasNextLine(); idx++) {
            String line = scanner.nextLine();

            System.out.println(
                    "\nINFO: GetFileSearch with SearchType: PHYSID"
                            + " PhysicalFileIdSet: "
                            + line);
            one.setPhysicalFileIdSet(line);
            Permission permission = new Permission();
            permission.setCourtClassCd(CourtClassType.A);
            List<Permission> permissions = new ArrayList();
            permissions.add(permission);
            one.setPermission(permissions);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetFileSearchResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetFileSearch with SearchType: PHYSID"
                                + " PhysicalFileIdSet: "
                                + line
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetFileSearch PHYSID Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetFileSearch PHYSID Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getFileSearchJUSTINNOCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetFileSearch request = new GetFileSearch();
        GetFileSearchRequest two = new GetFileSearchRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        one.setRequestAgencyIdentifierId(RAID);
        one.setSearchMode(SearchModeType.JUSTINNO);
        one.setFileDivisionCd(CourtDivisionType.valueOf("R"));
        two.setGetFileSearchRequest(one);
        request.setGetFileSearchRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getFileSearchJUSTINNO.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetFileSearchJUSTINNO.txt", "UTF-8");

        for (int idx = 0; scanner.hasNextLine(); idx++) {
            String line = scanner.nextLine();

            System.out.println(
                    "\nINFO: GetFileSearch with SearchType: JUSTINNO"
                            + " MdocJustinNoSet: "
                            + line);
            one.setMdocJustinNoSet(line);
            Permission permission = new Permission();
            permission.setCourtClassCd(CourtClassType.A);
            List<Permission> permissions = new ArrayList();
            permissions.add(permission);
            one.setPermission(permissions);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetFileSearchResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetFileSearch with SearchType: JUSTINNO"
                                + " MdocJustinNoSet: "
                                + line
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetFileSearch JUSTINNO Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetFileSearch JUSTINNO Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getFileSearchCROWNCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetFileSearch request = new GetFileSearch();
        GetFileSearchRequest two = new GetFileSearchRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        one.setRequestAgencyIdentifierId(RAID);
        one.setSearchMode(SearchModeType.CROWN);
        one.setFileDivisionCd(CourtDivisionType.valueOf("R"));
        two.setGetFileSearchRequest(one);
        request.setGetFileSearchRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getFileSearchCROWN.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetFileSearchCROWN.txt", "UTF-8");

        for (int idx = 0; scanner.hasNextLine(); idx++) {
            String[] params = scanner.nextLine().split(",");

            System.out.println(
                    "\nINFO: GetFileSearch with SearchType: CROWN"
                            + " CrownPartId: "
                            + params[0]
                            + " FileDesignationCd: "
                            + params[1]);
            SearchByCrown searchByCrown = new SearchByCrown();
            searchByCrown.setCrownPartId(params[0]);
            searchByCrown.setActiveOnlyYN(YesNoType.Y);
            searchByCrown.setFileDesignationCd(FileComplexityType.fromValue(params[1]));
            one.setSearchByCrown(searchByCrown);
            Permission permission = new Permission();
            permission.setCourtClassCd(CourtClassType.A);
            List<Permission> permissions = new ArrayList();
            permissions.add(permission);
            one.setPermission(permissions);

            String[] contextPath = {
                "ca.bc.gov.open.wsdl.pcss.three",
                "ca.bc.gov.open.wsdl.pcss.two",
                "ca.bc.gov.open.wsdl.pcss.one"
            };

            if (!compare(new GetFileSearchResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetFileSearch with SearchType: CROWN"
                                + " CrownPartId: "
                                + params[0]
                                + " FileDesignationCd: "
                                + params[1]
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetFileSearch CROWN Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetFileSearch CROWN Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getFileSearchPARTNAMECompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetFileSearch request = new GetFileSearch();
        GetFileSearchRequest two = new GetFileSearchRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        one.setRequestAgencyIdentifierId(RAID);
        one.setSearchMode(SearchModeType.PARTNAME);
        two.setGetFileSearchRequest(one);
        request.setGetFileSearchRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getFileSearchPARTNAME.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetFileSearchPARTNAME.txt", "UTF-8");

        for (int idx = 0; scanner.hasNextLine(); idx++) {
            String[] params = scanner.nextLine().split("\\|", -1);

            switch (idx % 3) {
                case 0:
                    one.setNameSearchTypeCd(NameSearchType.S);
                    break;
                case 1:
                    one.setNameSearchTypeCd(NameSearchType.E);
                    break;
                case 2:
                    one.setNameSearchTypeCd(NameSearchType.P);
                    break;
                default:
                    break;
            }

            System.out.println(
                    "\nINFO: GetFileSearch with SearchType: PARTNAME"
                            + " NameSearchType: "
                            + one.getNameSearchTypeCd().toString()
                            + " OrgNm: "
                            + params[0]
                            + " LastNm: "
                            + params[1]
                            + " GivenNm: "
                            + params[2]);
            if (params[0] == "" && params[1] == "") {
                System.out.println("WARN: Invalid data - OrgName and LastName both are empty");
                fileOutput.println("WARN: Invalid data - OrgName and LastName both are empty");
            }
            if (params[0] != "") {
                one.setOrgNm(params[0]);
            }
            if (params[1] != "") {
                one.setLastNm(params[1]);
            }
            if (params[2] != "") {
                one.setGivenNm(params[2]);
            }
            Permission permission = new Permission();
            permission.setCourtClassCd(CourtClassType.A);
            List<Permission> permissions = new ArrayList();
            permissions.add(permission);
            one.setPermission(permissions);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetFileSearchResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetFileSearch with SearchType: PARTNAME"
                                + " NameSearchType: "
                                + one.getNameSearchTypeCd().toString()
                                + " OrgNm: "
                                + params[0]
                                + " LastNm: "
                                + params[1]
                                + " GivenNm: "
                                + params[2]
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetFileSearch PARTNAME Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetFileSearch PARTNAME Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getFileSearchFILENOCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetFileSearch request = new GetFileSearch();
        GetFileSearchRequest two = new GetFileSearchRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetFileSearchRequest();
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        one.setRequestAgencyIdentifierId(RAID);
        one.setSearchMode(SearchModeType.FILENO);
        two.setGetFileSearchRequest(one);
        request.setGetFileSearchRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getFileSearchFILENO.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetFileSearchFILENO.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] params = line.split(",");

            System.out.println(
                    "\nINFO: GetFileSearch with SearchType: FILENO"
                            + " FileHomeAgencyId: "
                            + params[0]
                            + " FileNumberTxt: "
                            + params[1]);
            one.setFileHomeAgencyId(params[0]);
            one.setFileNumberTxt(params[1]);
            Permission permission = new Permission();
            permission.setCourtClassCd(CourtClassType.A);
            List<Permission> permissions = new ArrayList();
            permissions.add(permission);
            one.setPermission(permissions);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetFileSearchResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetFileSearch with SearchType: FILENO"
                                + " FileHomeAgencyId: "
                                + params[0]
                                + " FileNumberTxt: "
                                + params[1]
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetFileSearch FILENO Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetFileSearch FILENO Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getReservedJudgementCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetReservedJudgment request = new GetReservedJudgment();
        GetReservedJudgmentRequest two = new GetReservedJudgmentRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetReservedJudgmentRequest();
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        two.setGetReservedJudgmentRequest(one);
        request.setGetReservedJudgmentRequest(two);

        fileOutput = new PrintWriter(outputDir + "GetReservedJudgment.txt", "UTF-8");
        String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

        System.out.println("\nINFO: GetReservedJudgment");
        if (!compare(new GetReservedJudgmentResponse(), request, contextPath)) {
            fileOutput.println("INFO: GetReservedJudgment\n\n");
            ++diffCounter;
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetReservedJudgment Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetReservedJudgment Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getCodeValuesCompare() throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetCodeValues request = new GetCodeValues();
        GetCodeValuesRequest two = new GetCodeValuesRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetCodeValuesRequest();
        one.setRequestDtm(InstantSoapConverter.parse("2021-09-14 09:26:56.6"));
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestPartId("19014.0001");
        one.setLastRetrievedDate(InstantSoapConverter.parse("2021-09-14 09:26:56.6"));
        two.setGetCodeValuesRequest(one);
        request.setGetCodeValuesRequest(two);

        fileOutput = new PrintWriter(outputDir + "GetCodeValues.txt", "UTF-8");
        String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

        System.out.println("\nINFO: GetCodeValues");
        if (!compare(new GetCodeValuesResponse(), request, contextPath)) {
            fileOutput.println("INFO: GetCodeValues\n\n");
            ++diffCounter;
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetCodeValues Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetCodeValues Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getUserLoginCompare() throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetUserLogin request = new GetUserLogin();
        GetUserLoginRequest two = new GetUserLoginRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetUserLoginRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetUserLoginRequest();
        one.setRequestDtm(dtm);
        one.setDomainNm(domainNm);
        two.setGetUserLoginRequest(one);
        request.setGetUserLoginRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getUserLogin.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetUserLogin.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] params = line.split(",");

            System.out.println(
                    "\nINFO: GetUserLogin with DomainUserId: "
                            + params[0]
                            + " DomainUserGuid: "
                            + params[1]);
            one.setDomainUserId(params[0]);
            one.setDomainUserGuid(params[1]);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetUserLoginResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetUserLogin with DomainUserId: "
                                + params[0]
                                + " DomainUserGuid: "
                                + params[1]
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetUserLogin Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetUserLogin Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getOperationReportCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetOperationReport request = new GetOperationReport();
        GetOperationReportRequest two = new GetOperationReportRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetOperationReportRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetOperationReportRequest();
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestDtm(dtm);
        two.setGetOperationReportRequest(one);
        request.setGetOperationReportRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getOperationReportPartId.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetOperationReport.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            System.out.println("\nINFO: GetOperationReport with PartId: " + line);
            one.setRequestPartId(line);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetOperationReportResponse(), request, contextPath)) {
                fileOutput.println("INFO: GetOperationReport with PartId: " + line + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetOperationReport Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetOperationReport Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getOperationLovReportCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetOperationReportLov request = new GetOperationReportLov();
        GetOperationReportLovRequest two = new GetOperationReportLovRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetOperationReportLovRequest();
        one.setRequestDtm(dtm);
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestPartId("19014.0001");
        two.setGetOperationReportLovRequest(one);
        request.setGetOperationReportLovRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getOperationLovReport.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetOperationReportLov.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] params = line.split(",");

            System.out.println(
                    "\nINFO: GetOperationReportLov with"
                            + " ReportNm: "
                            + params[0]
                            + " ParmNm: "
                            + params[1]);
            one.setReportNm(params[0]);
            one.setParmNm(params[1]);

            String[] contextPath = {"ca.bc.gov.open.wsdl.pcss.two", "ca.bc.gov.open.wsdl.pcss.one"};

            if (!compare(new GetOperationReportLovResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetOperationReportLov with"
                                + " ReportNm: "
                                + params[0]
                                + " ParmNm: "
                                + params[1]
                                + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetOperationReportLov Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetOperationReportLov Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        overallDiff += diffCounter;
        fileOutput.close();
    }

    private void getResourceAvailabilityCompare()
            throws FileNotFoundException, UnsupportedEncodingException {
        int diffCounter = 0;

        GetResourceAvailability request = new GetResourceAvailability();
        GetResourceAvailabilityRequest two = new GetResourceAvailabilityRequest();
        ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetResourceAvailabilityRequest();
        one.setRequestDtm(dtm);
        one.setRequestPartId(partId);
        one.setRequestAgencyIdentifierId(RAID);
        one.setModeCd(OperationModeRscAvType.W);
        one.setBookingDt(dtm);
        two.setGetResourceAvailabilityRequest(one);
        request.setGetResourceAvailabilityRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getFileSearchPrimaryAgencyId.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetResourceAvailability.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            System.out.println("\nINFO: GetResourceAvailability with PrimaryAgencyId: " + line);
            one.setPrimaryAgencyId(line);

            String[] contextPath = {
                "ca.bc.gov.open.wsdl.pcss.three",
                "ca.bc.gov.open.wsdl.pcss.two",
                "ca.bc.gov.open.wsdl.pcss.one"
            };

            if (!compare(new GetResourceAvailabilityResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetResourceAvailability with PrimaryAgencyId: " + line + "\n\n");
                ++diffCounter;
            }
        }

        System.out.println(
                "########################################################\n"
                        + "INFO: GetResourceAvailability Completed there are "
                        + diffCounter
                        + " diffs\n"
                        + "########################################################");

        fileOutput.println(
                "########################################################\n"
                        + "INFO: GetResourceAvailability Completed there are "
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
        ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest one =
                new ca.bc.gov.open.wsdl.pcss.one.GetCourtCalendarDetailByDayRequest();
        one.setRequestDtm(dtm);
        one.setRequestAgencyIdentifierId(RAID);
        one.setRequestPartId(partId);
        two.setGetCourtCalendarDetailByDayRequest(one);
        request.setGetCourtCalendarDetailByDayRequest(two);

        InputStream inputIds = getClass().getResourceAsStream("/getCourtCalendar.csv");
        assert inputIds != null;
        Scanner scanner = new Scanner(inputIds);

        fileOutput = new PrintWriter(outputDir + "GetCourtCalendarDetailByDay.txt", "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] params = line.split(",");

            System.out.println(
                    "\nINFO: GetCourtCalendarDetailByDay with CourtAgencyId: "
                            + params[0]
                            + " CourtRoomCd: "
                            + params[1]
                            + " AppearanceDt: "
                            + params[2]);
            one.setCourtAgencyId(params[0]);
            one.setCourtRoomCd(params[1]);
            one.setAppearanceDt(InstantSoapConverter.parse(params[2]));

            String[] contextPath = {
                "ca.bc.gov.open.wsdl.pcss.three",
                "ca.bc.gov.open.wsdl.pcss.two",
                "ca.bc.gov.open.wsdl.pcss.one"
            };

            if (!compare(new GetCourtCalendarDetailByDayResponse(), request, contextPath)) {
                fileOutput.println(
                        "INFO: GetCourtCalendarDetailByDay with CourtAgencyId: "
                                + params[0]
                                + " CourtRoomCd: "
                                + params[1]
                                + " AppearanceDt: "
                                + params[2]
                                + "\n\n");
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
            Thread.sleep(5000);

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
        List<Change> actualChanges = new ArrayList<>(diff.getChanges());

        actualChanges.removeIf(
                c -> {
                    if (c instanceof ValueChange) {
                        return ((ValueChange) c).getLeft() == null
                                && ((ValueChange) c).getRight().toString().isBlank();
                    }

                    return false;
                });

        int diffSize = actualChanges.size();

        if (diffSize == 0) {
            System.out.println("Only null and blank changes detected");
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
