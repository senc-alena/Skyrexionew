package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PropertyReader;

import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

public class TestLogin extends BaseTest {

    @Test(invocationCount = 1, priority = 2, enabled = true)
    public void correctLogin() {
        System.out.println("TestLogin.correct !!!! in thread: " + Thread.currentThread().getId());
        loginPage.open();
        loginPage.login(withAdminPermission());

        assertTrue(productsPage.isTittleIsDisplayed(), "Заголовок не виден");
        assertEquals(productsPage.checkTittleName(), "Products", "Не верный заголовок");
    }

    @DataProvider(name = "incorrectLoginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"locked_out_user", password, "Epic sadface: Sorry, this user has been locked out."},
                {"", password, "Epic sadface: Username is required"},
                {user, "", "Epic sadface: Password is required"},
                {"Standard_user", password,
                        "Epic sadface: Username and password do not match any user in this service"}
        };
    }

    @Test(dataProvider = "incorrectLoginData", description = "тест проверяет авторизацию заблокированного пользователя",
            invocationCount = 1, priority = 1)
    public void incorrectLogin(String user, String password, String errorMsg) {
        System.out.println("TestLogin.incorrect !!!! in thread: " + Thread.currentThread().getId());
        loginPage.open();
        loginPage.login(user, password);

        assertTrue(loginPage.isErrorDisplayed(), "Нет сообщения об ошибке");
        assertEquals(loginPage.getErrorText(), errorMsg,
                "Не верный текст сообщения об ошибке");
    }
}
