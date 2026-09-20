package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.PropertiesPage;
import pages.PropertyDetailsPage;

public class PropertiesTest extends BaseTest {
    @Test
    public void listingAndDetailsShowConsistentData() {
        open("/properties");
        PropertiesPage page = new PropertiesPage(driver);
        Assert.assertTrue(page.isLoaded());
        if (page.count() == 0) throw new SkipException("Seed one approved property to test details");
        String title = page.cards().get(0).findElement(By.cssSelector(".property-title")).getText();
        String location = page.cards().get(0).findElement(By.cssSelector(".property-location")).getText();
        page.openFirstCard();
        PropertyDetailsPage details = new PropertyDetailsPage(driver);
        Assert.assertEquals(details.title(), title);
        Assert.assertTrue(details.location().contains(location.replace("📍", "").trim()));
        Assert.assertTrue(details.price().contains("JOD"));
        Assert.assertTrue(details.hasContactPhone());
        details.back();
        wait.until(d -> d.getCurrentUrl().endsWith("/properties"));
    }

    @Test
    public void filtersShowNoResultsAndReset() {
        open("/properties");
        PropertiesPage page = new PropertiesPage(driver);
        Assert.assertTrue(page.isLoaded());
        page.search("qa-location-with-no-matches-93842", "", "", "");
        wait.until(d -> !d.findElements(By.cssSelector(".properties-main .no-properties")).isEmpty());
        Assert.assertTrue(page.hasNoResults());
        page.reset();
        Assert.assertEquals(page.filterValue("location"), "");
        Assert.assertEquals(page.filterValue("minPrice"), "");
        Assert.assertEquals(page.filterValue("maxPrice"), "");
        Assert.assertEquals(page.filterValue("bedrooms"), "");
    }

    @Test
    public void locationAndPriceFiltersMatchVisibleCards() {
        open("/properties");
        PropertiesPage page = new PropertiesPage(driver);
        if (page.count() == 0) throw new SkipException("Seed one approved property for filter checks");
        String location = page.cards().get(0).findElement(By.cssSelector(".property-location"))
                .getText().replace("📍", "").trim();
        page.search(location, "", "", "");
        Assert.assertTrue(page.count() > 0);
        for (var card : page.cards()) {
            Assert.assertTrue(card.findElement(By.cssSelector(".property-location")).getText()
                    .toLowerCase().contains(location.toLowerCase()));
        }
        page.search("", "999999999999", "", "");
        wait.until(d -> !d.findElements(By.cssSelector(".properties-main .no-properties")).isEmpty());
        Assert.assertTrue(page.hasNoResults());
    }

    @Test
    public void bedroomsFilterKeepsMatchingProperties() {
        open("/properties");
        PropertiesPage page = new PropertiesPage(driver);
        if (page.count() == 0) throw new SkipException("Seed one approved property for bedroom filter");
        String bedrooms = page.cards().get(0).findElement(By.cssSelector(".property-specs .spec:first-child"))
                .getText().replaceAll("[^0-9]", "");
        if (!bedrooms.matches("[1-5]")) {
            throw new SkipException("Bedroom filter supports values 1 to 5");
        }
        page.search("", "", "", bedrooms);
        Assert.assertTrue(page.count() > 0);
        for (var card : page.cards()) {
            String actual = card.findElement(By.cssSelector(".property-specs .spec:first-child"))
                    .getText().replaceAll("[^0-9]", "");
            Assert.assertEquals(actual, bedrooms);
        }
    }

    @Test
    public void invalidPropertyIdHasRecoveryLink() {
        open("/properties/000000000000000000000000");
        PropertyDetailsPage details = new PropertyDetailsPage(driver);
        Assert.assertTrue(details.errorDisplayed());
        details.backFromError();
        wait.until(d -> d.getCurrentUrl().endsWith("/properties"));
    }
}
