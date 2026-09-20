package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	//POM
	WebDriver driver;
	//Locators
	private By EmailField=By.xpath("//input[@type='email']");
	private By PasswordField=By.xpath("//input[@type='password']");
	private By LoginButton=By.xpath("//button[@type='submit']");
	private By LogoutButton=By.xpath("//button[contains(text(),'Logout')]");
	private By ErrorMessage=By.className("error-message");
	
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}
	//Actions
	public void EnterEmail(String email) {
		driver.findElement(EmailField).sendKeys(email);
	}
	
	public void EnterPassword(String password) {
		driver.findElement(PasswordField).sendKeys(password);
	}
	
	public void clickLogin() {
	    driver.findElement(LoginButton).click();
	}
	
	public String ErrorMessage() {
		return driver.findElement(ErrorMessage).getText();
	}
	
	public boolean IsLogoutButtonDisplayed() {
		return driver.findElement(LogoutButton).isDisplayed();
	}

}
