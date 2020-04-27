package com.liuqiqi.sort;

/**
 * 快速排序
 *
 * @author liuqiqi
 * @date 2020/4/27 20:54
 */
public class QuickSort extends BaseOrder {

    @Override
    public void sort(Integer[] a) {
        doSort(a, 0, a.length - 1);
    }

    private void doSort(Integer[] a, int begin, int last) {
        /*结束条件*/
        if (begin < last) {
            /*以last的元素为标准进行分隔，小于last的在last左边，大于在last右边*/
            int partition = partition(a, begin, last);
            /*递归处理左子数组和又子数组，不包含last*/
            doSort(a, begin, partition - 1);
            doSort(a, partition + 1, last);
        }

    }

    /*分隔处理*/
    private int partition(Integer[] a, int begin, int last) {
        Integer x = a[last];
        int i = begin - 1;
        int j = begin;
        while (j < last) {
            if (a[j] <= x) {
                exchange(a, i + 1, j);
                i++;
            }
            j++;
        }
        exchange(a, i + 1, last);
        return i + 1;
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        /*准备数据*/
        Integer[] data = quickSort.prepareData();
        /*排序*/
        quickSort.sort(data);
        /*排序结果*/
        System.out.println(quickSort.isSort(data));
        /*输出元素*/
        quickSort.show(data);

    }
}
