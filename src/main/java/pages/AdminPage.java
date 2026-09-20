package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class AdminPage extends BasePage {
    public AdminPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        visible(By.cssSelector(".admin-dashboard .admin-header h1"));
        wait.until(d -> d.findElements(By.cssSelector(".admin-content .loading-container")).isEmpty());
        return !elements(By.cssSelector(".stats-grid .stat-card")).isEmpty();
    }

    public void openTab(int index) {
        isLoaded();
        elements(By.cssSelector(".admin-tabs .tab-btn")).get(index).click();
    }

    public boolean hasUsersTable() {
        return visible(By.cssSelector(".users-table-container .admin-table")).isDisplayed();
    }

    public boolean hasModeration() {
        return !elements(By.cssSelector(".moderation-table-container")).isEmpty();
    }

    private WebElement pendingRow(String title) {
        return visible(By.xpath("//div[contains(@class,'moderation-table-container')]//tr[td[normalize-space()='"
                + title + "']]"));
    }

    public void approve(String title) {
        openTab(1);
        pendingRow(title).findElement(By.cssSelector(".btn-approve")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                "//div[contains(@class,'moderation-table-container')]//tr[td[normalize-space()='"
                        + title + "']]")));
    }

    public void reject(String title) {
        openTab(1);
        pendingRow(title).findElement(By.cssSelector(".btn-reject")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept(); // confirmation
        wait.until(ExpectedConditions.alertIsPresent()).accept(); // success
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                "//div[contains(@class,'moderation-table-container')]//tr[td[normalize-space()='"
                        + title + "']]")));
    }

    public void editUserRole(String email, String role) {
        openTab(2);
        WebElement row = visible(By.xpath("//div[contains(@class,'users-table-container')]//tr[td[normalize-space()='"
                + email + "']]"));
        row.findElement(By.cssSelector(".btn-edit")).click();
        new Select(visible(By.cssSelector(".modal-content select"))).selectByValue(role);
        clickable(By.cssSelector(".modal-content .btn-save")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".modal-overlay")));
    }

    public void deleteUser(String email) {
        openTab(2);
        WebElement row = visible(By.xpath("//div[contains(@class,'users-table-container')]//tr[td[normalize-space()='"
                + email + "']]"));
        row.findElement(By.cssSelector(".btn-delete")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        wait.until(ExpectedConditions.invisibilityOf(row));
    }
}
