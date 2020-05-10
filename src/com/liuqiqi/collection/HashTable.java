package com.liuqiqi.collection;

/**
 * 哈希表接口
 *
 * @author liuqiqi
 * @date 2020/5/9 21:24
 */
public interface HashTable<K, V> {

    void put(K key, V value);

    V get(K key);

    int size();

    boolean contain(K key);

    V remove(K key);
}
