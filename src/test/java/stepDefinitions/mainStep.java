package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;
import utils.JsonReader;

import java.time.Duration;

public class mainStep {
        private WebDriverWait wait;
        private WebDriver driver;

       public mainStep() {
            driver = Hooks.getDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        @Given("I launch the login page")
        public void launchLoginPage() {
            driver.get(Constants.BASE_URL);
        }

        @When("I enter {string} into the username field")
        public void enterUsername(String username) {
            String locatorType = JsonReader.getLocator("LoginPage", "usernameField", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "usernameField", "value");
            wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType, locatorValue)));
            try {
                driver.findElement(getBy(locatorType, locatorValue)).sendKeys(username);
            } catch (Exception e) {
                System.err.println("Failed to enter username: " + e.getMessage());
                throw e; // rethrow the exception after logging
            }
        }

        @When("I enter {string} into the password field")
        public void enterPassword(String password) {
            String locatorType = JsonReader.getLocator("LoginPage", "passwordField", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "passwordField", "value");
            wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType, locatorValue)));
            driver.findElement(getBy(locatorType,locatorValue)).sendKeys(password);
        }

        @When("I click the login button")
        public void clickLoginButton() {
            String locatorType = JsonReader.getLocator("LoginPage", "loginButton", "locator");
            String locatorValue = JsonReader.getLocator("LoginPage", "loginButton", "value");
            wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType, locatorValue)));
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
    }

