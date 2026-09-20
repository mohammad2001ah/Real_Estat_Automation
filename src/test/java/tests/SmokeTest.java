package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {
    @Test
    public void homePageLoads() {
        open("/");
        Assert.assertTrue(wait.until(d -> !d.findElements(By.cssSelector("nav .logo")).isEmpty()));
    }
}
