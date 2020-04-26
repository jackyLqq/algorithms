package com.liuqiqi.sort;

import com.liuqiqi.common.BaseOrder;

/**
 * 插入排序
 *
 * @author liuqiqi
 * @date 2020/3/29 21:19
 */
public class Insertion extends BaseOrder {

    @Override
    public void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        Insertion insertion = new Insertion();
        Integer[] data = insertion.prepareData();
        insertion.sort(data);
        System.out.println(insertion.isSort(data));
        insertion.show(data);
    }
}
