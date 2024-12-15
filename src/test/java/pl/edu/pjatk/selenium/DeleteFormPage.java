package pl.edu.pjatk.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteFormPage {
    private final WebDriver webDriver;

    @FindBy(id = "options")
    private WebElement dropdown;

    @FindBy(id = "submit")
    private WebElement submitButton;

    private final Select select;

    public DeleteFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
        this.select = new Select(dropdown);
    }

    //Otwierana stronę
    public DeleteFormPage open(){
        this.webDriver.get("http://localhost:8080/view/deleteForm");
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(dropdown));
        return this;
    }

    public DeleteFormPage chooseOption(){
        String option = "Ford Blue";
        select.selectByVisibleText(option);

        var selectedOptions = select.getAllSelectedOptions();
        assertEquals(1, selectedOptions.size());
        assertEquals(option, selectedOptions.getFirst().getText());
        return this;
    }

    public DeleteFormPage submitForm(){
        this.submitButton.click();
        return this;
    }
}
