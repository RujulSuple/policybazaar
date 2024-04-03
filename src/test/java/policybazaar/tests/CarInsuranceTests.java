package policybazaar.tests;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import policybazaar.baseclass.BaseClass;
import policybazaar.pageobjects.CarInsurance;

public class CarInsuranceTests extends BaseClass {

	private CarInsurance carInsurance;

	//Initializing the object of the CarInsurance Class
	@BeforeClass(groups = {"smoke", "regression"})
	public void initCarInsurancePage() {
		carInsurance = new CarInsurance();
	}

	//Visiting to CarInsurance page
	@Test(priority = 1, groups = {"smoke", "regression"})
	public void navigateToCarInsurance() {
		carInsurance.visitCarInsurance();
	}

	@Test(priority = 2, dependsOnMethods = "navigateToCarInsurance")
	public void searchWithoutCarNumber(){
		carInsurance.submitCarNumber("");
		boolean status = carInsurance.getErrorMessage();
		Assert.assertTrue(status);
	}

	@Test(priority = 3, dependsOnMethods = "navigateToCarInsurance")
	public void searchWithInvalidCarNumber(){
		String carNumber = carInsurance.getData("InvalidCarNumber");
		carInsurance.submitCarNumber(carNumber);
		boolean status = carInsurance.getErrorMessage();
		Assert.assertTrue(status);
	}

	@Test(priority = 4, dependsOnMethods = "navigateToCarInsurance", groups = {"regression"})
	public void searchWithValidCarNumber(){
		String carNumber = carInsurance.getData("ValidCarNumber");
		carInsurance.submitCarNumber(carNumber);
		boolean status = carInsurance.getErrorMessage();
		Assert.assertFalse(status);
	}

	@Test(priority = 5, dependsOnMethods = "searchWithValidCarNumber")
	public void searchWithoutUserDetails(){
		carInsurance.fillApplicationDetails("","","");
		boolean status = carInsurance.getErrorMessage();
		Assert.assertTrue(status);
	}

	@Test(priority = 6, dependsOnMethods = "searchWithValidCarNumber")
	public void searchWithInvalidName(){
		String[] formData = carInsurance.getAllData("InvalidName");
		carInsurance.fillApplicationDetails(formData[0],formData[1],formData[2]);
		boolean status = carInsurance.getErrorMessage();
		Assert.assertTrue(status);
	}

	@Test(priority = 7, dependsOnMethods = "searchWithValidCarNumber")
	public void searchWithInvalidEmail(){
		String[] formData = carInsurance.getAllData("InvalidEmail");
		carInsurance.fillApplicationDetails(formData[0],formData[1],formData[2]);
		boolean status = carInsurance.getErrorMessage();
		Assert.assertTrue(status);
	}

	@Test(priority = 8, dependsOnMethods = "searchWithValidCarNumber")
	public void searchWithInvalidNumber(){
		String[] formData = carInsurance.getAllData("InvalidNumber");
		carInsurance.fillApplicationDetails(formData[0],formData[1],formData[2]);
		boolean status = carInsurance.getErrorMessage();
		Assert.assertTrue(status);
	}

	@Test(priority = 9, dependsOnMethods = "searchWithValidCarNumber", groups = {"regression"})
	public void searchWithValidDetails(){
		String[] formData = carInsurance.getAllData("ValidDetails");
		carInsurance.fillApplicationDetails(formData[0],formData[1],formData[2]);
		boolean status = carInsurance.getErrorMessage();
		Assert.assertFalse(status);
	}

	@AfterClass(groups = {"smoke", "regression"})
	public void teardown() {
		driver.quit();
	}
}
