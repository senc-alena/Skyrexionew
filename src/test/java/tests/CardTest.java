package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

public class CardTest extends BaseTest {
    final String goodName = "Sauce Labs Bike Light";

    @Test
    public void checkGoodsAdded() {
        System.out.println("CardTest.correct !!!! in thread: " + Thread.currentThread().getId());
        loginPage.open();
        loginPage.login(withAdminPermission());
        assertEquals(productsPage.checkTittleName(), "Products");

        productsPage.addGoodsToCard(goodName);
        productsPage.switchToCard();

        assertEquals(cardPage.checkTittleName(), "Your Cart");
        assertFalse(cardPage.getProductsNames().isEmpty());
        assertEquals(cardPage.getProductsNames().size(), 1);
        assertTrue(cardPage.getProductsNames().contains(goodName));
    }
}
