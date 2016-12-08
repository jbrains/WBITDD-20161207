package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.InMemoryCatalog;
import ca.jbrains.pos.Price;
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

}
