package ca.jbrains.pos.test;

import ca.jbrains.pos.Price;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class DisplayMessagesToWriterTest {
    @Test
    public void emptyBarcode() throws Exception {
        final StringWriter canvas = new StringWriter();

        new WriterDisplay(canvas).displayScannedEmptyBarcodeMessage();

        Assert.assertEquals(
                Arrays.asList("Scanning error: empty barcode"),
                lines(canvas.toString())
        );
    }

    @Test
    public void productNotFoundMessage() throws Exception {
        final StringWriter canvas = new StringWriter();

        new WriterDisplay(canvas).displayProductNotFoundMessage("::barcode not found::");

        Assert.assertEquals(
                Arrays.asList("Product not found for ::barcode not found::"),
                lines(canvas.toString())
        );
    }

    @Test
    public void price() throws Exception {
        final StringWriter canvas = new StringWriter();

        new WriterDisplay(canvas).displayPrice(Price.cents(198));

        Assert.assertEquals(
                Arrays.asList("EUR 1.98"),
                lines(canvas.toString())
        );
    }

    // REFACTOR Move to a reusable library OR replace with an existing library
    public static List<String> lines(String multilineText) {
        return Arrays.asList(multilineText.split(System.lineSeparator()));
    }

    public static class WriterDisplay {
        private final PrintWriter out;

        public WriterDisplay(StringWriter canvas) {
            this.out = new PrintWriter(canvas);
        }

        public void displayScannedEmptyBarcodeMessage() {
            out.println("Scanning error: empty barcode");
        }

        public void displayProductNotFoundMessage(String barcodeNotFound) {
            out.println(String.format("Product not found for %s", barcodeNotFound));
        }

        public void displayPrice(Price price) {
            out.println(String.format("EUR %.2f", price.euro()));
        }
    }
}
