package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PropertyDetailsPage extends BasePage {
    public PropertyDetailsPage(WebDriver driver) { super(driver); }

    public String title() {
        return visible(By.cssSelector(".property-info-section .property-header h1")).getText();
    }

    public String location() {
        return visible(By.cssSelector(".property-info-section .property-location")).getText();
    }

    public String price() {
        return visible(By.cssSelector(".property-info-section .property-price")).getText();
    }

    public boolean hasContactPhone() {
        return !visible(By.cssSelector(".contact-details .contact-item .value")).getText().isBlank();
    }

    public boolean errorDisplayed() {
        return visible(By.cssSelector(".error-page h2")).isDisplayed();
    }

    public void backFromError() {
        clickable(By.cssSelector(".error-page button")).click();
    }

    public void back() {
        clickable(By.cssSelector(".property-details-page .back-button")).click();
    }
}
