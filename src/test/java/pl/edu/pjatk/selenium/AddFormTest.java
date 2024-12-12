package pl.edu.pjatk.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//Aby test zadziałał aplikacja (czyli serwer) musi odpala przed testem
public class AddFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp(){
        this.webDriver = new ChromeDriver();
    }

    @Test
    public void testAddForm(){
        AddFormPage page = new AddFormPage(webDriver);

        page.open()
                .fillInNameInput("Porsche")
                .fillInColorInput("Black")
                .submitForm();
    }
}
