package testClasses.Booking;

import configs.dataPaths;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.AddOnsPage;
import testMethods.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingFlowTest {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = dataPaths.B2CBookingE2ETestData;
    static String environment;
    static String browser;
    static String outputExcel = configs.dataPaths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";
    static ExcelUtils excelUtils = new ExcelUtils();
    static SeatsPageMethods seatsPageMethods = new SeatsPageMethods();
    static AddOnsPageMethods addOnsPageMethods = new AddOnsPageMethods();

    // Extracting environment from test data sheet
    static {
        try {
            environment = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 1, 1);
            browser = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 0, 1);
        } catch (IOException e) {

            System.out.println("Failed to get URL or Browser data");
            throw new RuntimeException(e);
        }
    }


    public BookingFlowTest() throws IOException {
    }


    @AfterMethod
    public void close(ITestResult result) {
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            // If test fails and driver is not null, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }
        if (driver != null) {

            driver.quit();

        }
    }




    @DataProvider(name = "TestCase")
    public Object[][] getTestCase() throws IOException {
        List<Object[]> testCase = new ArrayList<>();

        // Extracting all test data from test cases in test data sheet
        int totalPaxCount = excelUtils.getRowCount(dataPath, "Booking Scenarios");

        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 0);
            String shouldRun = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 1);
            String domain = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 2);
            String cpy_source = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 3);
            String tripType = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 4);
            String origin = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 5);
            String destination = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 6);
            String departureDate = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 7);
            String departureMonth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 8);
            String returnDate = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 9);
            String returnMonth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 10);
            String adultCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 11);
            String youngAdultCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 12);
            String childCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 13);
            String infantCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 14);
            String departureAirline = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 15);
            String returnAirline = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 16);
            String mailID = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 17);
            String mobileNumber = (excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 18));
            String title = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 19);
            String firstName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 20);
            String middleName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 21);
            String lastName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 22);
            String dateOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 23);
            String monthOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 24);
            String yearOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 25);
            String passPortNumber = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 26);
            String dateOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 27);
            String monthOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 28);
            String yearOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 29);
            String passPortNationality = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 30);
            String passPortIssuingCountry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 31);
            String paymentMethod = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 32);
            String bankNameEFT = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 33);
            String isLoggedInUser = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 34);
            String isToBeCancelled = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 35);

            testCase.add(new Object[]{testCaseNumber,
                    shouldRun,
                    domain,
                    cpy_source,
                    tripType,
                    origin,
                    destination,
                    departureDate,
                    departureMonth,
                    returnDate,
                    returnMonth,
                    adultCount,
                    youngAdultCount,
                    childCount,
                    infantCount,
                    departureAirline,
                    returnAirline,
                    mailID,
                    mobileNumber,
                    title,
                    firstName,
                    middleName,
                    lastName,
                    dateOfBirth,
                    monthOfBirth,
                    yearOfBirth,
                    passPortNumber,
                    dateOfPassportExpiry,
                    monthOfPassportExpiry,
                    yearOfPassportExpiry,
                    passPortNationality,
                    passPortIssuingCountry,
                    paymentMethod,
                    bankNameEFT,
                    isLoggedInUser,
                    isToBeCancelled});
        }
        return testCase.toArray(new Object[0][]);
    }



    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String cpy_source, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String paymentMethod, String bankNameEFT, String isLoggedInUser, String isToBeCancelled) throws IOException, InterruptedException {

        runTime = m.getCurrentTime();

        if (!shouldRun.equalsIgnoreCase("Yes")) {

            System.out.println("Skipped this test");
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");

        }

        driver = m.launchBrowser(driver, browser);

        // Maximize window
        driver.manage().window().maximize();

        //Printing the test case number
        System.out.println(testCaseNumber + " Executed");


        TSMethods bookingFlowMethods = new TSMethods(driver);
        boolean isBundled = m.isBundled(domain, tripType, origin, destination);


        baseURL = m.getBaseURL(environment, domain, cpy_source);


        String CID = "";

        // Launch URL
        driver.get(baseURL);
        Thread.sleep(300);


        //accept all cookies
        driver.manage().deleteAllCookies();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {

        }

        Thread.sleep(2000);


        HomePageMethods homePageMethods = new HomePageMethods();
        SRPMethods srpMethods = new SRPMethods();


        if (isLoggedInUser.equalsIgnoreCase("Yes")) {

            bookingFlowMethods.login("Sreenivasulu@travelstart.com", "Test@123");

        }


        homePageMethods.makeFlightSearch(driver,tripType, origin, destination, departureMonth, departureDate, returnMonth, returnDate, adultCount, youngAdultCount, childCount, infantCount, "Economy");

        CID = m.getCID(driver);

        Assert.assertTrue(srpMethods.isSRPLoaded(driver), "Result not loaded");

        srpMethods.openFilters(driver, isBundled);

        srpMethods.selectAirlineFilter(driver, tripType, isBundled, departureAirline, returnAirline);

        srpMethods.applyFilters(driver);

        srpMethods.selectUnbundledFlights(driver, isBundled);



        srpMethods.proceedToTravellerPage(driver, isBundled);


        TravellerDetailsPageMethods travellerDetailsPageMethods = new TravellerDetailsPageMethods();

        boolean isTravellerPageLoaded = travellerDetailsPageMethods.isTravellerPageLoaded(driver);

        boolean isFareIncreased = false;

        double increasedFare = 0;

        isFareIncreased = travellerDetailsPageMethods.isFareIncreased(driver);

        double flightCost = travellerDetailsPageMethods.getFlightCost(driver);

        System.out.println(flightCost);

        double whatsappPrice = travellerDetailsPageMethods.selectWhatsapp(driver, true);

        travellerDetailsPageMethods.enterContactDetails(driver, mobileNumber, mailID);

        travellerDetailsPageMethods.enterPaxDetails(driver, tripType, departureAirline, returnAirline, title, firstName, lastName, dateOfBirth, monthOfBirth, yearOfBirth, passPortNumber, dateOfPassportExpiry, monthOfPassportExpiry, yearOfPassportExpiry, adultCount, youngAdultCount, childCount, infantCount);

        boolean isBaggageOffered = travellerDetailsPageMethods.isBaggageSelectionOffered(driver);
        String baggageType = "";

        if (isBaggageOffered){

            baggageType = travellerDetailsPageMethods.getBaggageType(driver, isBaggageOffered);
            travellerDetailsPageMethods.addBaggage(driver, baggageType, adultCount, youngAdultCount, childCount);
            Thread.sleep(5000);
        }

        boolean isMealsOffered = travellerDetailsPageMethods.isMealsOffered(driver);

        if(isMealsOffered){

            System.out.println(travellerDetailsPageMethods.selectMeal(driver, adultCount, youngAdultCount, childCount, infantCount));

            }

        Thread.sleep(5000);

        boolean isSeatsOffered = travellerDetailsPageMethods.isSeatsOffered(driver);
        boolean isAddOnsOffered = travellerDetailsPageMethods.isAddOnsOffered(driver);

        boolean selectSeats = true;

        travellerDetailsPageMethods.contiueToNextStep(driver);

        Thread.sleep(1000);

        boolean isSeatsMapLoaded = false;

        if (isSeatsOffered){

            isSeatsMapLoaded = seatsPageMethods.verifySeatsDisplayed(driver);
            Assert.assertTrue(isSeatsMapLoaded, "Seats failed");

        }


        if (isSeatsOffered && isSeatsMapLoaded){

            if (selectSeats){

                seatsPageMethods.selectSeats(driver, true);

                Thread.sleep(5000);

                // Get all selected seats
                System.out.println(seatsPageMethods.getSelectedSeatNumbers(driver));
                System.out.println(seatsPageMethods.getTotalSeatsCost(driver));

                seatsPageMethods.continueToNextStep(driver);

            }else {

                seatsPageMethods.skipSeats(driver, isSeatsOffered);

            }



        }




        boolean isAddOnsAvailable = false;
        isAddOnsAvailable = addOnsPageMethods.isAddOnsLoaded(driver, isAddOnsOffered);

        if (isAddOnsAvailable){

            addOnsPageMethods.unselectAllAddOns(driver);

            addOnsPageMethods.selectMedicalCancellationProduct(driver, true);

        }

        Map<String, String> breakDownOnAddOnsPage = addOnsPageMethods.getPriceBreakdown(driver);

        System.out.println(breakDownOnAddOnsPage);

        addOnsPageMethods.continueToNextStep(driver);

        }




    }



