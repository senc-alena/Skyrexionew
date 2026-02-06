package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import user.User;

public class LoginPage extends BasePage {
    private final By loginInput = By.id("user-name");
    private final By loginButton = By.cssSelector("[name='login-button']");
    private final By passwordInput = By.cssSelector(DATA_TEST_PATTERN.formatted("password"));
    private final By errorMsc = By.cssSelector(DATA_TEST_PATTERN.formatted("error"));

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открываем страницу логина")
    public void open() {
        driver.get(BASE_URL);
    }

    @Step("Логинимся под пользователем '{user}'")
    public void login(String user, String password) {
        fillLoginField(user);
        fillPasswordField(password);
        driver.findElement(loginButton).click();
    }

    @Step("Логинимся под пользователем")
    public void loginUser(User user) {
        fillLoginField(user.getEmail());
        fillPasswordField(user.getPassword());
        driver.findElement(loginButton).click();
    }

    @Step("Вводим логин {user}")
    public void fillLoginField(String user) {
        driver.findElement(loginInput).sendKeys(user);
    }

    @Step("Вводим пароль {password}")
    public void fillPasswordField(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Проверяем видимость уведомления об ошибке.")
    public boolean isErrorDisplayed() {
        return driver.findElement(errorMsc).isDisplayed();
    }

    @Step("Проверяем текст ошибки.")
    public String getErrorText() {
        return driver.findElement(errorMsc).getText();
    }
}
