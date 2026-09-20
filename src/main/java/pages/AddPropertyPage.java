package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddPropertyPage extends BasePage {
    public AddPropertyPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return visible(By.cssSelector(".add-property-page form.property-form")).isDisplayed();
    }

    public void fillRequired(String title) {
        fill(By.cssSelector(".property-form [name='title']"), title);
        fill(By.cssSelector(".property-form [name='price']"), "85000");
        fill(By.cssSelector(".property-form [name='location']"), "Amman");
        fill(By.cssSelector(".property-form [name='area']"), "110");
        fill(By.cssSelector(".property-form [name='bedrooms']"), "2");
        fill(By.cssSelector(".property-form [name='contactPhone']"), "0791234567");
    }

    public void upload(String... absolutePaths) {
        driver.findElement(By.id("images")).sendKeys(String.join("\n", absolutePaths));
        wait.until(d -> d.findElements(By.cssSelector(".image-preview-item")).size() == absolutePaths.length);
    }

    public void submit() {
        clickable(By.cssSelector(".property-form button[type='submit']")).click();
    }

    public String error() {
        return visible(By.cssSelector(".add-property-page .error-message")).getText();
    }

    public boolean success() {
        return visible(By.cssSelector(".add-property-page .success-message")).isDisplayed();
    }
}
