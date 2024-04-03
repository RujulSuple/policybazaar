package policybazaar.pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import policybazaar.baseclass.BaseClass;
import policybazaar.pageactions.PageActions;
import policybazaar.utility.Utility;
import policybazaar.utility.ExtentReportManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TravelInsurance extends BaseClass{

    PageActions pageActions = new PageActions(driver);
    Utility utility = new Utility();

    public TravelInsurance(){
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//a[contains(text(),'Insurance Products')]")
    private WebElement insuranceProducts;

    @FindBy(xpath = "//a[@class='headlink' and contains(text(),'Other Insurance')]/following::ul[1]/li[1]//a")
    private WebElement travelInsurancePage;

    @FindBy(xpath = "//div[@class='nextbtn']//button")
    private List<WebElement> continueButton;    // List of two Continue button element. 1st for detail form, 2nd for main city page.

    @FindBy(xpath = "//*[@class='err' and @style='display: block;']")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@id='selected-destinations']//input")
    private WebElement searchBox;

    @FindBy(xpath = "//ul[contains(@class,'autocomplete')]/li")
    private List<WebElement> searchSuggestions;

    @FindBy(xpath = "//*[@id='selected-destinations']/p")
    private List<WebElement> selectedDestinations;

    @FindBy(xpath = "//*[contains(@id,'countryRemove')]")
    private List<WebElement> removeSelectedDestination;

    @FindBy(xpath = "//li[contains(@class,'active') and not(contains(@class,'checked'))]")
    private WebElement activeApplicationTab;

    @FindBy(xpath = "//*[@id='startdate']")
    private WebElement startDate;

    @FindBy(xpath = "//*[@id='enddate']")
    private WebElement endDate;

    @FindBy(xpath = "//*[contains(@class,'previous-action')]")
    private WebElement previousCalendarPage;

    @FindBy(xpath = "//*[contains(@class,'next-action')]")
    private WebElement nextCalendarPage;

    @FindBy(xpath = "//*[contains(@class,'select-months')]/option[@selected='selected']")
    private WebElement currentCalendarMonth;

    @FindBy(xpath = "//*[contains(@class,'select-years')]/option[@selected='selected']")
    private WebElement currentCalendarYear;

    @FindBy(xpath = "//*[@class='lightpick__days']/div")
    private List<WebElement> calendarDates;

    @FindBy(xpath = "//div[@class='travellerDetails']//select")
    private List<WebElement> ageSelectionBoxes;

    @FindBy(xpath = "//div[contains(@class,'tcl-item')]")
    private List<WebElement> numberOfTraveller;

    @FindBy(xpath = "//label[@class='radioButton']//input")
    private List<WebElement> medicalConditions;

    @FindBy(xpath = "//*[@id='travelmobile']")
    private WebElement phoneNumber;

    @FindBy(xpath = "//input[@id='multiTrip' and @type='radio']")
    private WebElement frequentFlyer;

    @FindBy(xpath = "//input[@id='studentTrip' and @type='radio']")
    private WebElement studentType;

    @FindBy(xpath = "//*[@class='multiTripOptions']//input")
    private List<WebElement> multiTripDurations;

    @FindBy(xpath = "//input[contains(@id, 'Traveller') and @type='checkbox']")
    private List<WebElement> studentTravellers;

    @FindBy(xpath = "//*[contains(@class,' tripDurationInput')]//select")
    private WebElement studentTripDuration;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElement applyFilter;

    @FindBy(xpath = "//*[@class='filter_name_heading']")
    private WebElement sortByButton;

    @FindBy(xpath = "//*[contains(@name,'sort') and @type='radio']")
    private List<WebElement> sortValues;

    @FindBy(xpath = "//*[contains(@class,'insurerName')]")
    private List<WebElement> insurerNames;

    @FindBy(xpath = "//*[@class='premiumPlanPrice']")
    private List<WebElement> insurancePrice;

    @FindBy(xpath = "//*[contains(@class, 'close') and contains(@class, 'box')]")
    private WebElement closeResultPopup;

    public void visitTravelInsurance(){
        driver.get("https://www.policybazaar.com");
        pageActions.hoverOnElement(insuranceProducts);
        pageActions.scrollToElement(travelInsurancePage);
        pageActions.clickUsingJS(travelInsurancePage);
    }

    public void continueApplication(){
        if(continueButton.get(0).isDisplayed()){
//            continueButton.get(0).click();
            pageActions.clickUsingJS(continueButton.get(0));
        }
        else if(continueButton.get(1).isDisplayed()){
//            continueButton.get(1).click();
            pageActions.clickUsingJS(continueButton.get(1));
        }
    }

    public boolean checkErrorMessage(){
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
        catch(NoSuchElementException e){
        }
        ExtentReportManager.errorMessage=false;
        return false;
    }

    public String getData(String filedValue){
        String dataFile = "travelInsuranceData";
        String[] testData = utility.parseTestData(dataFile,filedValue);
        return utility.getRandomData(testData);
    }

    public ArrayList<String> getData(String filedValue, int requiredDataSize){
        String dataFile = "travelInsuranceData";
        String[] testData = utility.parseTestData(dataFile,filedValue);
        ArrayList<String> dataList = new ArrayList<>();
        if(testData.length<=requiredDataSize){
            return new ArrayList<>(Arrays.asList(testData));
        }
        else{
            for(int i=0; i<requiredDataSize;i++){
                dataList.add(testData[i]);
            }
            return dataList;
        }
    }

    public void enterDestination(String destinationName){
        searchBox.clear();
        searchBox.click();
        searchBox.sendKeys(destinationName);
    }

    public String[][] getSearchResults(String searchTerm){
        enterDestination(searchTerm);
        String[][] suggestions = new String[searchSuggestions.size()][2];
        for(int i=0; i<searchSuggestions.size();i++){
            suggestions[i][0] = searchTerm;
            suggestions[i][1] = searchSuggestions.get(i).getText();
        }
        return suggestions;
    }

    public void selectDestination(String location){
        if(!selectedDestinations().contains(location)){
            enterDestination(location);
            new Actions(driver).keyDown(Keys.ENTER).perform();
            searchBox.clear();
        }
    }

    public void selectDestination(ArrayList<String> location){
        for(String locationName : location){
            selectDestination(locationName);
        }
    }

    public ArrayList<String> selectedDestinations(){
        ArrayList<String> selections = new ArrayList<>();
        for(WebElement destinations: selectedDestinations){
            selections.add(destinations.getText().replace("×","").trim());
        }
        return selections;
    }

    public void removeSelectedDestination(){
        List<WebElement> selectedLocations = removeSelectedDestination;
        for(WebElement deselectDestination : selectedLocations){
            deselectDestination.click();
        }
    }

    public String getDetailPageHeader(String methodName, String stepName){
        continueApplication();
        if(!checkErrorMessage()){
            try {
                return driver.findElement(By.xpath("//div[@class='" + stepName + "']/div")).getText().trim();
            }
            catch(Exception e){
            }
        }
        return null;
    }

    public void moveCalendarForward(){
        nextCalendarPage.click();
    }

    public boolean getDateElement(String userDate){
        List<WebElement> dates = calendarDates;
        for(WebElement date: dates){
            try {
                if (date.getText().equals(userDate)) {
                    date.click();
                    activeApplicationTab.click();
                    return true;
                }
            }
            catch(ElementClickInterceptedException exception) {
                return false;
            }
        }
        return false;
    }

    public boolean selectDate(String dateType ,String date){
        if(dateType.equals("startDate")){
            startDate.click();
        }
        else if (dateType.equals("endDate")){
            pageActions.clickUsingJS(endDate);
        }

        ArrayList<String> months = new ArrayList<>(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));

        String[] currentDate = utility.getCurrentDate();
        int currYear = Integer.parseInt(currentDate[0]);
        String currMonth = months.get(Integer.parseInt(currentDate[1])-1);
        int currDay = Integer.parseInt(currentDate[2]);

        String[] userDate = date.split("-");
        int usrYear = Integer.parseInt(userDate[0]);
        String usrMonth = userDate[1];
        int usrDay = Integer.parseInt(userDate[2]);


        if(currYear<=usrYear){
            while(usrYear!=Integer.parseInt(currentCalendarYear.getText())) {
                if (nextCalendarPage.isDisplayed()) {
                    moveCalendarForward();
                }
                else {
                    return false;
                }
            }
            if(months.indexOf(currMonth)<= months.indexOf(usrMonth)){
                while(!(currentCalendarMonth.getText().contains(usrMonth))){
                    if (nextCalendarPage.isDisplayed()) {
                        nextCalendarPage.click();
                    }
                    else {
                        return false;
                    }
                }
                if(currDay<=usrDay){
                    if(!getDateElement(Integer.toString(usrDay))){
                        return false;
                    }
                }
                else{
                    if(getDateElement(Integer.toString(usrDay))){
                        return false;
                    }
                }
            }
            else{
                if(previousCalendarPage.isDisplayed()){
                    return false;
                }
            }
        }
        else{
            if(previousCalendarPage.isDisplayed()){
                return false;
            }
        }
        return true;
    }

    public boolean selectNumberOfTraveller(int travellerNumber){
        WebElement userSelection = numberOfTraveller.get(travellerNumber-1);
        userSelection.click();
        return (ageSelectionBoxes.size()==travellerNumber);
    }

    public void selectTravellerAge(ArrayList<String> ages){
        for(int i=0; i<ageSelectionBoxes.size();i++){
            pageActions.selectOption(ageSelectionBoxes.get(i),Integer.parseInt(ages.get(i)));
        }
    }

    public String selectMedicalCondition(String medicalCondition) {
        medicalConditions.get(1).click();
        return driver.findElement(By.xpath("//div[@class='fifthStep']/div")).getText().trim();
    }

    public void enterPhoneNumber(String number){
        phoneNumber.clear();
        phoneNumber.sendKeys(number);
        continueApplication();
    }

    public void selectFrequentPlanType(int tripDuration){
        frequentFlyer.click();
        ArrayList<Integer> availableDurations = new ArrayList<>(Arrays.asList(30,45,60,90));
        int selectedDuration = availableDurations.indexOf(tripDuration)+1;
        multiTripDurations.get(selectedDuration).click();
        applyFilter.click();
    }

    public boolean selectPlanType(String planType){
        if(planType.equalsIgnoreCase("Frequent flyer plans")) {
            frequentFlyer.click();
        }
        else if (planType.equalsIgnoreCase("Student plans")) {
            studentType.click();
        }
        return applyDetails();
    }

    public boolean applyDetails(){
        if(applyFilter.isEnabled()) {
            applyFilter.click();
            return true;
        }
        return false;
    }

    public boolean fillStudentPlanDetails(String tripDuration){
        for(WebElement students: studentTravellers){
            try {
                students.click();
            }
            catch(ElementClickInterceptedException e){
                pageActions.clickUsingJS(students);
            }
        }
        pageActions.selectOption(studentTripDuration,tripDuration);
        return applyDetails();
    }

    public void sortResults(String sortType){
        try {
            sortByButton.click();
        }
        catch (Exception e){
            pageActions.clickUsingJS(sortByButton);
        }

        if(sortType.equalsIgnoreCase("low")){
            try {
                sortValues.get(1).click();
            }
            catch (Exception e){
                pageActions.clickUsingJS(sortValues.get(1));
            }
        } else if (sortType.equalsIgnoreCase("high")) {
            try {
                sortValues.get(2).click();
            }
            catch (Exception e){
                pageActions.clickUsingJS(sortValues.get(2));
            }
        }
        else{
            try {
                sortValues.get(0).click();
            }
            catch (Exception e){
                pageActions.clickUsingJS(sortValues.get(0));
            }
        }
    }

    public void collectInsuranceDetails(int listSize){

        ArrayList<String> insurers = new ArrayList<>();
        ArrayList<String> premiumPrices = new ArrayList<>();
        int i=0;
        for(WebElement policies: insurerNames){
            pageActions.scrollToElement(policies);
            insurers.add(policies.getText());
            premiumPrices.add(insurancePrice.get(i).getText());
            if(insurers.size()==listSize){
                break;
            }
            i++;
        }
        int currIndex=0;
        for(int row = 0; row<insurers.size();row++){
            try {
                utility.writeToExcel("InsuranceDetails", insurers.get(currIndex), row,0);
                utility.writeToExcel("InsuranceDetails", premiumPrices.get(currIndex), row,1);
            }
            catch (IOException e){
                System.out.println("Some error occurred while storing data in excel");
                break;
            }
            currIndex++;
        }
    }
}
