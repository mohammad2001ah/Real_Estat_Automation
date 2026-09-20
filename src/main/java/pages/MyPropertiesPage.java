package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MyPropertiesPage extends BasePage {
    public MyPropertiesPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return visible(By.cssSelector(".my-properties-page h1")).isDisplayed();
    }

    public WebElement cardByTitle(String title) {
        By row = By.xpath("//div[contains(@class,'property-item')][.//h3[normalize-space()="
                + xpathLiteral(title) + "]]");
        return visible(row);
    }

    public void delete(String title) {
        WebElement row = cardByTitle(title);
        row.findElement(By.cssSelector(".property-actions .btn-danger")).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        wait.until(ExpectedConditions.invisibilityOf(row));
    }

    private String xpathLiteral(String text) {
        if (!text.contains("'")) return "'" + text + "'";
        return "\"" + text + "\"";
    }
}
