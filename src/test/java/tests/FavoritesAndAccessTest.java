package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.FavoritesPage;
import pages.PropertiesPage;

public class FavoritesAndAccessTest extends BaseTest {
    @Test
    public void guestIsRedirectedFromProtectedPages() {
        for (String path : new String[] {"/favorites", "/add-property", "/my-properties", "/admin"}) {
            open(path);
            wait.until(d -> d.getCurrentUrl().endsWith("/login"));
            Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"), path);
        }
    }

    @Test
    public void userCanAddAndRemoveFavorite() {
        register("user");
        open("/properties");
        PropertiesPage properties = new PropertiesPage(driver);
        if (properties.count() == 0) throw new SkipException("Seed an approved property for favorites");
        String title = properties.cards().get(0).findElement(By.cssSelector(".property-title")).getText();
        properties.favoriteFirstCard();
        open("/favorites");
        FavoritesPage favorites = new FavoritesPage(driver);
        Assert.assertTrue(favorites.isLoaded());
        Assert.assertEquals(favorites.count(), 1);
        Assert.assertEquals(driver.findElement(By.cssSelector(".favorites-grid .property-title")).getText(), title);
        favorites.removeFirst();
        Assert.assertEquals(favorites.count(), 0);
    }

    @Test
    public void logoutInvalidatesBrowserSession() {
        register("user");
        logout();
        open("/favorites");
        wait.until(d -> d.getCurrentUrl().endsWith("/login"));
        Assert.assertNull(((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('token')"));
    }
}
