package com.liuqiqi.sort;

import java.lang.reflect.Array;

/**
 * @author liuqiqi
 * @date 2020/4/23 18:17
 */
public class MergeSort extends BaseOrder {
    @Override
    public void sort(Integer[] a) {
        doSort(a);
    }

    private void doSort(Integer[] a) {
        /*递归结束条件*/
        if (a.length == 1) {
            return;
        }
        /*创建两个数组，中间分开，一次存储a的数据*/
        int begin = 0;
        int mid = a.length / 2;
        int end = a.length;
        Class<? extends Integer[]> arrayClass = a.getClass();

        Class<?> baseClass = arrayClass.getComponentType();
        Integer[] left = (Integer[]) Array.newInstance(baseClass, mid);
        Integer[] right = (Integer[]) Array.newInstance(baseClass, end - mid);
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

    private void doMerge(Integer[] dataTemp, Integer[] left, Integer[] right) {

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
        System.out.println(mergeSort2.isSort(data));
        mergeSort2.show(data);
    }
}
