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
            return value;
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

        int rs;
        if (scale % 2 != 0) {
            rs = Math.floorDiv(scale, 2) + 1;
            sb.append("0");
        } else {
            rs = scale / 2;
        }
        sb.reverse();

        int intRem;
        if (sb.length() % 2 != 0) {
            intRem = extractDigitFromStringBuilder(sb);
        } else {
            int d1 = extractDigitFromStringBuilder(sb);
            int d2 = extractDigitFromStringBuilder(sb);
            intRem = d1 * 10 + d2;
        }
        BigInteger rem = BigInteger.valueOf(intRem);
        BigInteger res = BigInteger.ZERO;
        BigInteger twenty = BigInteger.valueOf(20);

        long rd = 0;
        boolean sbEmpty = false;
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
            if (sbEmpty) {
                rd++;
            }
            res = res.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
            rem = newRem;
            if (sb.length() <= 0) {
                sbEmpty = true;
            }
            rem = rem.multiply(BigInteger.valueOf(10));
            rem = rem.add(BigInteger.valueOf(extractDigitFromStringBuilder(sb)));
            rem = rem.multiply(BigInteger.valueOf(10));
            rem = rem.add(BigInteger.valueOf(extractDigitFromStringBuilder(sb)));
        }
        return new BigDecimal(res, Math.addExact(rs, Math.toIntExact(rd)));
    }
}
