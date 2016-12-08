package ca.jbrains.pos.test;

public class WriterDisplay implements Display {
    @Override
    public void displayPrice(Price price) {
        System.out.println(String.format("EUR %.2f", price.euro()));
    }

    @Override
    public void displayProductNotFoundMessage(String barcodeNotFound) {
        System.out.println(String.format("Product not found for %s", barcodeNotFound));
    }

    @Override
    public void displayScannedEmptyBarcodeMessage() {
        System.out.println("Scanning error: empty barcode");
    }
}
