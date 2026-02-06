package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CardPage extends BasePage {
    By product = By.cssSelector(".inventory_item_name");

    public CardPage(WebDriver driver) {
        super(driver);
    }

    @Step("Получаем список товаров из корзины.")
    public ArrayList<String> getProductsNames() {
        List<WebElement> allProducts = driver.findElements(product);
        ArrayList<String> names = new ArrayList<>();

        for (WebElement product : allProducts) {
            names.add(product.getText());
        }

        return names;
    }
}
