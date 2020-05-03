package com.liuqiqi.sort;

import java.util.Arrays;

/**
 * 计数排序
 *
 * @author liuqiqi
 * @date 2020/4/28 16:01
 */
public class CountingSort extends BaseReturnOrder {

    private Integer maxNum = null;

    public CountingSort(Integer maxNum) {
        this.maxNum = maxNum;
    }

    @Override
    public Integer[] sort(Integer[] a) {
        Integer[] indexArray = new Integer[maxNum];
        Integer[] result = new Integer[a.length];
        Arrays.fill(indexArray, 0);
        /*indexArray存储的次数赋值*/
        for (int f = 0; f < a.length; f++) {
            indexArray[a[f]]++;
        }
        /*计算indexArray中元素的位置索引*/
        for (int i = 0; i < indexArray.length - 1; i++) {
            indexArray[i + 1] = indexArray[i] + indexArray[i + 1];
        }
        /*赋值,注意下标*/
        for (int j = 0; j < a.length; j++) {
            result[indexArray[a[j]] - 1] = a[j];
            indexArray[a[j]]--;
        }
        return result;
    }

    public static void main(String[] args) {
        CountingSort countingSort = new CountingSort(1000);
        Integer[] data = countingSort.prepareData();
        Integer[] sort = countingSort.sort(data);
        System.out.println(countingSort.isSort(sort));
        countingSort.show(sort);
    }
}
