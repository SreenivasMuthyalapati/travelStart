package test.Booking;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import testmethods.Method;
import testmethods.PaymentPageMethods;
import testmethods.TSMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingFlow {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.dataPath;
    static String environment;
    static String browser;
    static String outputExcel = Paths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";

    // Extracting environment from test data sheet
    static {
        try {
            environment = m.readDataFromExcel(dataPath, "URL's", 1, 1);
            browser = m.readDataFromExcel(dataPath, "URL's", 0, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BookingFlow() throws IOException {
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
        int totalPaxCount = m.getRowCount(dataPath, "Booking Test Cases");

        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 0);
            String shouldRun = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 1);
            String domain = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 2);
            String cpy_source = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 3);
            String tripType = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 4);
            String origin = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 5);
            String destination = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 6);
            String departureDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 7);
            String departureMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 8);
            String returnDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 9);
            String returnMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 10);
            String adultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 11);
            String youngAdultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 12);
            String childCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 13);
            String infantCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 14);
            String isBundled = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 15);
            String departureAirline = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 16);
            String returnAirline = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 17);
            String mailID = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 18);
            String mobileNumber = (m.readDataFromExcel(dataPath, "Booking Test Cases", i, 19));
            String title = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 20);
            String firstName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 21);
            String middleName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 22);
            String lastName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 23);
            String dateOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 24);
            String monthOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 25);
            String yearOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 26);
            String passPortNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 27);
            String dateOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 28);
            String monthOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 29);
            String yearOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 30);
            String passPortNationality = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 31);
            String passPortIssuingCountry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 32);
            String addBaggage = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 33);
            String addFlexi = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 34);
            String whatsapp = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 35);
            String addSeats = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 36);
            String paymentMethod = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 37);
            String bankNameEFT = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 38);
            String isLoggedInUser = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 39);
            String isToBeCancelled = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 40);

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
                    isBundled,
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
                    addBaggage,
                    addFlexi,
                    whatsapp,
                    addSeats,
                    paymentMethod,
                    bankNameEFT,
                    isLoggedInUser,
                    isToBeCancelled});
        }
        return testCase.toArray(new Object[0][]);
    }



    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String cpy_source, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount,String isBundled, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String addFlexi, String whatsApp, String addSeats, String paymentMethod, String bankNameEFT, String isLoggedInUser, String isToBeCancelled) throws IOException, InterruptedException {

        runTime = m.getCurrentTime();
        String testStatus;


        if (!shouldRun.equalsIgnoreCase("Yes")) {
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel("-", 1, outputExcel);
            testStatus = "Skipped";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel("Skipped this test case as this test case is not approved to run", 3, outputExcel);
            m.writeToExcel("-", 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");
        }

        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        }

        // Maximize window
        driver.manage().window().maximize();

        //Printing the test case number
        System.out.println(testCaseNumber + " Executed");

        System.out.println(environment);


        TSMethods bookingFlowMethods = new TSMethods(driver);

        // Setting up URL for ZA domain
//        if (domain.equalsIgnoreCase("ZA")){
//
//            environment = environment.toUpperCase();
//
//        switch (environment) {
//
//            case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 4, 1);
//            case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 6, 1));
//            case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 8, 1));
//            case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 10, 1));
//
//            default -> System.out.println("Invalid environment name");
//
//
//        }
//    }
//            // Setting up URL for NG domain
//        else if (domain.equalsIgnoreCase("NG")) {
//
//            switch (environment) {
//
//                case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 5, 1));
//                case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 7, 1));
//                case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 9, 1));
//                case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 11, 1));
//
//                default -> System.out.println("Invalid envinorment name");
//
//            }
//        }
//        // Setting FS META
//        else if (domain.equalsIgnoreCase("FS")) {
//
//            switch (environment) {
//
//                case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 12, 1));
//                case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 13, 1));
//                case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 14, 1));
//                case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 15, 1));
//
//                default -> System.out.println("Invalid envinorment name");
//
//            }
//        }

        baseURL = m.getBaseURL(environment, domain, cpy_source);


        String CID = Method.generateCID();

        System.out.println("URL: "+baseURL);

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




        if (isLoggedInUser.equalsIgnoreCase("Yes")) {

            bookingFlowMethods.login("Sreenivasulu@travelstart.com", "Test@123");

        }

        bookingFlowMethods.searchFlight(testCaseNumber, tripType, origin, destination, departureDate, departureMonth, returnDate, returnMonth,  adultCount, youngAdultCount, childCount, infantCount);

        bookingFlowMethods.SelectAirline(testCaseNumber, tripType, isBundled, departureAirline, returnAirline);
        bookingFlowMethods.clickBook(testCaseNumber, tripType, isBundled);
        bookingFlowMethods.enterPaxDetails(isLoggedInUser, testCaseNumber, tripType, adultCount, youngAdultCount, childCount, infantCount, departureAirline, returnAirline, mailID, mobileNumber, title, firstName, middleName, lastName, dateOfBirth, monthOfBirth, yearOfBirth, passPortNumber, dateOfPassportExpiry, monthOfPassportExpiry, yearOfPassportExpiry, passPortNationality, passPortIssuingCountry, addBaggage, whatsApp);
        bookingFlowMethods.add_seats(addSeats);
        System.out.println(bookingFlowMethods.getPriceBreakdown((FlightPage.fareBreakdownTables)));
        bookingFlowMethods.add_Addons(domain, addFlexi);
        Thread.sleep(2000);
        //System.out.println(bookingFlowMethods.getPriceBreakdown((FlightPage.fareBreakdownTables)));
        PaymentPageMethods paymentPageMethods = new PaymentPageMethods();
        String bookingRef = paymentPageMethods.getBookingReference(driver);
        bookingFlowMethods.paymentAndBooking(environment, testCaseNumber, domain, paymentMethod, bankNameEFT, isToBeCancelled);



    }


}