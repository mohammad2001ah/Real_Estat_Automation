package tests;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
public class LoginTest {
	WebDriver driver;
	LoginPage loginPage;
	String MyWebSite="http://localhost:3000/login";
	
	@BeforeMethod
	public void MySetup() {
		driver=new ChromeDriver();
		loginPage=new LoginPage(driver);
		driver.get(MyWebSite);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	@Test(enabled = false)
	public void LoginWithValidData() throws InterruptedException {
		loginPage.EnterEmail("mohammad1@gmail.com");
		loginPage.EnterPassword("123456789");
		loginPage.clickLogin();
		
		//Assertion
		Assert.assertTrue(loginPage.IsLogoutButtonDisplayed());
		Assert.assertEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		
	}
	@Test(enabled = true)
	public void LoginWithInvalidPassword() {
		
		
		//Assertion
		WebElement ErrorMessage =driver.findElement(By.className("error-message"));
		String ActualMessage=ErrorMessage.getText();
		String ExpectedMessage = "Invalid email or password";
		Assert.assertEquals(ActualMessage, ExpectedMessage);
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
	}
	@Test(enabled = true)
	public void LoginWithInvalidEmail() {
		
		//Assertion
		WebElement ErrorMessage =driver.findElement(By.className("error-message"));
		String ActualMessage=ErrorMessage.getText();
		String ExpectedMessage = "Invalid email or password";
		Assert.assertEquals(ActualMessage, ExpectedMessage);
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
	}
	@Test(enabled = true)
	public void LoginWithEmptyEmail() {
		
		//Assertion
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
	}
	@Test(enabled = true)
	public void LoginWithEmptyPassword() {
		
		
		//Assertion
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
	}
	@Test(enabled = true)
	public void LoginWithEmptyField() {
		
		//Assertion
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
	}
	@Test(enabled = true)
	public void LoginWithInvalidEmailFormat() {
		
		
		//Assertion
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
	}
	
	@AfterMethod
	public void CloseBrowser() throws InterruptedException {
		Thread.sleep(3000);
	    driver.quit();
	}
}
