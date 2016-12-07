package ca.jbrains.pos.test;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(ImmutableMap.of("12345", "EUR 7.95")));

        sale.onBarcode("12345");

        Assert.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(ImmutableMap.of("23456", "EUR 12.50")));

        sale.onBarcode("23456");

        Assert.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(ImmutableMap.of()));

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found for 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(ImmutableMap.of()));

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private final Catalog catalog;
        private Display display;

        public Sale(Display display, Catalog catalog) {
            this.display = display;
            this.catalog = catalog;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayScannedEmptyBarcodeMessage();
                return;
            }

            final String priceAsText = catalog.findPrice(barcode);
            if (priceAsText == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(priceAsText);
        }

    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void displayScannedEmptyBarcodeMessage() {
            this.text = "Scanning error: empty barcode";
        }

        public void displayProductNotFoundMessage(String barcode) {
            this.text = String.format("Product not found for %s", barcode);
        }

        public void displayPrice(String priceAsText) {
            this.text = priceAsText;
        }
    }
}
