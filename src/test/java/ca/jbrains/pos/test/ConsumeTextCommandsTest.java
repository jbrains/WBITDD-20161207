package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ConsumeTextCommandsTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void oneBarcode() throws Exception {
        final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("::barcode::"));
        }});

        new ConsumeTextCommands(barcodeScannedListener)
                .consume(new StringReader("::barcode::\n"));
    }

    @Test
    public void noBarcodes() throws Exception {
        final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

        context.checking(new Expectations() {{
            never(barcodeScannedListener);
        }});

        new ConsumeTextCommands(barcodeScannedListener)
                .consume(new StringReader(""));
    }

    @Test
    public void severalBarcodes() throws Exception {
        final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 1::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 2::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 3::"));
        }});

        new ConsumeTextCommands(barcodeScannedListener)
                .consume(new StringReader(
                        "::barcode 1::\n" +
                        "::barcode 2::\n" +
                        "::barcode 3::\n"
                ));
    }

    @Test
    public void emptyCommands() throws Exception {
        final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 1::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 2::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 3::"));
        }});

        new ConsumeTextCommands(barcodeScannedListener)
                .consume(new StringReader(
                        "\n" +
                        "\n" +
                        "::barcode 1::\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "::barcode 2::\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "::barcode 3::\n" +
                        "\n" +
                        "\n" +
                        "\n"
                ));
    }
    @Test
    public void insignificantWhitespace() throws Exception {
        final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 1::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 2::"));
            oneOf(barcodeScannedListener).onBarcode(with("::barcode 3::"));
        }});

        new ConsumeTextCommands(barcodeScannedListener)
                .consume(new StringReader(
                        "\t\t\n" +
                        "\n" +
                        "   \t ::barcode 1:: \t  \n" +
                        "\n" +
                        "  \t\t  \n" +
                        "\n" +
                        "  \t  ::barcode 2::\n" +
                        "      \n" +
                        "\n" +
                        "\n" +
                        "::barcode 3::         \t\n" +
                        "\n" +
                        "     \t     \n" +
                        "\n"
                ));
    }

    public static class ConsumeTextCommands {
        private final BarcodeScannedListener barcodeScannedListener;

        public ConsumeTextCommands(BarcodeScannedListener barcodeScannedListener) {
            this.barcodeScannedListener = barcodeScannedListener;
        }

        public void consume(Reader commandSource) throws IOException {
            new BufferedReader(commandSource).lines()
                    .map(String::trim)
                    .filter((line) -> !line.isEmpty())
                    .forEachOrdered(barcodeScannedListener::onBarcode);
        }
    }
}
