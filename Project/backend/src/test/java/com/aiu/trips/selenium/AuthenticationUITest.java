package com.aiu.trips.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium UI Automation Tests
 * Based on CSV Test Cases for Frontend Testing
 * Tests user authentication flows in the web interface
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationUITest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:3000"; // Frontend URL
    private static final int TIMEOUT_SECONDS = 10;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setUp() {
        driver.manage().deleteAllCookies();
    }

    // TC_001: Validate that the system allows users to log in with valid credentials
    @Test
    @Order(1)
    void testLoginWithValidCredentials() {
        try {
            driver.get(BASE_URL + "/login");
            
            // Wait for login form to load
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='email'], input[name='email']")));
            WebElement passwordInput = driver.findElement(
                    By.cssSelector("input[type='password'], input[name='password']"));
            WebElement loginButton = driver.findElement(
                    By.cssSelector("button[type='submit'], button:contains('Login')"));

            // Enter credentials
            emailInput.sendKeys("admin@aiu.edu");
            passwordInput.sendKeys("admin123");
            loginButton.click();

            // Wait for navigation to dashboard or home
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/dashboard"),
                    ExpectedConditions.urlContains("/home"),
                    ExpectedConditions.urlContains("/events")
            ));

            // Verify successful login
            String currentUrl = driver.getCurrentUrl();
            assertFalse(currentUrl.contains("/login"), "Should navigate away from login page");
        } catch (Exception e) {
            fail("Login test failed: " + e.getMessage());
        }
    }

    // TC_003: Validate that the system displays an error message for invalid login attempts
    @Test
    @Order(2)
    void testLoginWithInvalidCredentials() {
        try {
            driver.get(BASE_URL + "/login");

            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='email'], input[name='email']")));
            WebElement passwordInput = driver.findElement(
                    By.cssSelector("input[type='password'], input[name='password']"));
            WebElement loginButton = driver.findElement(
                    By.cssSelector("button[type='submit'], button:contains('Login')"));

            // Enter invalid credentials
            emailInput.sendKeys("invalid@aiu.edu");
            passwordInput.sendKeys("wrongpassword");
            loginButton.click();

            // Wait for error message
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".error, .alert-danger, [role='alert']")));

            // Verify error message is displayed
            assertTrue(errorMessage.isDisplayed(), "Error message should be displayed");
        } catch (Exception e) {
            fail("Invalid login test failed: " + e.getMessage());
        }
    }

    // TC_006: Validate that new users can successfully register with valid information
    @Test
    @Order(3)
    void testUserRegistration() {
        try {
            driver.get(BASE_URL + "/register");

            // Wait for registration form
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='email'], input[name='email']")));
            WebElement passwordInput = driver.findElement(
                    By.cssSelector("input[type='password'], input[name='password']"));
            WebElement fullNameInput = driver.findElement(
                    By.cssSelector("input[name='fullName'], input[name='name']"));
            WebElement submitButton = driver.findElement(
                    By.cssSelector("button[type='submit']"));

            // Enter registration data
            String timestamp = String.valueOf(System.currentTimeMillis());
            emailInput.sendKeys("newuser" + timestamp + "@aiu.edu");
            passwordInput.sendKeys("Password@123");
            fullNameInput.sendKeys("New Test User");
            submitButton.click();

            // Wait for success message or redirect
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/login"),
                    ExpectedConditions.urlContains("/dashboard"),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".success, .alert-success"))
            ));

            // Verify registration success
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("/login") || currentUrl.contains("/dashboard"),
                    "Should navigate to login or dashboard after registration");
        } catch (Exception e) {
            fail("Registration test failed: " + e.getMessage());
        }
    }

    // TC_007: Validate that the system prevents duplicate email addresses
    @Test
    @Order(4)
    void testDuplicateEmailRegistration() {
        try {
            driver.get(BASE_URL + "/register");

            // Wait for registration form
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='email'], input[name='email']")));
            WebElement passwordInput = driver.findElement(
                    By.cssSelector("input[type='password'], input[name='password']"));
            WebElement fullNameInput = driver.findElement(
                    By.cssSelector("input[name='fullName'], input[name='name']"));
            WebElement submitButton = driver.findElement(
                    By.cssSelector("button[type='submit']"));

            // Try to register with existing email
            emailInput.sendKeys("admin@aiu.edu");
            passwordInput.sendKeys("Password@123");
            fullNameInput.sendKeys("Duplicate User");
            submitButton.click();

            // Wait for error message
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".error, .alert-danger, [role='alert']")));

            // Verify error message is displayed
            assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for duplicate email");
        } catch (Exception e) {
            fail("Duplicate email test failed: " + e.getMessage());
        }
    }

    // Additional test: Navigate to registration from login page
    @Test
    @Order(5)
    void testNavigationToRegistration() {
        try {
            driver.get(BASE_URL + "/login");

            // Look for registration link
            WebElement registerLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("a[href*='register'], a:contains('Register'), a:contains('Sign up')")));
            registerLink.click();

            // Verify navigation to registration page
            wait.until(ExpectedConditions.urlContains("/register"));
            assertTrue(driver.getCurrentUrl().contains("/register"),
                    "Should navigate to registration page");
        } catch (Exception e) {
            fail("Navigation test failed: " + e.getMessage());
        }
    }

    // Additional test: Logout functionality
    @Test
    @Order(6)
    void testLogout() {
        try {
            // First login
            driver.get(BASE_URL + "/login");
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='email'], input[name='email']")));
            WebElement passwordInput = driver.findElement(
                    By.cssSelector("input[type='password'], input[name='password']"));
            WebElement loginButton = driver.findElement(
                    By.cssSelector("button[type='submit']"));

            emailInput.sendKeys("admin@aiu.edu");
            passwordInput.sendKeys("admin123");
            loginButton.click();

            // Wait for navigation
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

            // Find and click logout button
            WebElement logoutButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("button:contains('Logout'), a:contains('Logout'), [data-testid='logout']")));
            logoutButton.click();

            // Verify redirect to login
            wait.until(ExpectedConditions.urlContains("/login"));
            assertTrue(driver.getCurrentUrl().contains("/login"),
                    "Should navigate to login page after logout");
        } catch (Exception e) {
            fail("Logout test failed: " + e.getMessage());
        }
    }
}
