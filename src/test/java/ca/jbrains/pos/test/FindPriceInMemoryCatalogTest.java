package ca.jbrains.pos.test;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(ImmutableMap.of("12345", Price.cents(795)));
        Assert.assertEquals(Price.cents(795), catalog.findPrice("12345"));
    }

    @Test
    public void productNotFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(ImmutableMap.of());
        Assert.assertEquals(null, catalog.findPrice("::barcode not found::"));
    }

    public static class InMemoryCatalog {
        private final ImmutableMap<String, Price> pricesByBarcode;

        public InMemoryCatalog(ImmutableMap<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
