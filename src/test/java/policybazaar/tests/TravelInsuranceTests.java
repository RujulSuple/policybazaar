package policybazaar.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import policybazaar.baseclass.BaseClass;
import policybazaar.pageobjects.TravelInsurance;

import java.util.ArrayList;

public class TravelInsuranceTests extends BaseClass {

    private TravelInsurance travelInsurance;

    //Initializing the object of the TravelInsurance Class
    @BeforeClass(groups = {"smoke", "regression"})
    public void initTravelInsurancePage(){
        travelInsurance = new TravelInsurance();
    }

    //Visiting to TravelInsurance page
    @Test(priority = 1 ,groups = {"smoke", "regression"})
    public void navigateToTravelInsurance(){
        travelInsurance.visitTravelInsurance();
    }

    //Clicking on Continue button without Selecting country
    @Test(priority = 2, dependsOnMethods = "navigateToTravelInsurance")
    public void searchWithoutSelectingCountry(){
        travelInsurance.continueApplication();
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertTrue(status);
    }

    //Validating the suggestions field
    @Test(priority = 3, dependsOnMethods = "navigateToTravelInsurance", dataProvider = "searchResults")
    public void validateSearchSuggestions(String searchTerm, String suggestedDestination){
        Assert.assertTrue(suggestedDestination.contains(searchTerm));
    }

    @DataProvider(name = "searchResults", indices = {0})
    public String[][] getSearchSuggestions(){
        String searchTerm = travelInsurance.getData("SearchTerm");
        return travelInsurance.getSearchResults(searchTerm);
    }

    @Test(priority = 4, dependsOnMethods = "navigateToTravelInsurance",groups = {"regression"})
    public void validateCountrySelection(){
        ArrayList<String> countryNames = travelInsurance.getData("NonEuropeanCountry",2);
        travelInsurance.selectDestination(countryNames);
        Assert.assertEquals(countryNames, travelInsurance.selectedDestinations());
    }

    @Test(priority = 5, dependsOnMethods = "validateCountrySelection")
    public void validateRemovingCountrySelection(){
        travelInsurance.removeSelectedDestination();
        Assert.assertEquals( travelInsurance.selectedDestinations().size(),0);
    }

    @Test(priority = 6, dependsOnMethods = "navigateToTravelInsurance", groups = {"regression"})
    public void validateCountrySearch(){
        String countryName = travelInsurance.getData("EuropeanCountry");
        travelInsurance.selectDestination(countryName);
        travelInsurance.continueApplication();
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertFalse(status);
    }

    @Test(priority = 7, dependsOnMethods = "validateCountrySearch")
    public void searchWithoutDateSelection(){
        travelInsurance.continueApplication();
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertTrue(status);
    }

    @Test(priority = 8, dependsOnMethods = "validateCountrySearch")
    public void selectInvalidDate(){
        // Date Format Should be "2024-Mar-08"
        String sampleStartDate = travelInsurance.getData("InvalidStartDate");
        String sampleEndDate = travelInsurance.getData("InvalidEndDate");
        travelInsurance.selectDate("startDate",sampleStartDate);
        travelInsurance.selectDate("endDate",sampleEndDate);
        Assert.assertTrue(travelInsurance.checkErrorMessage());
    }

    @Test(priority = 9, dependsOnMethods = "validateCountrySearch", groups = {"regression"})
    public void searchWithValidDates(){
        String sampleStartDate = travelInsurance.getData("ValidStartDate");
        String sampleEndDate = travelInsurance.getData("ValidEndDate");
        travelInsurance.selectDate("startDate",sampleStartDate);
        travelInsurance.selectDate("endDate",sampleEndDate);
        String nextHeader = travelInsurance.getDetailPageHeader("searchWithValidDates","thirdStep");
        Assert.assertEquals(nextHeader,"How many people are travelling?");
    }

    @Test(priority = 10, dependsOnMethods = "searchWithValidDates", groups = {"regression"})
    public void validateTravellerNumberSelection(){
        int numberOfTravellers = Integer.parseInt(travelInsurance.getData("NumberOfTravellers"));
        Assert.assertTrue(travelInsurance.selectNumberOfTraveller(numberOfTravellers));
    }

    @Test(priority = 11, dependsOnMethods = "validateTravellerNumberSelection")
    public void searchWithoutTravellerAge(){
        travelInsurance.continueApplication();
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertTrue(status);
    }

    @Test(priority = 12, dependsOnMethods = "validateTravellerNumberSelection", groups = {"regression"})
    public void searchWithTravellersAge(){
        ArrayList<String> ages = travelInsurance.getData("Ages",2);
        travelInsurance.selectTravellerAge(ages);
        String nextHeader = travelInsurance.getDetailPageHeader("searchWithTravellersAge","fourthStep");
        Assert.assertTrue(nextHeader.contains("have a pre-existing medical condition?"));
    }

    @Test(priority = 13, dependsOnMethods = "searchWithTravellersAge")
    public void searchWithoutMedicalConditionDetail(){
        travelInsurance.continueApplication();
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertTrue(status);
    }

    @Test(priority = 14, dependsOnMethods = "searchWithTravellersAge", groups = {"regression"})
    public void searchWithMedicalConditionDetail(){
        String medicalCondition = travelInsurance.getData("MedicalCondition");
        String headerValue = travelInsurance.selectMedicalCondition(medicalCondition);
        Assert.assertEquals(headerValue,"Great! One last step to get your travel insurance plans");
    }

    @Test(priority = 15, dependsOnMethods = "searchWithMedicalConditionDetail")
    public void searchWithoutPhoneNumber(){
        travelInsurance.continueApplication();
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertTrue(status);
    }

    @Test(priority = 16, dependsOnMethods = "searchWithMedicalConditionDetail")
    public void searchWithInvalidPhoneNumber(){
        String phoneNumber = travelInsurance.getData("InvalidPhoneNumber");
        travelInsurance.enterPhoneNumber(phoneNumber);
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertTrue(status);
    }

    @Test(priority = 17, dependsOnMethods = "searchWithMedicalConditionDetail")
    public void searchWithValidPhoneNumber(){
        String phoneNumber = travelInsurance.getData("ValidPhoneNumber");
        travelInsurance.enterPhoneNumber(phoneNumber);
        boolean status = travelInsurance.checkErrorMessage();
        Assert.assertFalse(status);
    }

    @Test(priority = 18, dependsOnMethods = "searchWithValidPhoneNumber")
    public void validateStudentFilterWithoutPlanDetail(){
        String planType = travelInsurance.getData("PlanType");
        boolean status = travelInsurance.selectPlanType(planType);
        Assert.assertFalse(status);
    }

    @Test(priority = 19, dependsOnMethods = "searchWithValidPhoneNumber")
    public void validatePlanTypeDetailsSubmission(){
        String tripDuration = travelInsurance.getData("TripDuration");
        boolean status = travelInsurance.fillStudentPlanDetails(tripDuration);
        Assert.assertTrue(status);
    }

    @Test(priority = 20, dependsOnMethods = "searchWithValidPhoneNumber")
    public void validateSortingResults(){
        String sortType = travelInsurance.getData("SortType");
        travelInsurance.sortResults(sortType);
        int numberOfDataToCollect = Integer.parseInt(travelInsurance.getData("DataToCollect"));
        travelInsurance.collectInsuranceDetails(numberOfDataToCollect);
    }

    @AfterClass(groups = {"smoke", "regression"})
    public void teardown(){
        driver.quit();

    }
}



