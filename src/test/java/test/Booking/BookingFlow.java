package test.Booking;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.*;
import testmethods.Method;
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
    static String browser;
    static String environment;
    static String outputExcel = Paths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";

    // Extracting environment and browser details from test data sheet
    static {
        try {
            browser = m.readDataFromExcel(dataPath, "URL's", 0, 1).toUpperCase();
            environment = m.readDataFromExcel(dataPath, "URL's", 1, 1).toUpperCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BookingFlow() throws IOException {
    }


    @AfterMethod
    public void close(ITestResult result) {

        // If test fails and driver is not null, print the correlation ID
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {

            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }

        // Closes all active windows in the automation session
        if (driver != null) {

            driver.quit();

        }

    }



    @DataProvider(name = "TestCase")
    public Object[][] getTestCase() throws IOException {

        // Extracting all test data from test cases in test data sheet
        // Creating a list to store all the test data
        List<Object[]> testCase = new ArrayList<>();

        // Extracting test case count from data sheet
        int totalPaxCount = m.getRowCount(dataPath, "Booking Test Cases");

        // Iterates whole test data and stores in variables
        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 0);
            String shouldRun = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 1);
            String domain = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 2);
            String tripType = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 3);
            String origin = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 4);
            String destination = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 5);
            String departureDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 6);
            String departureMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 7);
            String returnDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 8);
            String returnMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 9);
            String adultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 10);
            String youngAdultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 11);
            String childCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 12);
            String infantCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 13);
            String isBundled = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 14);
            String departureAirline = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 15);
            String returnAirline = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 16);
            String mailID = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 17);
            String mobileNumber = (m.readDataFromExcel(dataPath, "Booking Test Cases", i, 18));
            String title = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 19);
            String firstName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 20);
            String middleName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 21);
            String lastName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 22);
            String dateOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 23);
            String monthOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 24);
            String yearOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 25);
            String passPortNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 26);
            String dateOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 27);
            String monthOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 28);
            String yearOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 29);
            String passPortNationality = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 30);
            String passPortIssuingCountry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 31);
            String addBaggage = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 32);
            String addFlexi = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 33);
            String whatsapp = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 34);
            String paymentMethod = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 35);
            String bankNameEFT = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 36);
            String toBeCancelled = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 37);

            // Adding test data stored variables into test case list
            testCase.add(new Object[]{testCaseNumber,
                    shouldRun,
                    domain,
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
                    paymentMethod,
                    bankNameEFT,
                    toBeCancelled});
        }

        // Returns multiple data at every iteration
        return testCase.toArray(new Object[0][]);
    }

    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount,String isBundled, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String addFlexi, String whatsApp, String paymentMethod, String bankNameEFT, String toBeCancelled) throws IOException, InterruptedException {

        // Storing runtime into a variable
        runTime = m.getCurrentTime();

        String testStatus;

        // To skip test if the test case is not included in test
        if (!shouldRun.equalsIgnoreCase("Yes")) {

            // Storing test details into result document

            // Writes test case number
            m.writeToExcel(testCaseNumber, 0, outputExcel);

            // Writes booking reference
            m.writeToExcel("-", 1, outputExcel);

            // Writes test status
            testStatus = "Skipped";
            m.writeToExcel(testStatus, 2, outputExcel);

            // Writes skip reason
            m.writeToExcel("Skipped this test case as this test case is not approved to run", 3, outputExcel);

            // Prints correlation ID
            m.writeToExcel("-", 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
            throw new SkipException("Test is skipped as this test case " +testCaseNumber+ " is not approved to run");
        }



        //Printing the test case number when executing test
        System.out.println(STR."\{testCaseNumber} Executed");

        // Launch browser
        // Invoke Chrome browser according to browser provided in test case

        // Invoke chrome browser
        if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
            driver = new ChromeDriver();

        }

        // Invoke edge browser
        else if (browser.equalsIgnoreCase("Edge")) {
            System.setProperty("webdriver.edge.driver", Paths.edgeDriver);
            driver = new EdgeDriver();

        }

        // Invoke firefox browser
        else if (browser.equalsIgnoreCase("Firefox")) {
            System.setProperty("webdriver.gecko.driver", Paths.geckoDriver);
            driver = new FirefoxDriver();

        }


        // Maximize window
        driver.manage().window().maximize();


        // Setting up URL for ZA domain according to the environment in test data
        if (domain.equalsIgnoreCase("ZA"))

            switch (environment) {

                // Live URL
                case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 4, 1);
                // Beta URL
                case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 6, 1));
                // Preprod URL
                case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 8, 1));
                // Alpha URL
                case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 10, 1));

                // Returns invalid environment if environment name doesn't match with environment names
                default -> System.out.println("Invalid environment name");


            }

            // Setting up URL for NG domain
        else if (domain.equalsIgnoreCase("NG")) {

            switch (environment) {

                // Live URL
                case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 5, 1));
                // Beta URL
                case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 7, 1));
                // Preprod URL
                case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 9, 1));
                // Alpha URL
                case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 11, 1));

                // Returns invalid environment if environment name doesn't match with environment names
                default -> System.out.println("Invalid envinorment name");

            }
        }


        // Launch URL
        driver.get(baseURL);
        Thread.sleep(500);


            // Accept all cookies
            driver.manage().deleteAllCookies();
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
            } catch (Exception e) {
                e.printStackTrace();
            }

        Thread.sleep(2000);

            //Selecting trip type in search if the trip type is oneway
            if (tripType.equalsIgnoreCase("Oneway")){

            driver.findElement(HomePage.oneWay).click();

            }

        // Entering departure city
        Thread.sleep(1000);
        driver.findElement(HomePage.departureCity).sendKeys(origin);

        // Waits for 2 seconds for city suggestions to come up
        Thread.sleep(2000);

        // Selecting city from suggestion
        driver.findElement(HomePage.option).click();

        // Entering return city
        driver.findElement(HomePage.arrivalCity).sendKeys(destination);

        // Waits for 2 seconds for city suggestions to come up
        Thread.sleep(2000);

        // Selecting city from suggestion
        driver.findElement(HomePage.option).click();

        //Creates instance to select date provided in test case
        HomePage dateSelect = new HomePage(driver);

        //Selecting date if trip is oneway
        if (tripType.equalsIgnoreCase("Oneway")){

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects month provided in test case
            m.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            WebElement departureDateElement = dateSelect.dateSelector(DepartureDate);
            departureDateElement.click();

        }

        // Selecting date if trip is return
        else if (tripType.equalsIgnoreCase("Return")) {

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects departure month provided in test case
            m.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            WebElement departureDateElement = dateSelect.dateSelector(DepartureDate);
            departureDateElement.click();

            // Selects return month provided in test case
            m.returnMonthSelector(driver,departureMonth, returnMonth);

            // Call the date selector method with a dynamic parameter
            String ReturnDate = m.doubleToString(returnDate);

            // Clicks on desired return date in calendar
            WebElement returnDateElement = dateSelect.dateSelector(ReturnDate);
            returnDateElement.click();
        }


        // Selecting Pax type and count
        driver.findElement(HomePage.passengerSelector).click();

        //Instance of homepage for pax selection
        HomePage paxSelect = new HomePage(driver);

        // Selecting Pax type and count
        paxSelect.paxSelector(adultCount, youngAdultCount, childCount, infantCount);

        // Clicks on apply after selecting passengers
        driver.findElement(HomePage.applyPax).click();

        //Clicking search
        driver.findElement(HomePage.search).click();
        Thread.sleep(2000);

        //Initializing wait explicitly
        WebDriverWait wait = new WebDriverWait(driver, 60);

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | NoSuchFrameException e) {
            e.printStackTrace();

        }


        // Asserting result
        // Initializes webelement result to store displayed result
        WebElement result = null;

        try {
            // To wait until result is loaded (Waits for 60 seconds maximum)
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);

        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }

        // Initializing a boolean variable for result assertion
        boolean isResultAvailable = false;

        try{
            // Stores true if result is avaiable
            isResultAvailable = result.isDisplayed();

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if (isResultAvailable){
            System.out.println("Result loaded");
        }else {

            // Returns test fail information into test result document

            // To take screenshot if result is not loaded
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);

            // Gets console if any error exists in console
            m.getConsole(driver);

            // Stores screenshot file into FILE variable
            File screenShotFile = new File(screenShotPath);

            // Sends a slack notification
            m.sendNotification(testCaseNumber, "Result not loaded or result not loaded within time limit");

            // Returns test case ID into test result document
            m.writeToExcel(testCaseNumber, 0, outputExcel);

            // Returns booking reference number into test result document
            m.writeToExcel("-", 1, outputExcel);

            // Returns test status into test result document
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);

            // Returns test failure reason into test result document
            m.writeToExcel("Result not loaded or result not loaded within time limit", 3, outputExcel);

            // Returns correlation ID into test result document
            m.writeToExcel(m.getCID(driver), 4, outputExcel);

            // Returns runtime into test result document
            m.writeToExcel(runTime, 5, outputExcel);

        }

        // Asserting if result is available or not
        Assert.assertTrue(isResultAvailable, "Search result not loaded");


        // Selecting airline from filter
        // Airline selection if trip type is oneway
        if (tripType.equalsIgnoreCase("Oneway")){

            // Clicks on filters button on SRP
            driver.findElement(SRP.filters).click();

            // Creating instance to select airline
            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects airline in filters
                WebElement airlineFilterElement = airlineInstance.airlineFilter(departureAirline);
                airlineFilterElement.click();

            }

            // Ignores test if desired airline is not available in result loaded
            catch (NoSuchElementException e){

                // Returns test skipping information into test result document
                // Writing test case number into test result document
                m.writeToExcel(testCaseNumber, 0, outputExcel);

                // Writes booking reference into test result document
                m.writeToExcel("-", 1, outputExcel);

                // Writes test status into test result document
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);

                // Writes test skip reason into test result document
                m.writeToExcel(("Skipped this test because desired airline: "+departureAirline+" was not avaible in result"), 3, outputExcel);

                // Writes correlation ID into test result document
                m.writeToExcel(m.getCID(driver), 4, outputExcel);

                // Writes test runtime into test result document
                m.writeToExcel(runTime, 5, outputExcel);

                // Skips the test as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");
            }

            // Clicks on apply filter button
            driver.findElement(Filters.apply).click();

            // Clicks on book button
            driver.findElement(SRP.book).click();

            // Waits for 1 second to refresh DOM
            Thread.sleep(1000);


        }

        // Selecting airlines in filters if flights are bundled and trip type is return
        else if (tripType.equalsIgnoreCase("return") && isBundled.equalsIgnoreCase("Yes" )){

            // Clicks on filters
            driver.findElement(SRP.filters).click();

            // Creating instance to select airline from filters
            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects desired airline in filters
                WebElement airlineFilterElement = airlineInstance.airlineFilter(departureAirline);
                airlineFilterElement.click();

            }

            // Skipping test if desired airline is not available in result
            catch (NoSuchElementException e){

                // Returning test skip information into test result document
                // Writes test case number into test result document
                m.writeToExcel(testCaseNumber, 0, outputExcel);

                // Writes booking reference into test result document
                m.writeToExcel("-", 1, outputExcel);

                // Writes test status into test result document
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);

                // Writes skip reason into test result document
                m.writeToExcel(("Skipped this test because desired airline: "+departureAirline+" was not avaible in result"), 3, outputExcel);

                // Writes session ID into test result document
                m.writeToExcel(m.getCID(driver), 4, outputExcel);

                // Writes runtime into test result document
                m.writeToExcel(runTime, 5, outputExcel);

                // Skips test execution as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");

            }

            // Clicks on apply filters button
            driver.findElement(Filters.apply).click();

            // Clicks on book now button
            driver.findElement(SRP.book).click();

            // Wait for 1 second for the DOM to get referesh
            Thread.sleep(1000);


        }

        // Selecting airline from filter if the searched flights are unbundled
        else if (isBundled.equalsIgnoreCase("No")) {

            // Clicks on filters on SRP
            driver.findElement(SRP.unBundledFilter).click();

            // Creating instance to select airline in filters
            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects desired airline in filters
                WebElement airlineFilterElement = airlineInstance.airlineFilter(departureAirline);
                airlineFilterElement.click();

            }

            // Skips test if the resired airline is not available in result
            catch (NoSuchElementException e){

                // Returning test skip information into test result document
                // Writes test case number into test result document
                m.writeToExcel(testCaseNumber, 0, outputExcel);

                // Writes booking reference into test result document
                m.writeToExcel("-", 1, outputExcel);

                // Writes test status into test result document
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);

                // Writes skip reason into test result document
                m.writeToExcel(("Skipped this test because desired airline: "+departureAirline+" was not avaible in result"), 3, outputExcel);

                // Writes correlation ID into test result document
                m.writeToExcel(m.getCID(driver), 4, outputExcel);

                // Writes runtime into test result document
                m.writeToExcel(runTime, 5, outputExcel);

                // Skips test as the desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");
            }

            // Clicks on return airline filter button
            driver.findElement(Filters.returnAirline).click();

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects desired return airline
                WebElement airlineFilterElement = airlineInstance.airlineFilter(returnAirline);
                airlineFilterElement.click();

            }

            // Skips test if the desired airline is not available in result
            catch (NoSuchElementException e){

                // Writes test case ID into test result document
                m.writeToExcel(testCaseNumber, 0, outputExcel);

                // Writes booking reference into test result document
                m.writeToExcel("-", 1, outputExcel);

                // Writes test status into test result document
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);

                // Writes skip reason into test result document
                m.writeToExcel(("Skipped this test because desired airline: "+returnAirline+" was not avaible in result"), 3, outputExcel);

                // Writes correlation ID into test result document
                m.writeToExcel(m.getCID(driver), 4, outputExcel);

                // Writes runtime into test result document
                m.writeToExcel(runTime, 5, outputExcel);

                // Skips test execution as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +returnAirline+ " was not available in search result");

            }

            // Clicks on apply filters button
            driver.findElement(Filters.apply).click();

            // Selects outbound flight in result
            driver.findElement(SRP.outboundFlightUnbundled).click();

            // Selects inbound flight in result
            driver.findElement(SRP.inboundFlightUnbundled).click();

            // Clicks on book button
            driver.findElement(SRP.domBook).click();


        }

        // To proceed booking if airport change pop-up appears
        if (tripType.equalsIgnoreCase("Return")){

            try {

                // Clicks on proceed on airport change pop up
                driver.findElement(SRP.airPortChange).click();

            }catch (NoSuchElementException ne){
                ne.printStackTrace();
            }
        }

        // Waits for 1 second for DOM to get refreshed
        Thread.sleep(1000);

        // Asserting Traveller Page
        WebElement travellerPage = null;
        try {

            // Waits until flight details page is loaded for maximum 60 seconds
            wait.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
            travellerPage = driver.findElement(FlightPage.flightReviewPage);

        }
        catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }

        // Initializing boolean variable to asser flight details page
        boolean istravellerPageAvailable = false;

        try{

            // Assigning boolean value to assertion variable if flight details page is available
            istravellerPageAvailable = travellerPage.isDisplayed();

        }catch (NullPointerException e){

            e.printStackTrace();

        }
        if (istravellerPageAvailable){

            System.out.println("Traveller page loaded");

        }

        // To return test failure information into test result document if flight details page is not loaded
        else {

            // Takes screenshot if flight details page is not loaded
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);

            // Gets console if any console error occurs
            m.getConsole(driver);

            // Stotes screenshot into a FILE variable
            File screenShotFile = new File(screenShotPath);

            // Sends slack notification
            m.sendNotification(testCaseNumber, "Not redirected to flight details screen or not redirected within 60 seconds");

            // Writes testcase ID into test result document
            m.writeToExcel(testCaseNumber, 0, outputExcel);

            // Writes booking reference into test result document
            m.writeToExcel("-", 1, outputExcel);

            // Writes test status into test result document
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);

            // Writes test failure reason into test result document
            m.writeToExcel(("Not redirected to flight details screen or not redirected within 60 seconds"), 3, outputExcel);

            // Writes session ID into test result document
            m.writeToExcel(m.getCID(driver), 4, outputExcel);

            //Writes runtime into test result document
            m.writeToExcel(runTime, 5, outputExcel);

        }

        // Proceeding if fare increase pop up appears
        try {

            // Clicks on proceed on fare increase pop up
            driver.findElement(FlightPage.fareIncreaseContinue).click();

        }catch (Exception e){

            e.printStackTrace();

        }

        // Initializing boolean variable to assert traveller details page
        boolean isTravellerPageAvailable = false;

        try {

            // Stores boolean value as true if traveller details page is loaded
            isTravellerPageAvailable = travellerPage.isDisplayed();

        } catch (NullPointerException e){

            e.printStackTrace();

        }

        // Asserting if traveller details page is available or not
        Assert.assertTrue(isTravellerPageAvailable, "Traveller page  not loaded");

        // To check is the airline is flysafair for test surname
        boolean isFAFlight = false;

        // To check is the airline is flysafair for test surname
        if (tripType.equalsIgnoreCase("Oneway") && departureAirline.equalsIgnoreCase("FA")){

            isFAFlight = true;

        } else if (tripType.equalsIgnoreCase("Return") && departureAirline.equalsIgnoreCase("FA") && returnAirline.equalsIgnoreCase("FA")) {

            isFAFlight = true;

        }


        String lastname;
        if (isFAFlight) {

            // Storing surname as "Test" if the airline is FlySafair
            lastname = "Test";

        } else {

            // Storing surname as actual if the airline is not FlySafair
            lastname = lastName;

        }


        // Sending contact details in booking
        // Sending mobile number
        driver.findElement(FlightPage.mobileNo).sendKeys(mobileNumber);

        // Sending mail ID
        driver.findElement(FlightPage.email).sendKeys(mailID);

        // Deselecting whatsapp add on if product is included in test case
        if (whatsApp.equalsIgnoreCase("No")){

            // Deselects whatsapp
            driver.findElement(FlightPage.whatsApp).click();

        }

        Thread.sleep(500);

        //Adding PAX details

        // Initializing a webelement to store primary pax title
        WebElement paxTitle = null;

        // Storing "Mr" title into webelement if test case has the title as Mr
        if (title.equalsIgnoreCase("mr")){

            paxTitle =  driver.findElement(FlightPage.mr);

        }

        // Storing "Ms" title into webelement if test case has the title as Ms
        else if (title.equalsIgnoreCase("ms")){

            paxTitle = driver.findElement(FlightPage.ms);

        }

        // Storing "Mrs" title into webelement if test case has the title as Mrs
        else if (title.equalsIgnoreCase("mrs")) {

            paxTitle = driver.findElement(FlightPage.mrs);

        }

        // Selecting passenger title
        paxTitle.click();

        // Sending First Name
        driver.findElement(FlightPage.firstName).sendKeys(firstName);

        // Sending Last Name
        driver.findElement(FlightPage.lastName).sendKeys(lastname);

        // Date of birth selection
        // Storing date of birth selections into webelements
        WebElement day = driver.findElement(FlightPage.dayDOB);
        WebElement month = driver.findElement(FlightPage.monthDOB);
        WebElement year = driver.findElement(FlightPage.yearDOB);

