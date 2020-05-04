package com.liuqiqi.collection;

/**
 * @author liuqiqi
 * @date 2020/5/3 19:39
 */
public interface Stack<T> {

    void push(T item);

    T pop();

    int size();

}
