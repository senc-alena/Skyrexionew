package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {

    @Test
    public void correctLogin() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        assertTrue(productsPage.isTittleIsDisplayed(), "Заголовок не виден");
        assertEquals(productsPage.getTitle(), "Products", "Не верный заголовок");
    }

    @Test
    public void incorrectLogin() {
        loginPage.open();
        loginPage.login("locked_out_user", "secret_sauce");

        assertTrue(loginPage.isErrorDisplayed(), "Нет сообщения об ошибке");
        assertEquals(loginPage.getErrorText(), "Epic sadface: Sorry, this user has been locked out.",
                "Не верный текст сообщения об ошибке");
    }

    @Test
    public void missingLogin() {
        loginPage.open();
        loginPage.login("", "secret_sauce");

        assertTrue(loginPage.isErrorDisplayed(), "Нет сообщения об ошибке");
        assertEquals(loginPage.getErrorText(), "Epic sadface: Username is required",
                "Не верный текст сообщения об ошибке");
    }

    @Test
    public void missingPassword() {
        loginPage.open();
        loginPage.login("standard_user", "");

        assertTrue(loginPage.isErrorDisplayed(), "Нет сообщения об ошибке");
        assertEquals(loginPage.getErrorText(), "Epic sadface: Password is required",
                "Не верный текст сообщения об ошибке");
    }

    @Test
    public void mistakeInLogin() {
        loginPage.open();
        loginPage.login("Standard_user", "secret_sauce");

        assertTrue(loginPage.isErrorDisplayed(), "Нет сообщения об ошибке");
        assertEquals(loginPage.getErrorText(),
                "Epic sadface: Username and password do not match any user in this service",
                "Не верный текст сообщения об ошибке");
    }
}
