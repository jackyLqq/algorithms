package com.liuqiqi.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author liuqiqi
 * @date 2020/4/26 13:50
 */
public abstract class Order {
    public boolean less(Integer a,
                        Integer b) {
        return a.compareTo(b) < 0;
    }

    public void exchange(Integer[] a, int i, int j) {
        Integer t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public void show(Integer[] a) {
        System.out.println(Arrays.toString(a));
    }

    public boolean isSort(Integer[] a) {
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
