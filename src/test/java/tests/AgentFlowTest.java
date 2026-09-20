package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddPropertyPage;
import pages.MyPropertiesPage;

public class AgentFlowTest extends BaseTest {
    @Test
    public void addPropertyNeedsFiveImages() {
        register("agent");
        open("/add-property");
        AddPropertyPage page = new AddPropertyPage(driver);
        Assert.assertTrue(page.isLoaded());
        page.fillRequired("QA image validation " + System.nanoTime());
        page.submit();
        Assert.assertFalse(page.error().isBlank());
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/add-property"));
    }

    @Test
    public void agentCanCreateAndDeleteOwnPendingProperty() throws Exception {
        register("agent");
        String title = "QA property " + System.nanoTime();
        open("/add-property");
        AddPropertyPage form = new AddPropertyPage(driver);
        form.fillRequired(title);
        form.upload(FixtureImages.createFive());
        form.submit();
        Assert.assertTrue(form.success(), "Property creation should succeed");
        open("/my-properties");
        MyPropertiesPage mine = new MyPropertiesPage(driver);
        Assert.assertTrue(mine.isLoaded());
        Assert.assertEquals(mine.cardByTitle(title).findElement(By.cssSelector(".property-title")).getText(), title);
        mine.delete(title);
        Assert.assertTrue(driver.findElements(By.xpath("//h3[normalize-space()='" + title + "']")).isEmpty());
    }
}
