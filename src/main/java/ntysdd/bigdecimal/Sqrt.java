package ntysdd.bigdecimal;

import lombok.NonNull;
import lombok.var;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

final class Sqrt {
    private Sqrt() {
        throw new UnsupportedOperationException();
    }

    public static BigDecimal sqrt(
            @NonNull BigDecimal value,
            @NonNull MathContext mathContext) {
        if (value.getClass() != BigDecimal.class) {
            throw new IllegalArgumentException();
        }
        var roundingMode = mathContext.getRoundingMode();
        int precision = mathContext.getPrecision();
        if (roundingMode != RoundingMode.FLOOR && roundingMode != RoundingMode.DOWN) {
            throw new UnsupportedOperationException();
        }
        if (precision == 0) {
            throw new UnsupportedOperationException();
        }
        int valueSignum = value.signum();
        if (valueSignum <= 0) {
            if (valueSignum == 0) {
                return value;
            }
            throw new ArithmeticException();
        }
        if (BigDecimal.ONE.compareTo(value) == 0) {
            if (value.precision() == precision) {
                return value;
            }
            return BigDecimal.ONE.setScale(precision - 1, RoundingMode.DOWN);
        }
        return sqrt(value.unscaledValue(), value.scale(), precision);
    }

    private static int extractDigitFromStringBuilder(StringBuilder sb) {
        int sbLength = sb.length();
        if (sbLength == 0) {
            return 0;
        }
        char ch = sb.charAt(sbLength - 1);
        sb.setLength(sbLength - 1);
        return ch - '0';
    }

    private static BigDecimal sqrt(
            BigInteger unscaledValue,
            int scale,
            int precision) {
        var sb = new StringBuilder(unscaledValue.toString());
        sb.reverse();
        long intLen = (long) sb.length() - (long) scale;
        long rs = -((intLen + 1) >> 1);
        int intRem;
        if (intLen % 2 != 0) {
            intRem = extractDigitFromStringBuilder(sb);
        } else {
            int d1 = extractDigitFromStringBuilder(sb);
            int d2 = extractDigitFromStringBuilder(sb);
            intRem = d1 * 10 + d2;
        }
        BigInteger rem = BigInteger.valueOf(intRem);
        BigInteger res = BigInteger.ZERO;
        BigInteger twenty = BigInteger.valueOf(20);

        for (int i = 0; i < precision; i++) {
            BigInteger newRem;
            int digit;
            int digitLowerBound = 0;
            int digitUpperBound = 10;
            while (true) {
                digit = (digitLowerBound + digitUpperBound + 1) / 2;
                newRem = rem.subtract(
                        res.multiply(twenty).add(
                                BigInteger.valueOf(digit)).multiply(BigInteger.valueOf(digit)));
                int newRemSignum = newRem.signum();
                if (newRemSignum < 0) {
                    digitUpperBound = digit;
                } else if (newRemSignum > 0) {
                    digitLowerBound = digit;
                } else {
                    break;
                }
                if (digitLowerBound == digitUpperBound - 1) {
                    if (digit != digitLowerBound) {
                        digit = digitLowerBound;
                        newRem = rem.subtract(
                                res.multiply(twenty).add(
                                        BigInteger.valueOf(digit)).multiply(BigInteger.valueOf(digit)));
                    }
                    break;
                }
            }

            res = res.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
            rem = newRem;

            rem = rem.multiply(BigInteger.valueOf(10));
            rem = rem.add(BigInteger.valueOf(extractDigitFromStringBuilder(sb)));
            rem = rem.multiply(BigInteger.valueOf(10));
            rem = rem.add(BigInteger.valueOf(extractDigitFromStringBuilder(sb)));
        }
        return new BigDecimal(res, Math.toIntExact(rs + precision));
    }

    public static BigInteger sqrtExact(@NonNull BigInteger value) {
        if (value.getClass() != BigInteger.class) {
            throw new IllegalArgumentException();
        }
        if (value.signum() <= 0) {
            if (value.signum() == 0) {
                return value;
            }
            throw new ArithmeticException();
        }
        if (BigInteger.ONE.equals(value)) {
            return value;
        }
        int valueBitLength = value.bitLength();
        if (valueBitLength <= 30) {
            int v = value.intValueExact();
            int s = (int) StrictMath.sqrt((double) v);
            if ((long) s * (long) s == v) {
                return BigInteger.valueOf(s);
            }
            throw new ArithmeticException();
        }
        int guessBit = 28 + valueBitLength % 2;
        int initBits = (int) Math.sqrt(value.shiftRight(
                valueBitLength - guessBit).intValueExact());
        BigInteger x = BigInteger.valueOf(Math.max(initBits, 1))
                .shiftLeft((valueBitLength - guessBit) / 2);

        BigInteger lower = BigInteger.ONE;
        BigInteger upper = value;
        while (true) {
            BigInteger x2 = x.multiply(x);
            int compare = x2.compareTo(value);
            if (compare > 0) {
                upper = upper.min(x);
                x = x.add(value.divide(x)).shiftRight(1);
            } else if (compare < 0) {
                lower = lower.max(x);
                BigInteger[] divResult = value.divideAndRemainder(x);
                x = x.add(divResult[0]);
                if (!divResult[1].equals(BigInteger.ZERO)) {
                    x = x.add(BigInteger.ONE);
                }
                boolean isOdd = x.testBit(0);
                x = x.shiftRight(1);
                if (isOdd) {
                    x = x.add(BigInteger.ONE);
                }
            } else {
                return x;
            }
            if (upper.subtract(lower).compareTo(BigInteger.valueOf(10)) < 0) {
                for (BigInteger t = lower.add(BigInteger.ONE); t.compareTo(upper) < 0; t = t.add(BigInteger.ONE)) {
                    if (t.multiply(t).equals(value)) {
                        return t;
                    }
                }
                throw new ArithmeticException();
            }
        }
    }
}
