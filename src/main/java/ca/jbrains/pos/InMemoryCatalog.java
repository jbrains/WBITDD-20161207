package ca.jbrains.pos;

import com.google.common.collect.ImmutableMap;

public class InMemoryCatalog implements Catalog {
    private final ImmutableMap<String, Price> pricesByBarcode;

    public InMemoryCatalog(ImmutableMap<String, Price> pricesByBarcode) {
        this.pricesByBarcode = pricesByBarcode;
    }

    public Price findPrice(String barcode) {
        return pricesByBarcode.get(barcode);
    }
}
