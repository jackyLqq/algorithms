package com.liuqiqi.collection;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 数组实现的栈
 *
 * @author liuqiqi
 * @date 2020/5/3 19:38
 */
public class ArrayStack<T> implements Stack<T> {

    /*存储数据的数组*/
    private final Object[] data;

    /*指向下一个元素要插入的位置*/
    private int top;

    public ArrayStack(int size) {
        this.data = new Object[size];
        this.top = 0;
    }

    @Override
    public void push(T item) {
        data[top++] = item;
    }

    @Override
    public T pop() {
        if (top == 0) {
            throw new NoSuchElementException();
        }
        Object item = data[top - 1];
        /*置空*/
        data[top - 1] = null;
        top--;
        return (T) item;
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public String toString() {
        return "ArrayStack{" +
                "data=" + Arrays.toString(data) +
                ", top=" + top +
                '}';
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>(10);
        Random random = new Random();
        Integer[] data = new Integer[10];
        for (int i = 0; i < data.length; i++) {
            int item = random.nextInt(1000);
            stack.push(item);
        }

        System.out.println("栈的大小：" + stack.size());
        System.out.println("栈的元素：" + stack.toString());

        for (int i = 0; i < data.length; i++) {
            System.out.println(stack.pop());
        }
    }
}
