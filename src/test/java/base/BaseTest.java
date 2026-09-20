package base;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.RegisterPage;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String baseUrl = System.getProperty("baseUrl", "http://localhost:3000");

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--window-size=1440,900");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    protected void open(String path) {
        driver.get(baseUrl + path);
    }

    protected String uniqueEmail(String prefix) {
        return prefix + System.nanoTime() + "@example.com";
    }

    protected String register(String role) {
        String email = uniqueEmail("qa-" + role + "-");
        open("/register");
        RegisterPage page = new RegisterPage(driver);
        page.EnterFullName("QA " + role);
        page.EnterEmail(email);
        page.EnterPassword("QaPass123!");
        page.EnterConfirmPassword("QaPass123!");
        page.SelectAccountType(role);
        page.ClickCreateAccount();
        wait.until(d -> !d.findElements(By.cssSelector("nav .user-info")).isEmpty());
        return email;
    }

    protected void login(String email, String password) {
        open("/login");
        LoginPage page = new LoginPage(driver);
        page.EnterEmail(email);
        page.EnterPassword(password);
        page.clickLogin();
        wait.until(d -> !d.findElements(By.cssSelector("nav .user-info")).isEmpty());
    }

    protected void logout() {
        driver.findElement(By.cssSelector("nav .nav-actions button.btn-secondary")).click();
        wait.until(d -> d.getCurrentUrl().endsWith("/login"));
    }
}
