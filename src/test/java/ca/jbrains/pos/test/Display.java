package ca.jbrains.pos.test;

public interface Display {
    void displayPrice(Price price);

    void displayProductNotFoundMessage(String barcodeNotFound);

    void displayScannedEmptyBarcodeMessage();
}
