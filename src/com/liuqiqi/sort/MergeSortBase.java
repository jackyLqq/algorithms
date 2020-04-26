package com.liuqiqi.sort;

import com.liuqiqi.common.BaseOrder;

import java.lang.reflect.Array;

/**
 * @author liuqiqi
 * @date 2020/4/4 20:51
 */
public class MergeSortBase extends BaseOrder {
    private int mid;

    public MergeSortBase(int mid) {
        this.mid = mid;
    }

    @Override
    public void sort(Comparable[] data) {
        if(mid < 0 || mid > data.length -1) {
            throw new IllegalArgumentException();
        }
        Class<? extends Comparable[]> arrayClass = data.getClass();

        Class<?> baseClass = arrayClass.getComponentType();
        Comparable[] dataTemp = (Comparable[]) Array.newInstance(baseClass, data.length);
        int firstBegin = 0;
        int lastBegin = mid + 1;
        for (int i = 0; i < data.length; i++) {
            if (firstBegin > mid) {
                dataTemp[i] = data[lastBegin++];
            } else if (lastBegin > data.length - 1) {
                dataTemp[i] = data[firstBegin++];
            } else if (less(data[firstBegin], data[lastBegin])) {
                dataTemp[i] = data[firstBegin++];
            } else {
                dataTemp[i] = data[lastBegin++];
            }
        }
        System.out.println(this.isSort(dataTemp));
        this.show(dataTemp);
    }

    public static void main(String[] args) {
        Integer[] data = new Integer[]{1,33,67,89,112,22,33,37,66,88,1111};
        MergeSortBase ms = new MergeSortBase(4);
        ms.sort(data);
    }
}
