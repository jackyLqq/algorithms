package com.liuqiqi.sort;

import com.liuqiqi.common.BaseOrder;

import java.lang.reflect.Array;

/**
 * @author liuqiqi
 * @date 2020/4/23 18:17
 */
public class MergeSort extends BaseOrder {
    @Override
    public void sort(Comparable[] a) {

        doSort(a);
        System.out.println(this.isSort(a));
        this.show(a);
    }

    private void doSort(Comparable[] a) {
        /*递归结束条件*/
        if (a.length == 1) {
            return;
        }
        /*创建两个数组，中间分开，一次存储a的数据*/
        int begin = 0;
        int mid = a.length / 2;
        int end = a.length;
        Class<? extends Comparable[]> arrayClass = a.getClass();

        Class<?> baseClass = arrayClass.getComponentType();
        Comparable[] left = (Comparable[]) Array.newInstance(baseClass, mid);
        Comparable[] right = (Comparable[]) Array.newInstance(baseClass, end - mid);
        /*数组赋值*/
        for (int i = begin; i < mid; i++) {
            left[i] = a[i];
        }

        for (int i = mid; i < end; i++) {
            right[i - mid] = a[i];
        }

        doSort(left);

        doSort(right);

        doMerge(a, left, right);
    }

    private void doMerge(Comparable[] dataTemp, Comparable[] left, Comparable[] right) {

        int l = 0;
        int r = 0;
        for (int i = 0; i < dataTemp.length; i++) {
            if (r == right.length) {
                dataTemp[i] = left[l++];
                continue;
            }
            if (l == left.length) {
                dataTemp[i] = right[r++];
                continue;
            }
            if (less(left[l], right[r])) {
                dataTemp[i] = left[l++];

            } else {
                dataTemp[i] = right[r++];
            }


        }

    }

    public static void main(String[] args) {
        MergeSort mergeSort2 = new MergeSort();
        Integer[] data = mergeSort2.prepareData();
        mergeSort2.sort(data);
    }
}
