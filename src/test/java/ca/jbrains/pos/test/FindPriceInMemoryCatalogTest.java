package ca.jbrains.pos.test;

import com.google.common.collect.ImmutableMap;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {

    @Override
    protected Catalog catalogWith(String barcode, Price matchingPrice) {
        return new InMemoryCatalog(ImmutableMap.of(
                "not " + barcode, Price.cents(0),
                barcode, matchingPrice,
                "still not " + barcode, Price.cents(18372)
        ));
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(ImmutableMap.of(
                "not " + barcodeToAvoid, Price.cents(1),
                "still not " + barcodeToAvoid, Price.cents(2),
                "definitely not " + barcodeToAvoid, Price.cents(3)
        ));
    }

    public static class InMemoryCatalog implements Catalog {
        private final ImmutableMap<String, Price> pricesByBarcode;

        public InMemoryCatalog(ImmutableMap<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
