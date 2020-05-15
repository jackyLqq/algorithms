package com.liuqiqi.tree;

/**
 * @author liuqiqi
 * @date 2020/5/12 18:20
 */
public interface Tree<K, V> {

    void put(K key, V value);

    V get(K key);

    BinarySearchTree.Node<K, V> getMin();

    BinarySearchTree.Node<K, V> getMax();

    BinarySearchTree.Node<K, V>[] getIncreaseKeys();

    BinarySearchTree.Node<K, V>[] getDecreaseKeys();

    V delete(K k);

    BinarySearchTree.Node<K, V> getPrevious(K k);

    BinarySearchTree.Node<K, V> getNext(K k);

    int size();
}
