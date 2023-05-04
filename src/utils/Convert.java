/** @author Douglas */

package utils;

public class Convert {

    public static Long[] intToBinary(Long value, int size) {
        if (value > Math.pow(2, size) - 1) {
            return null;
        }
        Long bin[] = new Long[size];
        int i = 0;
        while (value > 0 && i < size) {
            Long num = value % 2;
            value = value / 2;
            bin[i] = num;
            i++;
        }
        for (int j = 0; j <= size / 2; j++) {
            Long temp = bin[j];
            bin[j] = bin[size - j - 1];
            bin[size - j - 1] = temp;
        }
        return bin;
    }

    public static String intToBinaryString(Long value, Long size) {
        if (value > Math.pow(2, size) - 1) {
            return null;
        }
        char bin[] = new char[size.intValue()];
        for (int i = 0; i < size; i++) {
            bin[i] = '0';
        }
        int i = 0;
        while (value > 0 && i < size) {
            Long num = value % 2;
            value = value / 2;
            bin[i] = (num + "").charAt(0);
            i++;
        }
        for (int j = 0; j <= size / 2; j++) {
            char temp = bin[j];
            bin[j] = bin[size.intValue() - j - 1];
            bin[size.intValue() - j - 1] = temp;
        }
        String nova = new String(bin);
        return nova;
    }
}
