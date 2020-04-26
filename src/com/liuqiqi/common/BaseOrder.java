package com.liuqiqi.common;

import java.util.Arrays;
import java.util.Random;

/**
 * @author liuqiqi
 * @date 2020/3/22 22:15
 */
public abstract class BaseOrder {

    public abstract void sort(Comparable[] a);

    public boolean less(Comparable a,
                        Comparable b) {
        return a.compareTo(b) < 0;
    }

    public void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public void show(Comparable[] a) {
        System.out.println(Arrays.toString(a));
    }

    public boolean isSort(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (less(a[i + 1], a[i])) {
                return false;
            }
        }
        return true;
    }

    public Integer[] prepareData() {
        Random random = new Random();
        Integer[] data = new Integer[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(1000);
        }
        return data;
    }

}
