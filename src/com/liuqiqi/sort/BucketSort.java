package com.liuqiqi.sort;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 桶排序
 *
 * @author liuqiqi
 * @date 2020/5/3 15:06
 */
public class BucketSort {

    public Double[] sort(Double[] a) {
        Object[] temp = new Object[a.length];
        /*每个元素赋值链表*/
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new LinkedList<>();
        }
        /*给链表赋值*/
        for (int j = 0; j < a.length; j++) {
            int index = (int) (a[j] * 10);
            LinkedList<Double> list = (LinkedList<Double>) temp[index];
            list.offer(a[j]);
        }
        /*给链表元素排序，使用插入排序*/
        for (int i = 0; i < temp.length; i++) {
            LinkedList<Double> list = (LinkedList<Double>) temp[i];
            if (list.size() <= 1) {
                continue;
            }
            Double[] doubles = list.toArray(new Double[0]);
            insertSort(doubles);
            temp[i] = new LinkedList<>(Arrays.asList(doubles));
        }
        /*获取排序后的数据*/
        Double[] result = new Double[a.length];
        int index = 0;
        for (Object obj : temp) {
            LinkedList<Double> linkedList = (LinkedList<Double>) obj;
            Iterator<Double> iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                result[index++] = iterator.next();
            }
        }
        return result;
    }


    private void insertSort(Double[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && a[j].compareTo(a[j - 1]) < 0; j--) {
                Double t = a[j];
                a[i] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("######0.00");

        Double[] data = new Double[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = Double.valueOf(df.format(Math.random()));
        }
        BucketSort bucketSort = new BucketSort();
        System.out.println(Arrays.toString(data));
        Double[] sort = bucketSort.sort(data);
        System.out.println(Arrays.toString(sort));
    }
}
