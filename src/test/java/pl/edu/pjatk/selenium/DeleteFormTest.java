package pl.edu.pjatk.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DeleteFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp(){
        this.webDriver = new ChromeDriver();
    }

    @Test
    public void testAddForm(){
        DeleteFormPage page = new DeleteFormPage(webDriver);

        page.open()
                .chooseOption()
                .submitForm();
    }
}
