package tests;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

public class ProductsTest extends BaseTest {
    List<String> goodsList =
            List.of("Test.allTheThings() T-Shirt (Red)",
                    "Sauce Labs Fleece Jacket", "Sauce Labs Bike Light");

    @Test
    public void checkGoodsAdded() {
        System.out.println("ProductsTest.correct !!!! in thread: " + Thread.currentThread().getId());
        loginPage.open();
        loginPage.login(withAdminPermission());
        assertTrue(productsPage.isTittleIsDisplayed());
        assertEquals(productsPage.checkTittleName(), "Products");

        for (int i = 0; i < goodsList.size(); i++) {
            productsPage.addGoodsToCard(goodsList.get(i));
        }

        productsPage.addGoodsToCard(2);
        assertEquals(productsPage.checkCounterValue(), "4");
        assertEquals(productsPage.checkCounterColor(), "rgba(226, 35, 26, 1)");
    }
}
