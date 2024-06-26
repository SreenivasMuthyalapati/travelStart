package test.B2CCheckList;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.Paths;
import testmethods.Method;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Signup {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.B2CChecklistDataPath;
    static String browser;
    static String environment;
    static String outputExcel = Paths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath = "";
    static String shouldRun;


    static {
        try {
            shouldRun = m.readDataFromExcel(dataPath, "CheckList Scenarios", 5, 1);
            System.out.println("Should Run: " + shouldRun);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String domain = "ZA";

    // Extracting environment and browser details from test data sheet
    static {
        try {
            browser = m.readDataFromExcel(dataPath, "URL's", 0, 1).toUpperCase();
            environment = m.readDataFromExcel(dataPath, "URL's", 1, 1).toUpperCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Signup() throws IOException {
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
        List<Object[]> testCase = new ArrayList<>();
        int signUPCount = m.getRowCount(dataPath, "SignUP")-1;
        System.out.println("Total SignUP Count: " + signUPCount);

        for (int i = 1; i <= signUPCount; i++) {
            String testCaseNumber = m.readDataFromExcel(dataPath, "SignUP", i, 0);
            String testSummary = m.readDataFromExcel(dataPath, "SignUP", i, 1);
            String username = m.readDataFromExcel(dataPath, "SignUP", i, 2);
            String password = m.readDataFromExcel(dataPath, "SignUP", i, 3);
            String firstname = m.readDataFromExcel(dataPath, "SignUP", i, 4);
            String lastname = m.readDataFromExcel(dataPath, "SignUP", i, 5);
            testCase.add(new Object[]{testCaseNumber, testSummary, username, password, firstname, lastname});
        }

        return testCase.toArray(new Object[0][]);
    }

    @Test(dataProvider = "TestCase")
    public void signup(String testCaseNumber, String testSummary, String username, String password, String firstname, String lastname) throws IOException, InterruptedException {
        // Storing runtime into a variable
        runTime = m.getCurrentTime();
        String testStatus;

        // To skip test if the test case is not included in test
        if (!shouldRun.equalsIgnoreCase("Yes")) {
            // Storing test details into result document
            m.writeToExcel(testCaseNumber, 0, outputExcel); // Writes test case number
            m.writeToExcel("-", 1, outputExcel); // Writes booking reference
            testStatus = "Skipped";
            m.writeToExcel(testStatus, 2, outputExcel); // Writes test status
            m.writeToExcel("Skipped this test case as this test case is not approved to run", 3, outputExcel); // Writes skip reason
            m.writeToExcel("-", 4, outputExcel); // Prints correlation ID
            m.writeToExcel(runTime, 5, outputExcel);
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");
        }

        // Printing the test case number when executing test
        System.out.println("Test Case: " + testCaseNumber + ", " + testSummary);

        // Launch browser
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
           // System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            //System.setProperty("webdriver.edge.driver", Paths.edgeDriver);
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            System.setProperty("webdriver.gecko.driver", Paths.geckoDriver);
            driver = new FirefoxDriver();
        }

        // Maximize window
        driver.manage().window().maximize();

        // Setting up URL based on domain and environment
        switch (domain.toUpperCase()) {
            case "ZA":
                switch (environment) {
                    case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 4, 1);
                    case "BETA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 6, 1);
                    case "PREPROD" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 8, 1);
                    case "ALPHA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 10, 1);
                    default -> System.out.println("Invalid environment name");
                }
                break;
            case "NG":
                switch (environment) {
                    case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 5, 1);
                    case "BETA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 7, 1);
                    case "PREPROD" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 9, 1);
                    case "ALPHA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 11, 1);
                    default -> System.out.println("Invalid environment name");
                }
                break;
            default:
                System.out.println("Invalid domain name");
        }

        // Launch URL
        driver.get(baseURL);
        Thread.sleep(5000);
        driver.findElement(HomePage.myAccount).click();
        Thread.sleep(500);
        driver.findElement(LoginPage.signUpOption).click();
        Thread.sleep(500);
        driver.findElement(LoginPage.email).sendKeys(username);
        Thread.sleep(500);
        driver.findElement(LoginPage.signUPPassword).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(LoginPage.firstName).sendKeys(firstname);
        Thread.sleep(500);
        driver.findElement(LoginPage.lastName).sendKeys(lastname);
        Thread.sleep(500);
        driver.findElement(LoginPage.TandCCheckBox).click();
        Thread.sleep(500);
        driver.findElement(LoginPage.signUpButton).click();
        Thread.sleep(5000);

        driver.get("https://yopmail.com/");

        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.findElement(By.xpath("//input[@id='login']")).sendKeys(username);
        driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='ifinbox']")));

        WebElement inboxFrameElement = driver.findElement(By.xpath("//iframe[@id='ifinbox']"));

        driver.switchTo().frame(inboxFrameElement);

        for (int i = 0; i < 24; i++) {

            Thread.sleep(3000);
            driver.switchTo().defaultContent();
            driver.findElement(By.xpath("(//button[@id='refresh'])[1]")).click();
            driver.switchTo().frame(inboxFrameElement);
            Thread.sleep(2000);

            try {
                WebElement activationMail = driver.findElement(By.xpath("(//div[@class='mctn']//div[@class='lms'])[1]"));
                String mailSubject = activationMail.getText();
                if (mailSubject.equalsIgnoreCase("Your  account") || mailSubject.equalsIgnoreCase("Your account") || mailSubject.equalsIgnoreCase("Your Travelstart account")) {
                    activationMail.click();
                    break;
                }
            } catch (NoSuchElementException e) {
                // Email not found yet
            }
        }
        driver.switchTo().defaultContent();

        WebElement mailBodyFrameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='ifmail']")));
        driver.switchTo().frame(mailBodyFrameElement);
        driver.findElement(By.xpath("(//*[text()='Activate Account'])[1]")).click();

        Thread.sleep(2000);

        boolean accountStatusActive = m.checkAccountStatus(username,password);

        if (accountStatusActive) {
            System.out.println("Account created");
        }
        else {
            System.out.println("Account activation failed");
        }

        Assert.assertTrue(accountStatusActive, "Signup failed");

    }

}