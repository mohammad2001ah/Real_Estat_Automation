package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

	protected WebDriver driver;

	@BeforeMethod
	public void Setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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