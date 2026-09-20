package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class PropertiesPage extends BasePage {
    private final By page = By.cssSelector(".properties-page");
    private final By cards = By.cssSelector(".properties-grid .property-card");
    private final By empty = By.cssSelector(".properties-main .no-properties");
    private final By loading = By.cssSelector(".properties-main .loading-container");
    private final By searchButton = By.cssSelector(".filters-card button[type='submit']");
    private final By resetButton = By.cssSelector(".filters-card button[type='button']");

    public PropertiesPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        visible(page);
        waitForResults();
        return !elements(By.cssSelector(".properties-header h1")).isEmpty();
    }

    public void waitForResults() {
        wait.until(d -> d.findElements(loading).isEmpty()
                && (!d.findElements(cards).isEmpty() || !d.findElements(empty).isEmpty()));
    }

    public int count() { waitForResults(); return elements(cards).size(); }
    public boolean hasNoResults() { waitForResults(); return !elements(empty).isEmpty(); }
    public List<WebElement> cards() { waitForResults(); return elements(cards); }

    public void search(String location, String minPrice, String maxPrice, String bedrooms) {
        fill(By.cssSelector(".filters-card [name='location']"), location);
        fill(By.cssSelector(".filters-card [name='minPrice']"), minPrice);
        fill(By.cssSelector(".filters-card [name='maxPrice']"), maxPrice);
        new Select(visible(By.cssSelector(".filters-card [name='bedrooms']"))).selectByValue(bedrooms);
        clickable(searchButton).click();
        waitForResults();
    }

    public void reset() {
        clickable(resetButton).click();
        waitForResults();
    }

    public String filterValue(String name) {
        return driver.findElement(By.cssSelector(".filters-card [name='" + name + "']")).getDomProperty("value");
    }

    public void openFirstCard() {
        waitForResults();
        clickable(cards).click();
        wait.until(ExpectedConditions.urlMatches(".*/properties/[^/]+$"));
    }

    public void favoriteFirstCard() {
        waitForResults();
        clickable(By.cssSelector(".properties-grid .property-card:first-child .favorite-btn")).click();
        wait.until(d -> !d.findElements(By.cssSelector(".properties-grid .property-card:first-child .favorite-btn.active")).isEmpty());
    }
}
