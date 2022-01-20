package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.*;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class MyFirstTestCase extends BaseTest {
    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {

        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        StorePage storePage = new HomePage(driver)
                .load()
                .navigateToStoreUsingMenu()
                .search(searchFor);
        //storePage.isLoaded();
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");

        storePage.addToCart(product.getName());


        CartPage cartPage = storePage.viewCart();
        //cartPage.isLoaded();
        Assert.assertEquals(
                cartPage.getProductName(), product.getName()
        );

        CheckoutPage checkoutPage = cartPage.checkout()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Acer\\Documents\\ChromeDriver\\firefoxdriver.exe");
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        StorePage storePage = new HomePage(driver)
                .load()
                .navigateToStoreUsingMenu()
                .search(searchFor);
        //storePage.isLoaded();
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");

        storePage.addToCart(product.getName());


        CartPage cartPage = storePage.viewCart();
        //cartPage.isLoaded();
        Assert.assertEquals(
                cartPage.getProductName(), product.getName()
        );
        User user = JacksonUtils.deserializeJson("user.json", User.class);
        CheckoutPage checkoutPage = cartPage.checkout()
                .showLogin()
                .setLoginDetail(user)
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");

    }
}