//        //Selecting date of birth from dropdown
//        Select daySelector = new Select(day);
//        daySelector.selectByIndex(m.stringToInt(dateOfBirth));
//
//        //Selecting month of birth from dropdown
//        Select monthSelector = new Select(month);
//        monthSelector.selectByIndex(m.stringToInt(monthOfBirth));
//
//        //Selecting year of birth
//        String yearOfBirthSelect = m.doubleToString(yearOfBirth);
//        Select yearSelector = new Select(year);
//        yearSelector.selectByValue(yearOfBirthSelect);

        // Selection of date of birth

        m.selectFromDropDown(driver, day, dateOfBirth);
        m.selectFromDropDown(driver, month, monthOfBirth);
        m.selectFromDropDown(driver, year, yearOfBirth);



        //Add checked baggage
        if (addBaggage.equalsIgnoreCase("Yes")){

            try {

                List<WebElement> addBaggageChecks = driver.findElements(FlightPage.addCheckedBaggage);
                for (WebElement addBaggageCheck : addBaggageChecks) {
                    addBaggageCheck.click();
                }
            }catch (NoSuchElementException e){
                System.out.println("Baggage addition is not available for this flight");
            }
        }

        //Passport details
        WebElement passPortInfo = null;
        try {
            passPortInfo = driver.findElement(FlightPage.ppInfo);
            passPortInfo = driver.findElement(FlightPage.ppInfo);
        } catch (NoSuchElementException ne) {
            ne.printStackTrace();
            System.out.println("PassPort details not required for this flight");
        }
        try {
            if (passPortInfo.isDisplayed()) {
                WebElement passportNumber = driver.findElement(FlightPage.ppNumber);
                passportNumber.sendKeys(passPortNumber);

                WebElement passportExpiryday = driver.findElement(FlightPage.ppExpiryDate);
                WebElement passportExpirymonth = driver.findElement(FlightPage.ppExpiryMonth);
                WebElement passportExpiryyear = driver.findElement(FlightPage.ppExpiryYear);

//                Select passportExpirydaySelector = new Select(passportExpiryday);
//                passportExpirydaySelector.selectByIndex(m.stringToInt(dateOfPassportExpiry));
//                Select passportExpirymonthSelector = new Select(passportExpirymonth);
//                passportExpirymonthSelector.selectByIndex(m.stringToInt(monthOfPassportExpiry));
//                Select passportExpiryyearSelector = new Select(passportExpiryyear);
//                passportExpiryyearSelector.selectByValue(m.doubleToString(yearOfPassportExpiry));

                // Select passport expiry using click method

                m.selectFromDropDown(driver,passportExpiryday,dateOfPassportExpiry);
                m.selectFromDropDown(driver,passportExpirymonth, monthOfPassportExpiry);
                m.selectFromDropDown(driver,passportExpiryyear, yearOfPassportExpiry);

                driver.findElement(FlightPage.ppNationality).click();
                driver.findElement(FlightPage.ppnationalityIndia).click();

                Thread.sleep(1000);
                driver.findElement(FlightPage.ppIssuingCountry).click();
                driver.findElement(FlightPage.ppInsuingCountryIndia).click();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int adultCountTiInt = Integer.parseInt(adultCount);
        int youngAdultCountTiInt = Integer.parseInt(youngAdultCount);
        int childCountTiInt = Integer.parseInt(childCount);
        int infantCountTiInt = Integer.parseInt(infantCount);

        if (adultCountTiInt > 1 || youngAdultCountTiInt > 0 || childCountTiInt > 0 || infantCountTiInt > 0){
        m.paxSender(driver, adultCount, youngAdultCount, childCount, infantCount, departureAirline, returnAirline);
        }

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | NoSuchFrameException e) {
            e.printStackTrace();

        }

        driver.findElement(FlightPage.contnue).click();
        System.out.println("Traveller details have been added");
        Thread.sleep(200);

        // To deselect addOns
        try {
            List<WebElement> selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
            int numberOfSelectedAddOns = selectedAddons.size();

            for (int i = 0; i < numberOfSelectedAddOns;) {
                WebElement selectedAddon = selectedAddons.get(i);
                if (selectedAddon.isDisplayed()) {
                    selectedAddon.click();
                }
                // Re-find the list of selected addons after each interaction
                selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
                int newNumberOfSelectedAddOns = selectedAddons.size();

                // Check if the number of selected addons has decreased
                if (newNumberOfSelectedAddOns < numberOfSelectedAddOns) {
                    numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                    // Restart the loop as the indices may have shifted
                    continue;
                }

                numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                i++; // Proceed to the next addon
            }
        } catch (Exception e) {
            System.out.println("No addons selected by default");
        }

        // To add flexi
        try {
            List<WebElement> availableAddons = driver.findElements(AddOnsPage.addOnName);
            List<WebElement> SelectAddons = driver.findElements(AddOnsPage.selectAddon);
            int numberOfAddOns = availableAddons.size();
            if (addFlexi.equalsIgnoreCase("Yes")){
                for (int i = 0; i < numberOfAddOns; ) {
                    WebElement availableAddon = availableAddons.get(i);
                    WebElement SelectaddOn = SelectAddons.get(i);
                    if (availableAddon.getText().contains("Flexible Travel Dates") && SelectaddOn.isDisplayed()) {
                        SelectaddOn.click();
                    }
                    // Re-find the list of selected addons after each interaction
                    availableAddons = driver.findElements(AddOnsPage.addOnName);
                    int newNumberOfAddOns = availableAddons.size();

                    // Check if the number of selected addons has decreased
                    if (newNumberOfAddOns < numberOfAddOns) {
                        numberOfAddOns = newNumberOfAddOns;
                        // Restart the loop as the indices may have shifted
                        continue;
                    }

                    numberOfAddOns = newNumberOfAddOns;
                    i++; // Proceed to the next addon
                }}
        } catch (NoSuchElementException e) {
            System.out.println("Add ons not available");
        }


        if (domain.equalsIgnoreCase("NG")){
            driver.findElement(AddOnsPage.checkBoxNG).click();
        }

        driver.findElement(AddOnsPage.contnue).click();
        Thread.sleep(200);
        try {
            driver.findElement(AddOnsPage.noIWillRiskIt).click();
        } catch (Exception e){

        }
        //Payment page

        String bookingReference = "";

        // Asserting Payment Page
        WebElement paymentPage = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.bookingReference));
            paymentPage = driver.findElement(PaymentPage.bookingReference);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean ispaymentPageAvailable = false;
        try{
            ispaymentPageAvailable = paymentPage.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (ispaymentPageAvailable){
            System.out.println("Payment page loaded");
            bookingReference = driver.findElement(PaymentPage.bookingReference).getText();
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            m.sendNotification(testCaseNumber, "Not redirected to payment screen or not redirected within 60 seconds");

            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel("-", 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Not redirected to payment page or not redirected within 60 seconds"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

        Assert.assertTrue(ispaymentPageAvailable, "Payment page not loaded");

        String timeOne = "";
        String timeTwo = "";

        //Payment in ZA - EFT
        if (domain.equalsIgnoreCase("ZA")&& paymentMethod.equalsIgnoreCase("EFT")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
            driver.findElement(PaymentPage.EFT).click();

            switch (bankNameEFT) {

                //Banks
                case "Nedbank" -> driver.findElement(PaymentPage.nedBank).click();
                case "FNB" -> driver.findElement(PaymentPage.fnb).click();
                case "Standard Bank" -> driver.findElement(PaymentPage.standardBank).click();
                case "ABSA" -> driver.findElement(PaymentPage.absa).click();


                default -> System.out.println("Invalid bank name");

            }

            driver.findElement(PaymentPage.payNow).click();

        }

        //Paying with card
        else if (paymentMethod.equalsIgnoreCase("cc/dc")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.credicCardOrDebitCard));
            driver.findElement(PaymentPage.credicCardOrDebitCard).click();
            String cardNumber = m.readDataFromExcel(dataPath, "Card detals", 2,1);
            String cardHolderName = m.readDataFromExcel(dataPath, "Card detals", 2,2);
            String cardExpiryMonth = (m.readDataFromExcel(dataPath, "Card detals", 2,3));
            String cardExpiryYear = m.readDataFromExcel(dataPath, "Card detals", 2,4);
            String CVV = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2,5));
            String AddressLine1 = m.readDataFromExcel(dataPath, "Card detals", 2,6);
            String AddressLine2 = m.readDataFromExcel(dataPath, "Card detals", 2,7);
            String PostalCode = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2,8));
            String city = m.readDataFromExcel(dataPath, "Card detals", 2,9);
            String country = m.readDataFromExcel(dataPath, "Card detals", 2,10);
            String contactNumber = m.readDataFromExcel(dataPath, "Card detals", 2,11);



            driver.findElement(PaymentPage.cardNumber).sendKeys(cardNumber);
            driver.findElement(PaymentPage.cardHolderName).sendKeys(cardHolderName);

            WebElement CardExpiryMonthElement = driver.findElement(PaymentPage.cardExpiryMonth);
            WebElement CardExpiryYearElement = driver.findElement(PaymentPage.cardExpiryYear);



 //           Select expiryMonthSelector = new Select(CardExpiryMonthElement);
