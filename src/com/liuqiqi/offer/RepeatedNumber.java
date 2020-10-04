package com.liuqiqi.offer;

import java.util.Arrays;
import java.util.Random;

/**
 * 重复数字
 *
 * @author liuqiqi
 * @date 2020/10/2 9:15
 */
public class RepeatedNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int length = 10;
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(length);
        }
        System.out.println(Arrays.toString(array));
        boolean repeatedFlag = hasRepeatedNumberOfArray(array);
        System.out.println("是否存在重复数字：" + repeatedFlag);

    }

    public static boolean hasRepeatedNumberOfArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array can not be null");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= array.length) {
                throw new IllegalArgumentException("value can not larger than length of  array");
            }
            if (array[i] == i) {
                continue;
            }
            if (array[array[i]] == array[i]) {
                return true;
            } else {
                int tmp = array[array[i]];
                array[array[i]] = array[i];
                array[i] = tmp;
            }
        }
        return false;
    }
}
