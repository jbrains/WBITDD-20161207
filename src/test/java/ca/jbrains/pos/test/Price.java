package ca.jbrains.pos.test;

import lombok.Value;

@Value(staticConstructor = "cents")
public class Price {
    private final int centsValue;

    public double euro() {
        return getCentsValue() / 100.0d;
    }
}
