package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {
	WebDriver driver;
	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
	}
	//Locators

	private By FullNameField = By.xpath("//input[@type='text']");

	private By EmailField = By.xpath("//input[@type='email']");

	private By PasswordField = By.xpath("(//input[@type='password'])[1]");

	private By ConfirmPasswordField = By.xpath("(//input[@type='password'])[2]");

	private By AccountTypeDropdown = By.xpath("//select");

	private By RegularUserOption = By.xpath("//option[@value='user']");

	private By AgentOption = By.xpath("//option[@value='agent']");

	private By CreateAccountButton = By.xpath("//button[@type='submit']");

	private By LoginLink = By.linkText("Login");
	
	private By LogoutButton=By.xpath("//button[contains(text(),'Logout')]");
	
	private By ErrorMessage=By.className("error-message");
	//Action
	public void EnterFullName(String fullName) {
		driver.findElement(FullNameField).sendKeys(fullName);
	}
	public void EnterEmail(String email) {
		driver.findElement(EmailField).sendKeys(email);
	}
	public void EnterPassword(String password) {
        driver.findElement(PasswordField).sendKeys(password);
    }

    public void EnterConfirmPassword(String confirmPassword) {
        driver.findElement(ConfirmPasswordField).sendKeys(confirmPassword);
    }

	public void SelectAccountType(String accountType) {
		Select select = new Select(driver.findElement(AccountTypeDropdown));
		select.selectByValue(accountType);
	}
	public void ClickCreateAccount() {
        driver.findElement(CreateAccountButton).click();
    }
	
	public boolean IsLogoutButtonDisplayed() {
		return driver.findElement(LogoutButton).isDisplayed();
	}
	
	public String ErrorMessage() {
		return driver.findElement(ErrorMessage).getText();
	}
	
	public String GetFullNameValidationMessage() {
		return driver.findElement(FullNameField).getDomProperty("validationMessage");
	}

	public String GetEmailValidationMessage() {
		return driver.findElement(EmailField).getDomProperty("validationMessage");
	}

	public String GetPasswordValidationMessage() {
		return driver.findElement(PasswordField).getDomProperty("validationMessage");
	}

	public String GetConfirmPasswordValidationMessage() {
		return driver.findElement(ConfirmPasswordField).getDomProperty("validationMessage");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
