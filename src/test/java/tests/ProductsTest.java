package tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

public class ProductsTest extends BaseTest {
    List<String> goodsList =
            List.of("Test.allTheThings() T-Shirt (Red)",
                    "Sauce Labs Fleece Jacket", "Sauce Labs Bike Light");

    List<String> itemsToTest = List.of("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt");

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

    @Test
    public void checkMultipleItemsButtonState() {
        System.out.println("checkMultipleItemsButtonState !!!! in thread: " + Thread.currentThread().getId());

        loginPage.open();
        loginPage.login(withAdminPermission());

        for (String item : itemsToTest) {
            assertTrue(productsPage.isAddToCartButtonDisplayedForItem(item),
                    "Товар " + item + " должен иметь кнопку 'Add to cart'");
        }

        for (int i = 0; i < 2; i++) {
            productsPage.addGoodsToCard(itemsToTest.get(i));
        }

        assertTrue(productsPage.isRemoveButtonDisplayedForItem(itemsToTest.get(0)),
                "Первый товар должен иметь кнопку 'Remove'");
        assertTrue(productsPage.isRemoveButtonDisplayedForItem(itemsToTest.get(1)),
                "Второй товар должен иметь кнопку 'Remove'");
        assertTrue(productsPage.isAddToCartButtonDisplayedForItem(itemsToTest.get(2)),
                "Третий товар всё ещё должен иметь кнопку 'Add to cart'");

        assertEquals(productsPage.checkCounterValue(), "2",
                "Счётчик должен показывать 2 после добавления двух товаров");
    }

    @Test
    public void checkButtonChangesFromAddToCartToRemove() {
        System.out.println("checkButtonChangesFromAddToCartToRemove !!!! in thread: " + Thread.currentThread().getId());

        loginPage.open();
        loginPage.login(withAdminPermission());
        assertTrue(productsPage.isTittleIsDisplayed());
        assertEquals(productsPage.checkTittleName(), "Products");

        String testItem = "Sauce Labs Backpack";
        assertTrue(productsPage.isAddToCartButtonDisplayedForItem(testItem),
                "Кнопка должна показывать 'Add to cart' перед добавлением товара");

        productsPage.addGoodsToCard(testItem);
        assertTrue(productsPage.isRemoveButtonDisplayedForItem(testItem),
                "Кнопка должна показывать 'Remove' после добавления товара");
        assertEquals(productsPage.checkCounterValue(), "1",
                "Счётчик корзины должен показывать 1 после добавления товара");
    }

    @Test
    public void checkItemAlreadyInCartErrorMessage() {
        System.out.println("checkItemAlreadyInCartErrorMessage !!!! in thread: " + Thread.currentThread().getId());
        loginPage.open();
        loginPage.login(withAdminPermission());

        String testItem = "Sauce Labs Backpack";
        assertTrue(productsPage.isAddToCartButtonDisplayedForItem(testItem),
                "Кнопка должна показывать 'Add to cart' перед добавлением товара");

        productsPage.addGoodsToCard(testItem);
        assertTrue(productsPage.isRemoveButtonDisplayedForItem(testItem),
                "Кнопка должна показывать 'Remove' после добавления товара");
        assertEquals(productsPage.checkCounterValue(), "1",
                "Счётчик должен показывать 1 после добавления товара");

        try {
            productsPage.addGoodsToCard(testItem);
            fail("Должно было выбросить NoSuchElementException - товар уже в корзине");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("ОШИБКА: Этот товар уже добавлен в корзину");
            System.out.println("(Кнопка 'Add to cart' не найдена, теперь она 'Remove')");
        }

        assertEquals(productsPage.checkCounterValue(), "1",
                "Счётчик не должен измениться при попытке повторного добавления");
        productsPage.addGoodsToCard("Sauce Labs Bike Light");
        assertEquals(productsPage.checkCounterValue(), "2",
                "После добавления другого товара должно быть 2 товара в корзине");
    }
}
