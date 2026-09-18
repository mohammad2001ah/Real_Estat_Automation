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

public class LoginPage {
	WebDriver driver;
	String MyWebSite="http://localhost:3000/login";
	
	@BeforeMethod
	public void MySetup() {
		driver=new ChromeDriver();
		driver.get(MyWebSite);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	@Test(enabled = false)
	public void LoginWithValidData() throws InterruptedException {
		//Web Element
		Thread.sleep(2000);
		WebElement openLoginPage=driver.findElement(By.linkText("Get Started"));
		openLoginPage.click();
		
		WebElement openLoginForm=driver.findElement(By.linkText("Login"));
		openLoginForm.click();
		Thread.sleep(2000);
		WebElement EmailField=driver.findElement(By.xpath("//input[@type='email']"));
		EmailField.sendKeys("mohammad1@gmail.com");
		
		WebElement PasswordField=driver.findElement(By.xpath("//input[@type='password']"));
		PasswordField.sendKeys("123456789");
		
		WebElement LoginButton=driver.findElement(By.xpath("//button[@type='submit']"));
		LoginButton.click();
		
		WebElement LogoutButton=driver.findElement(By.xpath("//button[contains(text(),'Logout')]"));
		boolean VisibleButton=LogoutButton.isDisplayed();
		//Assertion 
		System.out.println("Is The Button Visible?"+VisibleButton);
		Assert.assertTrue(VisibleButton);
		
		Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:3000/properties");
	}
	@Test(enabled = true)
	public void LoginWithInvalidPassword() {
		WebElement EmailField=driver.findElement(By.xpath("//input[@type='email']"));
		EmailField.sendKeys("mohammad1@gmail.com");
		
		WebElement PasswordField=driver.findElement(By.xpath("//input[@type='password']"));
		PasswordField.sendKeys("1234567");
		
		WebElement LoginButton=driver.findElement(By.xpath("//button[@type='submit']"));
		LoginButton.click();
		
		//Assertion
		WebElement ErrorMessage =driver.findElement(By.className("error-message"));
		String ActualMessage=ErrorMessage.getText();
		String ExpectedMessage = "Invalid email or password";
		Assert.assertEquals(ActualMessage, ExpectedMessage);
	}
	@AfterMethod
	public void CloseBrowser() throws InterruptedException {
		Thread.sleep(3000);
	    driver.quit();
	}
}
