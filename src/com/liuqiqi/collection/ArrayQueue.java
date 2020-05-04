package com.liuqiqi.collection;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author liuqiqi
 * @date 2020/5/4 10:02
 */
public class ArrayQueue<T> implements Queue<T> {

    private int size;

    private int head;
    private int tail;

    private Object[] data;


    public ArrayQueue(int length) {
        this.data = new Object[length];
        this.head = this.tail = this.size = 0;
    }

    @Override
    public void enQueue(T item) {
        if (this.size == data.length) {
            throw new IllegalArgumentException();
        }
        data[tail] = item;
        tail = (tail + 1) % data.length;
        size++;
    }

    @Override
    public T deQueue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        T item = (T) data[head];
        /*help gc*/
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        return item;
    }

    @Override
    public int size() {
        return this.size;
    }


    @Override
    public String toString() {
        return "ArrayQueue{" +
                "size=" + size +
                ", head=" + head +
                ", tail=" + tail +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayQueue<>(10);
        Random random = new Random();
        Integer[] data = new Integer[10];
        for (int i = 0; i < data.length; i++) {
            int item = random.nextInt(1000);
            queue.enQueue(item);
        }

        System.out.println("栈的大小：" + queue.size());
        System.out.println("栈的元素：" + queue.toString());

        for (int i = 0; i < data.length; i++) {
            System.out.println(queue.deQueue());
        }
    }
}
