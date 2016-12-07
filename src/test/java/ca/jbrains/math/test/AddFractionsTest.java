package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assert.assertEquals(new Fraction(0), sum);
    }

    @Test
    public void notZeroPlusZero() throws Exception {
        Fraction sum = new Fraction(3).plus(new Fraction(0));
        Assert.assertEquals(new Fraction(3), sum);
    }

    @Test
    public void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(7));
        Assert.assertEquals(new Fraction(7), sum);
    }

    @Test
    public void notZeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(3).plus(new Fraction(9));
        Assert.assertEquals(new Fraction(12), sum);
    }

    @Test
    public void sameDenominators() throws Exception {
        Fraction sum = new Fraction(1, 5).plus(new Fraction(2, 5));
        Assert.assertEquals(new Fraction(3, 5), sum);
    }

    @Test
    public void differentDenominatorsWithoutNeedingToReduceTheResult() throws Exception {
        Fraction sum = new Fraction(3, 7).plus(new Fraction(2, 3));
        Assert.assertEquals(new Fraction(23, 21), sum);
    }

    public static class Fraction {
        private int numerator;
        private int denominator;

        public Fraction(int integerValue) {
            this(integerValue, 1);
        }

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction plus(Fraction that) {
            return new Fraction(
                    this.numerator * that.denominator + that.numerator * this.denominator,
                    this.denominator * that.denominator);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Fraction) {
                Fraction that = (Fraction) other;
                return this.numerator * that.denominator == this.denominator * that.numerator;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return String.format("%d/%d", numerator, denominator);
        }
    }
}
