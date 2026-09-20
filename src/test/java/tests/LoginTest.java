package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    @Test
    public void validUserLoginAndLogout() {
        String email = register("user");
        logout();
        login(email, "QaPass123!");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/properties"));
        logout();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
    }

    @Test
    public void invalidPasswordDoesNotLogin() {
        String email = register("user");
        logout();
        open("/login");
        LoginPage page = new LoginPage(driver);
        page.EnterEmail(email);
        page.EnterPassword("WrongPass123!");
        page.clickLogin();
        Assert.assertEquals(wait.until(d -> d.findElement(By.className("error-message")).getText()),
                "Invalid email or password");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
    }

    @Test
    public void nonexistentEmailDoesNotLogin() {
        open("/login");
        LoginPage page = new LoginPage(driver);
        page.EnterEmail(uniqueEmail("unknown-"));
        page.EnterPassword("WrongPass123!");
        page.clickLogin();
        Assert.assertEquals(wait.until(d -> d.findElement(By.className("error-message")).getText()),
                "Invalid email or password");
    }

    @Test
    public void emptyAndMalformedEmailAreBlockedByBrowser() {
        open("/login");
        LoginPage page = new LoginPage(driver);
        page.EnterPassword("QaPass123!");
        page.clickLogin();
        Assert.assertFalse(driver.findElement(By.cssSelector("input[type='email']"))
                .getDomProperty("validationMessage").isBlank());
        page.EnterEmail("not-an-email");
        page.clickLogin();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
        Assert.assertFalse(driver.findElement(By.cssSelector("input[type='email']"))
                .getDomProperty("validationMessage").isBlank());
    }
}
