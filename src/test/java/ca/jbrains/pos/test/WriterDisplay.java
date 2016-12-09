package ca.jbrains.pos.test;

import ca.jbrains.pos.Price;

import java.io.PrintWriter;
import java.io.StringWriter;

public class WriterDisplay {
    private final PrintWriter out;

    public WriterDisplay(StringWriter canvas) {
        // REFACTOR OK, OK... formatPrice() shouldn't be in this class
        this.out = canvas == null ? null : new PrintWriter(canvas);
    }

    public void displayScannedEmptyBarcodeMessage() {
        out.println("Scanning error: empty barcode");
    }

    public void displayProductNotFoundMessage(String barcodeNotFound) {
        out.println(String.format("Product not found for %s", barcodeNotFound));
    }

    public void displayPrice(Price price) {
        out.println(formatPrice(price));
    }

    public String formatPrice(Price price) {
        return String.format("EUR %.2f", price.euro());
    }
}
