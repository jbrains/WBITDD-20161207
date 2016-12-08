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

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }

    public static class ConsumeTextCommands {
        private final BarcodeScannedListener barcodeScannedListener;

        public ConsumeTextCommands(BarcodeScannedListener barcodeScannedListener) {
            this.barcodeScannedListener = barcodeScannedListener;
        }

        public void consume(Reader commandSource) throws IOException {
            new BufferedReader(commandSource).lines()
                    .forEachOrdered(barcodeScannedListener::onBarcode);
        }
    }
}
