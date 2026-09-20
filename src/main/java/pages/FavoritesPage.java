package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FavoritesPage extends BasePage {
    public FavoritesPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return visible(By.cssSelector(".favorites-page .favorites-header h1")).isDisplayed();
    }

    public int count() {
        isLoaded();
        return elements(By.cssSelector(".favorites-grid .property-card")).size();
    }

    public void removeFirst() {
        clickable(By.cssSelector(".favorites-grid .property-card:first-child .favorite-btn")).click();
        wait.until(d -> d.findElements(By.cssSelector(".favorites-grid .property-card")).isEmpty());
    }
}
