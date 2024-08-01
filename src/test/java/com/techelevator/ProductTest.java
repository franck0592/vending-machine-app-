package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductTest {
    Product product = new Product("A1", "Potato Crisps", 3.05);

    @Test
    public void is_sold_available() {
        Assert.assertFalse(product.isSoldOut());
    }

    @Test
    public void does_exist() {
        Assert.assertTrue(product.doesExist("A1"));
    }

    @Test
    public void purchase_product_returns_true() {
        for(int i = 5; i >= 1; i--) {
            Assert.assertTrue(product.purchaseProduct());
        }
        Assert.assertTrue(product.isSoldOut());
    }
}