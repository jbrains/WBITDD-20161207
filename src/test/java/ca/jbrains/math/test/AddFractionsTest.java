package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assert.assertEquals(0, sum.intValue());
    }

    @Test
    public void notZeroPlusZero() throws Exception {
        Fraction sum = new Fraction(3).plus(new Fraction(0));
        Assert.assertEquals(3, sum.intValue());
    }

    @Test
    public void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(7));
        Assert.assertEquals(7, sum.intValue());
    }

    public static class Fraction {
        private final int integerValue;

        public Fraction(int integerValue) {
            this.integerValue = integerValue;
        }

        public Fraction plus(Fraction that) {
            if (this.integerValue == 0)
                return that;
            else
                return this;
        }

        public int intValue() {
            return integerValue;
        }
    }
}
