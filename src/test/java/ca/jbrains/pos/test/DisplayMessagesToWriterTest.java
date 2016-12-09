package ca.jbrains.pos.test;

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

    // REFACTOR Move to a reusable library OR replace with an existing library
    public static List<String> lines(String multilineText) {
        return Arrays.asList(multilineText.split(System.lineSeparator()));
    }

    public static class WriterDisplay {
        private final StringWriter canvas;

        public WriterDisplay(StringWriter canvas) {
            this.canvas = canvas;
        }

        public void displayScannedEmptyBarcodeMessage() {
            new PrintWriter(canvas).println("Scanning error: empty barcode");
        }
    }
}
