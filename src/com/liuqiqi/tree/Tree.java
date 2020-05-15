package com.liuqiqi.tree;


/**
 * 树接口
 *
 * @author liuqiqi
 * @date 2020/5/12 18:20
 */
public interface Tree<K, V> {


    void put(K key, V value);

    V get(K key);

    Node<K, V> getMin();

    Node<K, V> getMax();

    Node<K, V>[] getIncreaseKeys();

    Node<K, V>[] getDecreaseKeys();

    V delete(K k);

    Node<K, V> getPrevious(K k);

    Node<K, V> getNext(K k);

    int size();

}
