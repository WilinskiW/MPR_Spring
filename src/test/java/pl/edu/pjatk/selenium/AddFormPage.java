package pl.edu.pjatk.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddFormPage {
    private final WebDriver webDriver;

    @FindBy(id = "make")
    private WebElement makeInput;

    @FindBy(id = "color")
    private WebElement colorInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public AddFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
    }

    //Otwierana stronę
    public AddFormPage open(){
        this.webDriver.get("http://localhost:8080/view/addForm");
        return this;
    }

    public AddFormPage fillInNameInput(String text){
        this.makeInput.sendKeys(text);
        return this;
    }

    public AddFormPage fillInColorInput(String text){
        this.colorInput.sendKeys(text);
        return this;
    }

    public AddFormPage submitForm(){
        this.submitButton.click();
        return this;
    }
}
