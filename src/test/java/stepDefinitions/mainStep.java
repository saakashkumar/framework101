package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.JsonReader;

public class mainStep {

        private WebDriver driver;

        @Given("I launch the login page")
        public void launchLoginPage() {
            // Set up WebDriverManager for Chrome
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://the-internet.herokuapp.com/login");
        }

        @When("I enter {string} into the username field")
        public void enterUsername(String username) {
            String locatorType = JsonReader.getLocator("LoginPage", "usernameField", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "usernameField", "value");
            driver.findElement(getBy(locatorType, locatorValue)).sendKeys(username);
        }

        @When("I enter {string} into the password field")
        public void enterPassword(String password) {
            String locatorType = JsonReader.getLocator("LoginPage", "passwordField", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "passwordField", "value");
            driver.findElement(getBy(locatorType,locatorValue)).sendKeys(password);
        }

        @When("I click the login button")
        public void clickLoginButton() {
            String locatorType = JsonReader.getLocator("LoginPage", "loginButton", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "loginButton", "value");
            driver.findElement(getBy(locatorType,locatorValue)).click();
        }

        @Then("I should see {string} in the success message")
        public void verifySuccessMessage(String expectedMessage) {
            String locatorType = JsonReader.getLocator("LoginPage", "successMessage", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "successMessage", "value");
            String actualMessage = driver.findElement(getBy(locatorType, locatorValue)).getText();
            if (!actualMessage.contains(expectedMessage)) {
                throw new AssertionError("Expected message to contain '" + expectedMessage + "', but got '" + actualMessage + "'");
            }
        }

        @Then("I should see {string} in the error message")
        public void verifyErrorMessage(String expectedMessage) {
            String locatorType = JsonReader.getLocator("LoginPage", "failureMessage", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "failureMessage", "value");
            String actualMessage = driver.findElement(getBy(locatorType, locatorValue)).getText();
            if (!actualMessage.contains(expectedMessage)) {
                throw new AssertionError("Expected message to contain '" + expectedMessage + "', but got '" + actualMessage + "'");
            }
        }

        private By getBy(String type, String value) {
            switch (type.toLowerCase()) {
                case "id": return By.id(value);
                case "name": return By.name(value);
                case "css": return By.cssSelector(value);
                case "xpath": return By.xpath(value);
                default: throw new IllegalArgumentException("Unsupported locator type: " + type);
            }
        }
        @After
        public void closeBrowser() {
            if (driver != null) {
                driver.quit();
            }
        }
    }

