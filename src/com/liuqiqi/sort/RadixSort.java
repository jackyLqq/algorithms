package com.liuqiqi.sort;

import java.util.Arrays;

/**
 * 基数排序
 *
 * @author liuqiqi
 * @date 2020/5/3 10:26
 */
public class RadixSort extends BaseReturnOrder {


    public static void main(String[] args) {
        Integer[] data = new Integer[10];
        for (int j = 0; j < 10; j++) {
            data[j] = (int) (Math.random() * 900) + 100;
        }
        RadixSort radixSort = new RadixSort();
        Integer[] sort = radixSort.sort(data);
        radixSort.isSort(sort);
        radixSort.show(sort);

    }

    @Override
    public Integer[] sort(Integer[] a) {
        /*计算元素的位数*/
        if (a.length == 0) {
            return new Integer[0];
        }

        Integer num = a[0];
        int digit = ("" + num).length();
        int nowNum = 1;

        for (int i = 1; i <= digit; i++) {
            a = countingSortByDigit(a, nowNum);
            nowNum = nowNum * 10;
        }

        return a;
    }

    private Integer[] countingSortByDigit(Integer[] a, int digitNum) {
        Integer[] indexArray = new Integer[10];
        Integer[] result = new Integer[a.length];
        Arrays.fill(indexArray, 0);
        /*indexArray存储的次数赋值*/
        for (int f = 0; f < a.length; f++) {
            int dividerNum = a[f] / digitNum;
            indexArray[dividerNum % 10]++;
        }
        /*计算indexArray中元素的位置索引*/
        for (int i = 0; i < indexArray.length - 1; i++) {
            indexArray[i + 1] = indexArray[i] + indexArray[i + 1];
        }
        /*赋值,注意下标*/
        for (int j = 0; j < a.length; j++) {
            int dividerNum = a[j] / digitNum;
            /*寻找插入点*/
            int preIndex = 0;
            if (dividerNum % 10 != 0) {
                preIndex = indexArray[dividerNum % 10 - 1];
            }
            int nowIndex = indexArray[dividerNum % 10];
            int insertIndex = preIndex + 1;
            for (int i = insertIndex; i <= nowIndex; i++) {
                if (result[i - 1] == null) {
                    insertIndex = i - 1;
                    break;
                }
            }
            result[insertIndex] = a[j];
        }
        return result;

    }


}
