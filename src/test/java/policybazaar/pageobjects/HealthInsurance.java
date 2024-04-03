package policybazaar.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import policybazaar.baseclass.BaseClass;
import policybazaar.pageactions.PageActions;
import policybazaar.utility.Utility;

public class HealthInsurance extends BaseClass {
	PageActions pageActions = new PageActions(driver);
	Utility utility = new Utility();
	
	public HealthInsurance(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}


	@FindBy(xpath="//a[contains(text(),'Insurance Products')]")
	private WebElement insuranceProduct;

	@FindBy(xpath="//a[@class='headlink' and contains(text(),'Health Insurance')]/following::ul[1]//a")
	private List<WebElement> healthInsuranceProducts;

	@FindBy(xpath = "//div[contains(@class,'ruby-col-3')][2]/ul")
	private WebElement healthInsuranceMenu;

	public boolean getHealthInsuranceMenu() {
		driver.get("https://www.policybazaar.com/");
		pageActions.hoverOnElement(insuranceProduct);
		try {
			if(healthInsuranceMenu.isDisplayed()){
				collectHealthInsuranceProducts();
				return true;
			}
		}
		catch(Exception e){
		}
		return false;
	}

	public void collectHealthInsuranceProducts(){
		String sheetName = "HealthInsuranceProducts";
		int row = 0;
		for(WebElement healthInsuranceProduct : healthInsuranceProducts) {
			try {
				utility.writeToExcel(sheetName, healthInsuranceProduct.getText(), row,0);
			}
			catch (Exception e){
				break;
			}
			row++;
		}
	}
}