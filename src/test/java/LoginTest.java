import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {
    @Test
    public void firstLogin() {
        browser.findElement(By.id("user-name")).sendKeys("standart_user");
        browser.findElement(By.id("user-name")).sendKeys(Keys.CONTROL + "A");
        browser.findElement(By.id("user-name")).sendKeys(Keys.BACK_SPACE);
        browser.findElement(By.id("user-name")).sendKeys("standard_user");
        browser.findElement(By.xpath("//*[@data-test='password']")).sendKeys("secret_sauce");
        browser.findElement(By.cssSelector("[name='login-button']")).click();

        boolean tittleIsDisplayed = browser.findElement(By.cssSelector("[data-test='title']")).isDisplayed();
        assertTrue(tittleIsDisplayed, "Заголовок не виден");

        String titleName = browser.findElement(By.cssSelector("[data-test='title']")).getText();
        assertEquals(titleName, "Products", "Не верный заголовок");
    }

    @Test
    public void incorrectLogin() {
        browser.findElement(By.id("user-name")).sendKeys("locked_out_user");
        browser.findElement(By.xpath("//*[@data-test='password']")).sendKeys("secret_sauce");
        browser.findElement(By.cssSelector("[name='login-button']")).click();

        boolean errorIsDisplayed = browser.findElement(By.cssSelector("[data-test='error']")).isDisplayed();
        assertTrue(errorIsDisplayed, "Нет сообщения об ошибке");

        String errorMsg = browser.findElement(By.cssSelector("[data-test='error']")).getText();
        assertEquals(errorMsg, "Epic sadface: Sorry, this user has been locked out.",
                "Не верный текст сообщения об ошибке");
    }

}
