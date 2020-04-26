package com.liuqiqi.sort;

import com.liuqiqi.common.BaseOrder;

/**
 * @author liuqiqi
 * @date 2020/3/29 22:15
 */
public class ShellSort extends BaseOrder {

    @Override
    public void sort(Comparable[] a) {
        int internalNum = 1;
        while (internalNum < a.length / 3) {
            internalNum = internalNum * 3 + 1;
        }

        while (internalNum >= 1) {

            for (int i = internalNum; i < a.length; i++) {
                for (int j = i; j - internalNum >= 0 && less(a[j], a[j - internalNum]); j = j - internalNum) {
                    exchange(a, j, j - internalNum);
                }
            }
            internalNum = internalNum / 3;

        }

    }

    public static void main(String[] args) {
        ShellSort shellSort = new ShellSort();
        Integer[] data = shellSort.prepareData();
        shellSort.sort(data);
        System.out.println(shellSort.isSort(data));
        shellSort.show(data);
    }
}
