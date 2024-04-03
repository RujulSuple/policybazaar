package policybazaar.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import policybazaar.baseclass.BaseClass;
import policybazaar.pageactions.PageActions;
import policybazaar.utility.Utility;
import policybazaar.utility.ExtentReportManager;

public class CarInsurance extends BaseClass {
	PageActions pageActions = new PageActions(driver);
	Utility utility = new Utility();
	public CarInsurance() {
		PageFactory.initElements(driver, this);
	}


	@FindBy(xpath="//a[contains(text(),'Insurance Products')]")
	private WebElement insuranceProducts;

	@FindBy(xpath="//a[@class='headlink' and contains(text(),'Car Insurance')]")
	private WebElement carInsuranceElement;

	@FindBy(xpath="//*[contains(@class,'input_box')]//input")
	public WebElement searchCarNumber;


	@FindBy(xpath="//div[@class='err' and contains(text(),'Please enter')]")
	public WebElement errorMessage;

	@FindBy(xpath="//input[@id='txtName']")
	public WebElement inputUsername;

	@FindBy(xpath="//input[@id='txtEmail']")
	public WebElement inputEmail;

	@FindBy(xpath="//input[@id='mobNumber']")
	public WebElement inputPhNumber;


	public void visitCarInsurance() {
		driver.get("https://www.policybazaar.com/");
		pageActions.hoverOnElement(insuranceProducts);
		carInsuranceElement.click();
	}

	public void submitCarNumber(String carNumber){
		searchCarNumber.clear();
		searchCarNumber.sendKeys(carNumber);
	}

	public boolean getErrorMessage(){
		Actions action = new Actions(driver);
		action.keyDown(Keys.ENTER).perform();
		try {
			if(errorMessage.isDisplayed()){
				ExtentReportManager.errorMessage=true;
				return true;
			}
			else{
				ExtentReportManager.errorMessage=false;
				return false;
			}
		}
		catch(Exception e){
		}
		ExtentReportManager.errorMessage=false;
		return false;
	}

	public String getData(String filedValue){
		String dataFile = "carInsuranceData";
		String[] testData = utility.parseTestData(dataFile, filedValue);
		return utility.getRandomData(testData);
	}

	public String[] getAllData(String filedValue){
		String dataFile = "carInsuranceData";
		return utility.parseTestData(dataFile, filedValue);
	}

	public void fillApplicationDetails(String name, String email, String number){
		inputUsername.clear();
		inputUsername.sendKeys(name);

		inputEmail.clear();
		inputEmail.sendKeys(email);

		inputPhNumber.clear();
		inputPhNumber.sendKeys(number);

		new Actions(driver).keyDown(Keys.ENTER).perform();
	}
}
