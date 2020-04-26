package com.liuqiqi.sort;

import com.liuqiqi.common.BaseOrder;

/**
 * 选择排序
 *
 * @author liuqiqi
 * @date 2020/3/22 22:11
 */
public class Selection extends BaseOrder {


    @Override
    public void sort(Comparable[] data) {

        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (less(data[j], data[i])) {
                    exchange(data, i, j);
                }
            }
        }

    }


    public static void main(String[] args) {
        Selection selection = new Selection();
        Integer[] data = selection.prepareData();
        selection.sort(data);
        System.out.println(selection.isSort(data));
        selection.show(data);
    }

}
