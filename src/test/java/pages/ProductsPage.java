package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {
    private static final String ADD_TO_PATTERN =
            "//*[text()='%s']//ancestor::div[@class='inventory_item']" +
                    "//child::button[text()='Add to cart']";
    private final By title = By.cssSelector("[data-test='title']");
    private final By cardCounter = By.cssSelector(DATA_TEST_PATTERN.formatted("shopping-cart-badge"));

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTittleIsDisplayed() {
        return driver.findElement(title).isDisplayed();
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }

    public void addGoodsToCard(String goodsName) {
        By addToCard = By.xpath(ADD_TO_PATTERN.formatted(goodsName));
        driver.findElement(addToCard).click();
    }

    public String checkCounterValue() {
        return driver.findElement(cardCounter).getText();
    }

    public String checkCounterColor() {
        return driver.findElement(cardCounter).getCssValue("background-color");
    }
}
