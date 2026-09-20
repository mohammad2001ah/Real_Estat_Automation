package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterTest extends BaseTest {
    private void fill(RegisterPage page, String email, String password, String confirmation, String role) {
        page.EnterFullName("QA registration");
        page.EnterEmail(email);
        page.EnterPassword(password);
        page.EnterConfirmPassword(confirmation);
        page.SelectAccountType(role);
    }

    @Test
    public void registerUserAndAgent() {
        register("user");
        Assert.assertNotNull(((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('token')"));
        logout();
        register("agent");
        Assert.assertNotNull(((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('token')"));
    }

    @Test
    public void duplicateEmailShowsServerError() {
        String email = register("user");
        logout();
        open("/register");
        RegisterPage page = new RegisterPage(driver);
        fill(page, email, "QaPass123!", "QaPass123!", "user");
        page.ClickCreateAccount();
        Assert.assertEquals(wait.until(d -> d.findElement(By.className("error-message")).getText()),
                "User already exists");
    }

    @Test
    public void mismatchedAndShortPasswordsAreRejected() {
        open("/register");
        RegisterPage page = new RegisterPage(driver);
        fill(page, uniqueEmail("mismatch-"), "QaPass123!", "Different!", "user");
        page.ClickCreateAccount();
        Assert.assertFalse(wait.until(d -> d.findElement(By.className("error-message")).getText()).isBlank());
        open("/register");
        page = new RegisterPage(driver);
        fill(page, uniqueEmail("short-"), "12345", "12345", "user");
        page.ClickCreateAccount();
        Assert.assertFalse(wait.until(d -> d.findElement(By.className("error-message")).getText()).isBlank());
    }

    @Test
    public void requiredFieldsAreBlockedByBrowser() {
        open("/register");
        RegisterPage page = new RegisterPage(driver);
        page.ClickCreateAccount();
        Assert.assertFalse(page.GetFullNameValidationMessage().isBlank());
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/register"));
    }
}
