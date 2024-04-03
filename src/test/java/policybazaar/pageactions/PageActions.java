package policybazaar.pageactions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class PageActions {

    public WebDriver driver;

    public PageActions(WebDriver driver){
        this.driver = driver;
    }

    public void scrollToElement(WebElement element){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block : 'center'});", element);
    }

    public void hoverOnElement(WebElement element){
        (new Actions(driver)).moveToElement(element).perform();
    }

    public void clickUsingJS(WebElement pageElement){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pageElement);
    }

    public void selectOption(WebElement dropDownElement, String textOption){
        Select select = new Select(dropDownElement);
        select.selectByVisibleText(textOption);
    }

    public void selectOption(WebElement dropDownElement, int indexOfOption){
        Select select = new Select(dropDownElement);
        select.selectByIndex(indexOfOption);
    }
}
