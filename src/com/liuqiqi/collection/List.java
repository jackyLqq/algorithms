package com.liuqiqi.collection;

/**
 * @author liuqiqi
 * @date 2020/5/4 15:33
 */
public interface List<T> {

    void add(T item);

    void add(int index, T item);

    boolean delete(T item);

    boolean delete(int index);

    T get(int index);

    T get(T item);

    int size();

}
