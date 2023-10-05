package ntysdd.bigdecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

class SqrtTest {

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testSqrt_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Sqrt.sqrt(null, new MathContext(10, RoundingMode.FLOOR));
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            Sqrt.sqrt(BigDecimal.ONE, null);
        });
    }

    @Test
    void testSqrt_Zero() {
        Assertions.assertSame(BigDecimal.ZERO, Sqrt.sqrt(BigDecimal.ZERO,
                new MathContext(10, RoundingMode.FLOOR)));
        BigDecimal value = new BigDecimal("0");
        Assertions.assertSame(value, Sqrt.sqrt(value, new MathContext(10,
                RoundingMode.FLOOR)));
        BigDecimal value2 = new BigDecimal("0.00");
        Assertions.assertSame(value2, Sqrt.sqrt(value2, new MathContext(10,
                RoundingMode.FLOOR)));
    }

    @Test
    void testSqrt_Negative() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Sqrt.sqrt(new BigDecimal("-1"), new MathContext(10,
                    RoundingMode.FLOOR));
        });
    }

    @Test
    void testSqrt_One() {
        Assertions.assertSame(BigDecimal.ONE, Sqrt.sqrt(BigDecimal.ONE,
                new MathContext(10, RoundingMode.FLOOR)));
        BigDecimal value = new BigDecimal("1");
        Assertions.assertSame(value, Sqrt.sqrt(value, new MathContext(10,
                RoundingMode.FLOOR)));
        BigDecimal value2 = new BigDecimal("1.00");
        Assertions.assertSame(value2, Sqrt.sqrt(value2, new MathContext(10,
                RoundingMode.FLOOR)));
    }

    @Test
    void testSqrt_Percent() {
        Assertions.assertEquals("0.1", Sqrt.sqrt(new BigDecimal("0.01"),
                new MathContext(1, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.10", Sqrt.sqrt(new BigDecimal("0.01"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.10", Sqrt.sqrt(new BigDecimal("0.0100"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.100", Sqrt.sqrt(new BigDecimal("0.0100"),
                new MathContext(3, RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_Two() {
        Assertions.assertEquals("1", Sqrt.sqrt(new BigDecimal("2"),
                new MathContext(1, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.4", Sqrt.sqrt(new BigDecimal("2.0"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.41", Sqrt.sqrt(new BigDecimal("2.00"),
                new MathContext(3, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.414", Sqrt.sqrt(new BigDecimal("2.000"),
                new MathContext(4, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.4142", Sqrt.sqrt(new BigDecimal("2.0000"),
                new MathContext(5, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.4", Sqrt.sqrt(new BigDecimal("2.0000000"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_Ten() {
        Assertions.assertEquals("3.1", Sqrt.sqrt(new BigDecimal("10"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("3.16", Sqrt.sqrt(new BigDecimal("10.0"),
                new MathContext(3, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("3.162", Sqrt.sqrt(new BigDecimal("10.00"),
                new MathContext(4, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("3.1622", Sqrt.sqrt(new BigDecimal("10.000"),
                new MathContext(5, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("3.16227",
                Sqrt.sqrt(new BigDecimal("10.0000"), new MathContext(6,
                        RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_Hundred() {
        Assertions.assertEquals("10.0", Sqrt.sqrt(new BigDecimal("100"),
                new MathContext(3, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("10.00", Sqrt.sqrt(new BigDecimal("100.0"),
                new MathContext(4, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("10.000", Sqrt.sqrt(new BigDecimal("100.00"),
                new MathContext(5, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("10.0000",
                Sqrt.sqrt(new BigDecimal("100.000"), new MathContext(6,
                        RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("10.00000",
                Sqrt.sqrt(new BigDecimal("100.0000"),
                new MathContext(7, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("10.00000",
                Sqrt.sqrt(new BigDecimal("1E2"),
                        new MathContext(7, RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_Thousand() {
        Assertions.assertEquals("31.62", Sqrt.sqrt(new BigDecimal("1000"),
                new MathContext(4, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("31.622", Sqrt.sqrt(new BigDecimal("1000.0"),
                new MathContext(5, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("31.6227",
                Sqrt.sqrt(new BigDecimal("1000.00"), new MathContext(6,
                        RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("31.62277",
                Sqrt.sqrt(new BigDecimal("1000.000"), new MathContext(7,
                        RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("31.622776",
                Sqrt.sqrt(new BigDecimal("1000.0000"), new MathContext(8
                        , RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("31.622776",
                Sqrt.sqrt(new BigDecimal("1E3"), new MathContext(8
                        , RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("31.622776",
                Sqrt.sqrt(new BigDecimal("1.0000000000000E3"), new MathContext(8
                        , RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_Pi() {
        Assertions.assertEquals("1", Sqrt.sqrt(new BigDecimal("3"),
                new MathContext(1, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.7", Sqrt.sqrt(new BigDecimal("3.1"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.77", Sqrt.sqrt(new BigDecimal("3.14"),
                new MathContext(3, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.772", Sqrt.sqrt(new BigDecimal("3.141"),
                new MathContext(4, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("1.7724", Sqrt.sqrt(new BigDecimal("3.1415"),
                new MathContext(5, RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_PointOne() {
        Assertions.assertEquals("0.31", Sqrt.sqrt(new BigDecimal("0.10"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrt_PointZeroZeroOne() {
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("0.001"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("0.0010"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("0.00100"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("0.001000"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("1E-3"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("1.0E-3"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("1.00E-3"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
        Assertions.assertEquals("0.031", Sqrt.sqrt(new BigDecimal("1.000E-3"),
                new MathContext(2, RoundingMode.FLOOR)).toString());
    }

    @Test
    void testSqrtExact_Zero() {
        BigInteger zero = new BigInteger("0");
        Assertions.assertSame(zero, Sqrt.sqrtExact(zero));
    }

    @Test
    void testSqrtExact_Negative() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Sqrt.sqrtExact(BigInteger.valueOf(-1));
        });
    }

    @Test
    void testSqrtExact_One() {
        BigInteger one = new BigInteger("1");
        Assertions.assertSame(one, Sqrt.sqrtExact(one));
    }

    @Test
    void testSqrtExact_Two() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Sqrt.sqrtExact(BigInteger.valueOf(2));
        });
    }

    @Test
    void testSqrtExact_Four() {
        Assertions.assertEquals(BigInteger.valueOf(2),
                Sqrt.sqrtExact(BigInteger.valueOf(4)));
    }

    @Test
    void testSqrtExact_BigNumber() {
        Assertions.assertEquals(new BigInteger("50712712067055368402"),
                Sqrt.sqrtExact(new BigInteger(
                        "2571779165196063176145474879527940033604")));
    }

    @Test
    void testSqrtExact_BigNumberNotExact() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Sqrt.sqrtExact(new BigInteger("99999999999999999999999999"));
        });
    }
}
