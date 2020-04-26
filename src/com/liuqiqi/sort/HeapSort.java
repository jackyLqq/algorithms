package com.liuqiqi.sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * 堆排序(基于最小堆)
 *
 * @author liuqiqi
 * @date 2020/4/26 12:17
 */
public class HeapSort {

    private int size;

    private Integer[] a;

    public HeapSort(Integer[] a) {
        this.a = a;
        this.size = a.length;
    }

    public Integer[] sort() {
        /**
         * 1、初始化堆
         * 2、删除根节点，依次输出排序后的元素
         */
        initHeap(a);

        return getOrderArray(a);
    }

    private Integer[] getOrderArray(Integer[] a) {
        Integer[] orderArray = (Integer[]) Array.newInstance(Integer.class, a.length);
        for (int i = 0; i < orderArray.length; i++) {
            orderArray[i] = popRoot(a);
        }
        return orderArray;
    }

    /*弹出根元素*/
    private Integer popRoot(Integer[] a) {
        Integer root = a[0];
        /*调整堆*/
        a[0] = a[size - 1];
        a[size - 1] = null;
        size--;
        siftDown(a, 1);
        return root;
    }

    private void initHeap(Integer[] a) {
        /*下标-1*/
        for (int i = a.length / 2; i > 0; i--) {
            siftDown(a, i);
        }
    }

    /*i做下沉操作,i的子数组已经有序，所有的数组取值要-1，左子节点i<<1,右子树i<<1+1*/
    private void siftDown(Integer[] a, int j) {
        Integer now = a[j - 1];
        while (j * 2 <= size) {
            Integer left = a[(j << 1) - 1];
            int selIndex;
            if ((j << 1) + 1 > size) {
                /*右子树为空*/
                selIndex = j << 1;
            } else {
                /*右子树不为为空*/
                Integer right = a[(j << 1)];
                selIndex = left.compareTo(right) > 0 ? (j << 1) + 1 : j << 1;
            }
            if (now.compareTo(a[selIndex - 1]) > 0) {
                a[j - 1] = a[selIndex - 1];
                j = selIndex;
            } else {
                /*符合最小堆性质，退出*/
                break;
            }
        }
        /*最后j就是要插入的下表*/
        a[j - 1] = now;

    }

    public static void main(String[] args) {
        Random random = new Random();
        Integer[] data = new Integer[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(1000);
        }

        HeapSort heapSort = new HeapSort(data);
        Integer[] sort = heapSort.sort();
        System.out.println(Arrays.toString(sort));
    }


}
