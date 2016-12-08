package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class SellOneItemControllerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void productFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);
        final SellOneItemController controller = new SellOneItemController(catalog, display);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("12345"));
            will(returnValue(Price.cents(795)));

            oneOf(display).displayPrice(with(Price.cents(795)));
        }});

        controller.onBarcode("12345");
    }

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);
        final SellOneItemController controller = new SellOneItemController(catalog, display);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("::barcode not found::"));
            will(returnValue(null));

            oneOf(display).displayProductNotFoundMessage(with("::barcode not found::"));
        }});

        controller.onBarcode("::barcode not found::");
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);
        final SellOneItemController controller = new SellOneItemController(null, display);

        context.checking(new Expectations() {{
            oneOf(display).displayScannedEmptyBarcodeMessage();
        }});

        controller.onBarcode("");
        
    }

}
