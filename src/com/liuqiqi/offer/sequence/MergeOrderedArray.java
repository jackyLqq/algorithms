package com.liuqiqi.offer.sequence;

import java.util.Arrays;

/**
 * @author liuqiqi
 * @date 2020/10/5 10:32
 */
public class MergeOrderedArray {

    public static void main(String[] args) {
        int[] arrayFirst = new int[]{1, 1, 1, 1, 1};
        int[] arraySecond = new int[]{5, 6, 7, 8, 10};
        int arrayFirstSize = arrayFirst.length;
        arrayFirst = Arrays.copyOf(arrayFirst, arrayFirst.length + arraySecond.length);
        int[] merge = merge(arrayFirst, arraySecond, arrayFirstSize);
        System.out.println(Arrays.toString(merge));
    }

    public static int[] merge(int[] arrayFirst, int[] arraySecond, int arrayFirstSize) {
        int arraySecondLength = arraySecond.length;
        int insertFLag = arrayFirstSize + arraySecondLength - 1;
        while (arrayFirstSize != 0 && arraySecondLength != 0) {
            if (arrayFirst[arrayFirstSize - 1] < arraySecond[arraySecondLength - 1]) {
                arrayFirst[insertFLag] = arraySecond[arraySecondLength - 1];
                insertFLag--;
                arraySecondLength--;
            } else {
                arrayFirst[insertFLag] = arrayFirst[arrayFirstSize - 1];
                insertFLag--;
                arrayFirstSize--;
            }
        }
        return arrayFirst;
    }
}
