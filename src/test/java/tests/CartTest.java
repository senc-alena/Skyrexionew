package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static enums.TitleNaming.CART;
import static enums.TitleNaming.PRODUCTS;
import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

@Feature("Корзина")
public class CartTest extends BaseTest {
    final String goodName = "Sauce Labs Bike Light";

    @Story("Добавление товара в корзину и переход в неё")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Тест проверяет переход в корзину")
    public void checkGoodsAdded() {
        System.out.println("CardTest.correct !!!! in thread: " + Thread.currentThread().threadId());
        loginPage.open();
        loginPage.loginUser(withAdminPermission());
        assertEquals(productsPage.checkTittleName(), PRODUCTS.getDisplayName());

        productsPage.addGoodsToCard(goodName);
        productsPage.switchToCard();

        assertEquals(cardPage.checkTittleName(), CART.getDisplayName());
        assertFalse(cardPage.getProductsNames().isEmpty());
        assertEquals(cardPage.getProductsNames().size(), 1);
        assertTrue(cardPage.getProductsNames().contains(goodName));
    }
}
