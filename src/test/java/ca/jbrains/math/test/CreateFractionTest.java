package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class CreateFractionTest {
    @Test
    public void zeroDenominator() throws Exception {
        try {
            new Fraction(7, 0);
            Assert.fail("How did you create a fraction with a 0 denominator?!");
        } catch (IllegalArgumentException expected) {
        }
    }
}
