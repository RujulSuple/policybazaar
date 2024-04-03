package policybazaar.tests;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import policybazaar.baseclass.BaseClass;
import policybazaar.pageobjects.HealthInsurance;


public class HealthInsuranceTests extends BaseClass{
	private HealthInsurance healthInsuranceObj;
	
	@BeforeClass(groups = {"smoke", "regression"})
	public void initHealthInsurancePage() {
		healthInsuranceObj = new HealthInsurance(driver);
	}

	@Test(priority=1, groups = {"smoke", "regression"})
	public void validateHeathInsuranceMenu() {
		boolean status = healthInsuranceObj.getHealthInsuranceMenu();
		Assert.assertTrue(status);
	}

	@AfterClass(groups = {"smoke", "regression"})
	public void teardown() {
		driver.quit();
	}
}
