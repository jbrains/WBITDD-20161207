package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Catalog catalog = catalogWith("12345", Price.cents(795));
        Assert.assertEquals(Price.cents(795), catalog.findPrice("12345"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = catalogWithout("::barcode not found::");
        Assert.assertEquals(null, catalog.findPrice("::barcode not found::"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
