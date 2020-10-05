package com.liuqiqi.offer.array;

import java.util.Arrays;
import java.util.Random;

/**
 * @author liuqiqi
 * @date 2020/10/3 9:46
 */
public class RepeatedNumberOfBS {

    public static void main(String[] args) {
        Random random = new Random();
        int length = 10;
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(length);
        }
        System.out.println(Arrays.toString(array));
        Integer repeatedNumber = getOneRepeatedNumberOfArray(array);
        System.out.println("是否存在重复数字：" + repeatedNumber);
    }

    private static Integer getOneRepeatedNumberOfArray(int[] array) {
        if (array == null || array.length < 1) {
            throw new IllegalArgumentException("args error");
        }
        int start = 0;
        int end = array.length - 1;
        while (end >= start) {
            int mid = ((end - start) >> 1) + start;
            int count = countRange(array, start, mid);
            if (end == start) {
                if (count > 1) {
                    return start;
                } else {
                    break;
                }
            }
            if (count > (mid - start + 1)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    private static int countRange(int[] array, int start, int end) {
        int count = 0;
        for (int num : array) {
            if (num >= start && num <= end) {
                count++;
            }
        }
        return count;
    }
}