//            expiryMonthSelector.selectByIndex(cardExpiryMonth);
            m.selectFromDropDown(driver, CardExpiryMonthElement, cardExpiryMonth);

//            Select expiryYearSelector = new Select(CardExpiryYearElement);
//            expiryYearSelector.selectByValue(cardExpiryYear);
            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);

            driver.findElement(PaymentPage.CVV).sendKeys(CVV);


//            driver.findElement(PaymentPage.addressLine1).sendKeys(AddressLine1);
//            driver.findElement(PaymentPage.addressLine2).sendKeys(AddressLine2);
//
//            driver.findElement(PaymentPage.postalCode).sendKeys(PostalCode);
//            driver.findElement(PaymentPage.city).sendKeys(city);
//
//            driver.findElement(PaymentPage.country).click();
//            Thread.sleep(500);
//            driver.findElement(PaymentPage.countryIndia).click();
//            driver.findElement(PaymentPage.contactNo).sendKeys(m.doubleToString(contactNumber));

            driver.findElement(PaymentPage.payNow).click();


        }

        // Pay using iPay
        else if (domain.equalsIgnoreCase("ZA") && (paymentMethod.equalsIgnoreCase("Instant EFT") || paymentMethod.equalsIgnoreCase("IPAY"))) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.iPay));
            driver.findElement(PaymentPage.iPay).click();
            //driver.findElement(PaymentPage.payNow).click();

        }

        // Pay using NG EFT
        else if (domain.equalsIgnoreCase("NG") && paymentMethod.equalsIgnoreCase("EFT")){

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
            driver.findElement(PaymentPage.EFT).click();

            switch (bankNameEFT) {

                //Banks
                case "travelstart" -> driver.findElement(PaymentPage.travelStart).click();
                case "Access" -> driver.findElement(PaymentPage.access).click();
                case "UBA" -> driver.findElement(PaymentPage.UBA).click();
                case "Zenith" -> driver.findElement(PaymentPage.zenithBank).click();


                default -> System.out.println("Invalid bank name");

            }
            driver.findElement(PaymentPage.reserve).click();

        }

        //Pay using Paystack
        else if (domain.equalsIgnoreCase("NG") && (paymentMethod.equalsIgnoreCase("Instant EFT")||paymentMethod.equalsIgnoreCase("Paystack"))) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.payStack));
            driver.findElement(PaymentPage.payStack).click();

            driver.findElement(PaymentPage.payNow).click();

        }
        timeOne = m.getCurrentTime();


        Thread.sleep(10000);

        // Asserting Booking confirmation Page
        WebElement bookingConfirm = null;

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(BookingConfirmationPage.isBookingConfirmed));
            timeTwo = m.getCurrentTime();
            bookingConfirm = driver.findElement(BookingConfirmationPage.isBookingConfirmed);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean isbookingRefAvailable = false;
        try{
            isbookingRefAvailable = bookingConfirm.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (TimeoutException te){
            System.out.println("Booking failed due to this booking took more than 1 minute");
        }
        if (isbookingRefAvailable){
            bookingReference = driver.findElement(BookingConfirmationPage.refNumber).getText();
            bookingReference = bookingReference.trim();
            System.out.println("Booking completed. "+ bookingReference);

            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel(bookingReference, 1, outputExcel);
            testStatus = "Passed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Booking completed"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            m.sendNotification(testCaseNumber, "Booking not succeeded");
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel(bookingReference, 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Booking not succeeded"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

            Assert.assertTrue(isbookingRefAvailable, "Booking failed");

        Long timeTookForBooking = null;

        if (isbookingRefAvailable){
            timeTookForBooking = m.timeCalculator(timeOne, timeTwo);
        }

        boolean isBookingDoneWithinTime = false;
        isBookingDoneWithinTime = timeTookForBooking <= 45;

        if (!isBookingDoneWithinTime){
            m.sendNotification(testCaseNumber, "Booking not completed within 45 seconds, time took for booking is "+ timeTookForBooking + " seconds");
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel(bookingReference, 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Booking not completed within 45 seconds, time took for booking is "+ timeTookForBooking +" seconds"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

//        String bookingStatus = m.getBookingStatus(environment, bookingReference);
//        boolean isBookingDone = false;
//        isBookingDone = bookingStatus.equalsIgnoreCase("Pending");

        Assert.assertTrue((isBookingDoneWithinTime), "Booking not completed within 45 seconds");

        // For cancellation

        if (toBeCancelled.equalsIgnoreCase("Yes")){
            m.cancelBooking(environment, bookingReference);
        }


    }

}
