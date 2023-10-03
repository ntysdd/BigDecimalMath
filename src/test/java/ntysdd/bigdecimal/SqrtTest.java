package ntysdd.bigdecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

class SqrtTest {

    @Test
    void testSqrt_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Sqrt.sqrt(null, RoundingMode.FLOOR);
        });
    }

    @Test
    void testSqrt_Zero() {
        Assertions.assertSame(BigDecimal.ZERO, Sqrt.sqrt(BigDecimal.ZERO, RoundingMode.FLOOR));
        BigDecimal value = new BigDecimal("0");
        Assertions.assertSame(value, Sqrt.sqrt(value, RoundingMode.FLOOR));
        BigDecimal value2 = new BigDecimal("0.00");
        Assertions.assertSame(value2, Sqrt.sqrt(value2, RoundingMode.FLOOR));
    }

    @Test
    void testSqrt_Negative() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Sqrt.sqrt(new BigDecimal("-1"), RoundingMode.FLOOR);
        });
    }

    @Test
    void testSqrt_One() {
        Assertions.assertSame(BigDecimal.ONE, Sqrt.sqrt(BigDecimal.ONE, RoundingMode.FLOOR));
        BigDecimal value = new BigDecimal("1");
        Assertions.assertSame(value, Sqrt.sqrt(value, RoundingMode.FLOOR));
        BigDecimal value2 = new BigDecimal("1.00");
        Assertions.assertSame(value2, Sqrt.sqrt(value2, RoundingMode.FLOOR));
    }

    @Test
    void testSqrt_Two() {
        Assertions.assertEquals("1", Sqrt.sqrt(new BigDecimal("2"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.4", Sqrt.sqrt(new BigDecimal("2.0"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.41", Sqrt.sqrt(new BigDecimal("2.00"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.414", Sqrt.sqrt(new BigDecimal("2.000"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.4142", Sqrt.sqrt(new BigDecimal("2.0000"), RoundingMode.FLOOR).toString());
    }

    @Test
    void testSqrt_Ten() {
        Assertions.assertEquals("3.1", Sqrt.sqrt(new BigDecimal("10"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("3.16", Sqrt.sqrt(new BigDecimal("10.0"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("3.162", Sqrt.sqrt(new BigDecimal("10.00"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("3.1622", Sqrt.sqrt(new BigDecimal("10.000"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("3.16227", Sqrt.sqrt(new BigDecimal("10.0000"), RoundingMode.FLOOR).toString());
    }

    @Test
    void testSqrt_Hundred() {
        Assertions.assertEquals("10.0", Sqrt.sqrt(new BigDecimal("100"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("10.00", Sqrt.sqrt(new BigDecimal("100.0"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("10.000", Sqrt.sqrt(new BigDecimal("100.00"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("10.0000", Sqrt.sqrt(new BigDecimal("100.000"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("10.00000", Sqrt.sqrt(new BigDecimal("100.0000"), RoundingMode.FLOOR).toString());
    }

    @Test
    void testSqrt_Thousand() {
        Assertions.assertEquals("31.62", Sqrt.sqrt(new BigDecimal("1000"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("31.622", Sqrt.sqrt(new BigDecimal("1000.0"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("31.6227", Sqrt.sqrt(new BigDecimal("1000.00"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("31.62277", Sqrt.sqrt(new BigDecimal("1000.000"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("31.622776", Sqrt.sqrt(new BigDecimal("1000.0000"), RoundingMode.FLOOR).toString());
    }

    @Test
    void testSqrt_Pi() {
        Assertions.assertEquals("1", Sqrt.sqrt(new BigDecimal("3"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.7", Sqrt.sqrt(new BigDecimal("3.1"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.77", Sqrt.sqrt(new BigDecimal("3.14"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.772", Sqrt.sqrt(new BigDecimal("3.141"), RoundingMode.FLOOR).toString());
        Assertions.assertEquals("1.7724", Sqrt.sqrt(new BigDecimal("3.1415"), RoundingMode.FLOOR).toString());
    }

}

