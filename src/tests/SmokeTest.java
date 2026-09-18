package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest {
	@Test
	public void OpenBrowser() {
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://127.0.0.1:3000");
		Assert.assertFalse(driver.getTitle().isEmpty());
		driver.quit();
	}
	
	
}
