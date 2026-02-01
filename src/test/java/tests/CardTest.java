package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CardTest extends BaseTest {
    final String goodName = "Sauce Labs Bike Light";

    @Test
    public void checkGoodsAdded() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isTittleIsDisplayed());

        productsPage.addGoodsToCard(goodName);
        productsPage.switchToCard();

        assertEquals(cardPage.checkTittleName(), "Your Cart");
        assertEquals(cardPage.getProductsNames().size(), 1);
        assertFalse(cardPage.getProductsNames().isEmpty());
        assertTrue(cardPage.getProductsNames().contains(goodName));
    }
}
