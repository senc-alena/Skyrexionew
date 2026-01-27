package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ProductsTest extends BaseTest {

    @Test
    public void checkGoodsAdded() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.isTittleIsDisplayed();
        productsPage.addGoodsToCard("Sauce Labs Bike Light");
        productsPage.addGoodsToCard("Sauce Labs Fleece Jacket");
        productsPage.addGoodsToCard("Test.allTheThings() T-Shirt (Red)");
        assertEquals(productsPage.checkCounterValue(), "3");
        assertEquals(productsPage.checkCounterColor(), "rgba(226, 35, 26, 1)");
    }
}
