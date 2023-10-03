package ntysdd.bigdecimal;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

final class Sqrt {
    private Sqrt() {
        throw new UnsupportedOperationException();
    }

    public static BigDecimal sqrt(
            @NonNull BigDecimal value,
            @NonNull RoundingMode roundingMode) {
        if (value.getClass() != BigDecimal.class) {
            throw new IllegalArgumentException();
        }
        if (roundingMode != RoundingMode.FLOOR && roundingMode != RoundingMode.DOWN) {
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
        BigInteger unscaledValue = value.unscaledValue();
        int len = unscaledValue.toString().length();
        return sqrt(unscaledValue, value.scale(), len);
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
        StringBuilder sb = new StringBuilder();
        sb.append(unscaledValue);
        int rs;
        rs = scale;
        if (rs % 2 != 0) {
            rs++;
            sb.append("0");
        }
        sb.reverse();

        int intRem = 0;
        if (sb.length() % 2 != 0) {
            intRem = extractDigitFromStringBuilder(sb);
        } else {
            intRem = extractDigitFromStringBuilder(sb);
            intRem *= 10;
            intRem += extractDigitFromStringBuilder(sb);
        }
        BigInteger rem = BigInteger.valueOf(intRem);

        BigInteger res = BigInteger.ZERO;
        BigInteger twenty = BigInteger.valueOf(20);

        int rd = 0;
        boolean sbEmpty = false;
        for (int i = 0; i < precision; i++) {
            for (int digit = 9; digit >= 0; digit--) {
                BigInteger newRem = rem.subtract(res.multiply(twenty)
                        .add(BigInteger.valueOf(digit)).multiply(BigInteger.valueOf(digit)));
                if (newRem.signum() < 0) {
                    continue;
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
                break;
            }
        }
        return new BigDecimal(res, rs / 2 + rd);
    }
}
