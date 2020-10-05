package com.liuqiqi.offer.sequence;

import java.util.Arrays;

/**
 * @author liuqiqi
 * @date 2020/10/5 10:00
 */
public class BlankDecode {
    public static void main(String[] args) {
        String str = " We are    happy. ";
        String decodeStr = decodeBlank(str);
        System.out.println(decodeStr);
    }

    public static String decodeBlank(String str) {
        if (str == null) {
            throw new IllegalArgumentException("char array can not be null");
        }
        char[] original = str.toCharArray();
        int blackSize = 0;
        for (char c : original) {
            if (c == ' ') {
                blackSize++;
            }
        }
        if (blackSize == 0) {
            return str;
        }
        int newArrayLength = original.length + blackSize * 2;
        int originalLength = original.length;
        if (originalLength < newArrayLength) {
            original = Arrays.copyOf(original, newArrayLength);
        }
        while (originalLength != newArrayLength) {
            if (original[originalLength- 1] == ' ') {
                original[newArrayLength - 1] = '0';
                original[newArrayLength - 2] = '2';
                original[newArrayLength - 3] = '%';
                newArrayLength = newArrayLength - 3;
            } else {
                original[newArrayLength - 1] = original[originalLength - 1];
                newArrayLength = newArrayLength - 1;
            }
            originalLength--;
        }
        return new String(original);
    }
}
