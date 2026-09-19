package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.RegisterPage;

public class RegisterTest {
	WebDriver driver;
	RegisterPage registerPage;
	String myWebPage = "http://localhost:3000/register";

	@BeforeMethod
	public void MySetup() {
		driver = new ChromeDriver();
		registerPage = new RegisterPage(driver);
		driver.get(myWebPage);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@Test(priority = 1)
	public void RegisterWithValidData() {
		String uniquEmail = "test" + System.currentTimeMillis() + "@gmail.com";
		registerPage.EnterFullName("Test1");
		registerPage.EnterEmail(uniquEmail);
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();
		Assert.assertTrue(registerPage.IsLogoutButtonDisplayed());
	}

	@Test(priority = 2)
	public void RegisterWithEmailAlreadyExists() {
		registerPage.EnterFullName("Test1");
		registerPage.EnterEmail("test1@gmail.com");
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();

		Assert.assertEquals(registerPage.ErrorMessage(), "User already exists");
	}

	@Test(priority = 3)
	public void RegisterWithEmptyFullName() {
		registerPage.EnterEmail("test@gmail.com");
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();

		Assert.assertFalse(registerPage.GetFullNameValidationMessage().isEmpty());
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 4)
	public void RegisterWithEmptyEmail() {
		registerPage.EnterFullName("Test User");
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();

		Assert.assertFalse(registerPage.GetEmailValidationMessage().isEmpty());
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 5)
	public void RegisterWithInvalidEmailFormat() {
		registerPage.EnterFullName("Test User");
		registerPage.EnterEmail("testgmail.com");
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();

		Assert.assertFalse(registerPage.GetEmailValidationMessage().isEmpty());
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 6)
	public void RegisterWithEmptyPassword() {
		registerPage.EnterFullName("Test User");
		registerPage.EnterEmail("test@gmail.com");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();

		Assert.assertFalse(registerPage.GetPasswordValidationMessage().isEmpty());
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 7)
	public void RegisterWithEmptyConfirmPassword() {
		registerPage.EnterFullName("Test User");
		registerPage.EnterEmail("test@gmail.com");
		registerPage.EnterPassword("123456");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();

		Assert.assertFalse(registerPage.GetConfirmPasswordValidationMessage().isEmpty());
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 8)
	public void RegisterWithEmptyFields() {
		registerPage.ClickCreateAccount();

		Assert.assertFalse(registerPage.GetFullNameValidationMessage().isEmpty());
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 9)
	public void RegisterWithPasswordMismatch() {

		registerPage.EnterFullName("Test User");
		String uniqueEmail = "test" + System.currentTimeMillis() + "@gmail.com";
		registerPage.EnterEmail(uniqueEmail);
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("654321");
		registerPage.SelectAccountType("user");
		registerPage.ClickCreateAccount();
		
		Assert.assertEquals(registerPage.ErrorMessage(), "Passwords do not match");
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/register");
	}
	
	@Test(priority = 10)
	public void RegisterAsAgent() {
		String uniqueEmail = "agent" + System.currentTimeMillis() + "@gmail.com";
		registerPage.EnterFullName("Test Agent");
		registerPage.EnterEmail(uniqueEmail);
		registerPage.EnterPassword("123456");
		registerPage.EnterConfirmPassword("123456");
		registerPage.SelectAccountType("agent");
		registerPage.ClickCreateAccount();

		Assert.assertTrue(registerPage.IsLogoutButtonDisplayed());
	}

	@AfterMethod
	public void CloseTest(ITestResult result) throws InterruptedException {
		if (result.getStatus() == ITestResult.SUCCESS) {
			System.out.println("PASSED: " + result.getName());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("FAILED: " + result.getName());
		}
		System.out.println("_________________________________________________");
		Thread.sleep(1000);
		driver.quit();
	}
}
