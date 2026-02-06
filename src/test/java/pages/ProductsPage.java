package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {
    private static final String ADD_TO_PATTERN =
            "//*[text()='%s']//ancestor::div[@class='inventory_item']" +
                    "//child::button[text()='Add to cart']";
    private static final String ITEM_BUTTON_PATTERN =
            "//*[text()='%s']//ancestor::div[@class='inventory_item']" +
                    "//child::button";
    private final By title = By.cssSelector("[data-test='title']");
    private final By cardLink = By.cssSelector(DATA_TEST_PATTERN.formatted("shopping-cart-badge"));

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTittleIsDisplayed() {
        return driver.findElement(title).isDisplayed();
    }

    @Step("Добавляем товар '{goodsName}' в корзину")
    public void addGoodsToCard(String goodsName) {
        By addToCard = By.xpath(ADD_TO_PATTERN.formatted(goodsName));
        driver.findElement(addToCard).click();
    }

    @Step("Добавляем товар #{goodIndex} в корзину (по индексу)")
    public void addGoodsToCard(int goodIndex) {
        driver.findElements(By.xpath("//*[text()='Add to cart']")).get(goodIndex).click();
    }

    @Step("Получаем количество товаров в корзине.")
    public String checkCounterValue() {
        return driver.findElement(cardLink).getText();
    }

    @Step("Получаем цвет корзины.")
    public String checkCounterColor() {
        return driver.findElement(cardLink).getCssValue("background-color");
    }

    @Step("Переходим в корзину")
    public void switchToCard() {
        driver.findElement(cardLink).click();
    }

    @Step("Получаем текст кнопки товара '{goodsName}'.")
    public String getButtonTextForItem(String goodsName) {
        By itemButton = By.xpath(ITEM_BUTTON_PATTERN.formatted(goodsName));
        return driver.findElement(itemButton).getText();
    }

    @Step("Проверяем, что у товара '{goodsName}' кнопка 'Remove'.")
    public boolean isRemoveButtonDisplayedForItem(String goodsName) {
        return getButtonTextForItem(goodsName).equals("Remove");
    }

    @Step("Проверяем, что у товара '{goodsName}' кнопка 'Add to cart'.")
    public boolean isAddToCartButtonDisplayedForItem(String goodsName) {
        return getButtonTextForItem(goodsName).equals("Add to cart");
    }
}
