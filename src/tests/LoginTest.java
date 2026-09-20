package tests;

import java.time.Duration;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {
	LoginPage loginPage;
	String MyWebSite="http://localhost:3000/login";
	
	@BeforeMethod
	public void MySetup() {
		loginPage=new LoginPage(driver);
		driver.get(MyWebSite);
	}
	@Test(enabled = true,priority = 1)
	public void LoginWithValidData() throws InterruptedException {

		loginPage.EnterEmail("mohammad1@gmail.com");
		loginPage.EnterPassword("123456789");
		loginPage.clickLogin();
		
		//Assertion
		Assert.assertTrue(loginPage.IsLogoutButtonDisplayed());
		Assert.assertEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 1 - Login With Valid Data");
		
	}
	@Test(enabled = true,priority = 2)
	public void LoginWithInvalidPassword() {
		loginPage.EnterEmail("mohammad1@gmail.com");
		loginPage.EnterPassword("123587");
		loginPage.clickLogin();
		loginPage.ErrorMessage();
		
		//Assertion
		Assert.assertEquals(loginPage.ErrorMessage(), "Invalid email or password");
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 2 - Login With Invalid Password");
	}
	@Test(enabled = true,priority = 3)
	public void LoginWithInvalidEmail() {
		loginPage.EnterEmail("invalid55@gmail.com");
		loginPage.EnterPassword("12356789");
		loginPage.clickLogin();
		loginPage.ErrorMessage();
		//Assertion
		Assert.assertEquals(loginPage.ErrorMessage(), "Invalid email or password");
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 3 - Login With Invalid Email");
	}
	@Test(enabled = true,priority = 4)
	public void LoginWithEmptyEmail() {
		loginPage.EnterEmail("");
		loginPage.EnterPassword("123456789");
		loginPage.clickLogin();
		
		//Assertion
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/login");
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 4 - Login With Empty Email");

	}
	
	@Test(enabled = true,priority = 5)
	public void LoginWithEmptyPassword() {
		loginPage.EnterEmail("");
		loginPage.EnterPassword("123456789");
		loginPage.clickLogin();
		
		//Assertion
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/login");
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 5 - Login With Empty Password");
	}
	@Test(enabled = true,priority = 6)
	public void LoginWithEmptyField() {
		loginPage.EnterEmail("");
		loginPage.EnterPassword("");
		loginPage.clickLogin();
		
		//Assertion
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/login");
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 6- Login With Empty Fields");
	}
	@Test(enabled = true,priority = 7)
	public void LoginWithInvalidEmailFormat() {
		loginPage.EnterEmail("moahmmad1.gmail.com");
		loginPage.EnterPassword("123456789");
		loginPage.clickLogin();
		
		//Assertion
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/login");
		Assert.assertNotEquals(driver.getCurrentUrl(),"http://localhost:3000/properties");
		System.out.println("Test 7 - Login With Invalid Email Format");
	}
	
}
