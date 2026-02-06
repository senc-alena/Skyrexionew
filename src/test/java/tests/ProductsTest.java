package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

@Feature("Продуктовая страница")
public class ProductsTest extends BaseTest {
    List<String> goodsList =
            List.of("Test.allTheThings() T-Shirt (Red)",
                    "Sauce Labs Fleece Jacket", "Sauce Labs Bike Light");

    @Story("Добавление нескольких товаров в корзину")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Тест проверяет верное количество добавленных товаров и цвет иконки корзины")
    public void checkGoodsAdded() {
        System.out.println("ProductsTest.correct !!!! in thread: " + Thread.currentThread().threadId());
        loginPage.open();
        loginPage.loginUser(withAdminPermission());
        assertTrue(productsPage.isTittleIsDisplayed());
        assertEquals(productsPage.checkTittleName(), "Products");

        for (String s : goodsList) {
            productsPage.addGoodsToCard(s);
        }

        productsPage.addGoodsToCard(2);
        assertEquals(productsPage.checkCounterValue(), "4");
        assertEquals(productsPage.checkCounterColor(), "rgba(226, 35, 26, 1)");
    }

    @Story("Изменение состояния кнопок Add to cart/Remove")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Тест проверяет добавление товаров в корзину и значение кнопок")
    public void checkMultipleItemsButtonState() {
        System.out.println("checkMultipleItemsButtonState !!!! in thread: " + Thread.currentThread().threadId());

        loginPage.open();
        loginPage.loginUser(withAdminPermission());

        for (String item : goodsList) {
            assertTrue(productsPage.isAddToCartButtonDisplayedForItem(item),
                    "Товар " + item + " должен иметь кнопку 'Add to cart'");
        }

        for (int i = 0; i < 2; i++) {
            productsPage.addGoodsToCard(goodsList.get(i));
        }

        assertTrue(productsPage.isRemoveButtonDisplayedForItem(goodsList.get(0)),
                "Первый товар должен иметь кнопку 'Remove'");
        assertTrue(productsPage.isRemoveButtonDisplayedForItem(goodsList.get(1)),
                "Второй товар должен иметь кнопку 'Remove'");
        assertTrue(productsPage.isAddToCartButtonDisplayedForItem(goodsList.get(2)),
                "Третий товар всё ещё должен иметь кнопку 'Add to cart'");

        assertEquals(productsPage.checkCounterValue(), "2",
                "Счётчик должен показывать 2 после добавления двух товаров");
    }

    @Story("Переключение кнопки Add to cart → Remove")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Тест проверяет переключение значение кнопки с Add to cart на Remove")
    public void checkButtonChangesFromAddToCartToRemove() {
        System.out.println("checkButtonChangesFromAddToCartToRemove !!!! in thread: " + Thread.currentThread().threadId());

        loginPage.open();
        loginPage.loginUser(withAdminPermission());
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

    @Story("Блокировка повторного добавления товара в корзину")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Тест проверяет невозможность повторного добавления товара в корзину")
    public void checkItemAlreadyInCartErrorMessage() {
        System.out.println("checkItemAlreadyInCartErrorMessage !!!! in thread: " + Thread.currentThread().threadId());
        loginPage.open();
        loginPage.loginUser(withAdminPermission());

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
