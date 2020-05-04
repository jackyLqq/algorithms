package com.liuqiqi.collection;

/**
 * 队列
 *
 * @author liuqiqi
 * @date 2020/5/4 9:53
 */
public interface Queue<T> {

    void enQueue(T item);

    T deQueue();

    int size();
}
