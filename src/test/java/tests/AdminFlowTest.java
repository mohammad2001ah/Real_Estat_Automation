package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.AddPropertyPage;
import pages.AdminPage;
import pages.MyPropertiesPage;
import pages.PropertiesPage;

public class AdminFlowTest extends BaseTest {
    private void loginAsAdmin() {
        String email = System.getProperty("adminEmail", "");
        String password = System.getProperty("adminPassword", "");
        if (email.isBlank() || password.isBlank()) {
            throw new SkipException("Set -DadminEmail and -DadminPassword to run admin tests");
        }
        login(email, password);
        wait.until(d -> d.getCurrentUrl().endsWith("/admin"));
    }

    @Test
    public void dashboardShowsPropertiesModerationAndUsers() {
        loginAsAdmin();
        AdminPage admin = new AdminPage(driver);
        Assert.assertTrue(admin.isLoaded());
        admin.openTab(1);
        Assert.assertTrue(admin.hasModeration());
        admin.openTab(2);
        Assert.assertTrue(admin.hasUsersTable());
    }

    @Test
    public void adminApprovesAgentPropertyAndPropertyBecomesPublic() throws Exception {
        if (System.getProperty("adminEmail", "").isBlank()
                || System.getProperty("adminPassword", "").isBlank()) {
            throw new SkipException("Set admin credentials");
        }
        String agent = register("agent");
        String title = "QA approval " + System.nanoTime();
        open("/add-property");
        AddPropertyPage form = new AddPropertyPage(driver);
        form.fillRequired(title);
        form.upload(FixtureImages.createFive());
        form.submit();
        Assert.assertTrue(form.success());
        logout();
        loginAsAdmin();
        AdminPage admin = new AdminPage(driver);
        admin.approve(title);
        open("/properties");
        PropertiesPage publicPage = new PropertiesPage(driver);
        publicPage.waitForResults();
        Assert.assertTrue(publicPage.cards().stream().anyMatch(
                card -> card.findElement(By.cssSelector(".property-title")).getText().equals(title)));
        // Delete only the property created by this test, through the agent's own UI.
        logout();
        login(agent, "QaPass123!");
        open("/my-properties");
        new MyPropertiesPage(driver).delete(title);
    }

    @Test
    public void adminRejectsAgentPropertyAndItStaysHidden() throws Exception {
        if (System.getProperty("adminEmail", "").isBlank()
                || System.getProperty("adminPassword", "").isBlank()) {
            throw new SkipException("Set admin credentials");
        }
        String agent = register("agent");
        String title = "QA rejection " + System.nanoTime();
        open("/add-property");
        AddPropertyPage form = new AddPropertyPage(driver);
        form.fillRequired(title);
        form.upload(FixtureImages.createFive());
        form.submit();
        Assert.assertTrue(form.success());
        logout();
        loginAsAdmin();
        new AdminPage(driver).reject(title);
        open("/properties");
        PropertiesPage publicPage = new PropertiesPage(driver);
        Assert.assertFalse(publicPage.cards().stream().anyMatch(
                card -> card.findElement(By.cssSelector(".property-title")).getText().equals(title)));
        logout();
        login(agent, "QaPass123!");
        open("/my-properties");
        new MyPropertiesPage(driver).delete(title);
    }

    @Test
    public void adminCanEditAndDeleteOnlyFreshTestUser() {
        if (System.getProperty("adminEmail", "").isBlank()
                || System.getProperty("adminPassword", "").isBlank()) {
            throw new SkipException("Set admin credentials");
        }
        String agent = register("agent");
        logout();
        loginAsAdmin();
        AdminPage admin = new AdminPage(driver);
        admin.editUserRole(agent, "user");
        admin.openTab(2);
        Assert.assertTrue(driver.findElement(By.xpath(
                "//div[contains(@class,'users-table-container')]//tr[td[normalize-space()='"
                        + agent + "']]//span[contains(@class,'role-badge')]")).getAttribute("class").contains("user"));
        admin.deleteUser(agent);
    }
}
