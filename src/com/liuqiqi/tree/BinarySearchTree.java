package com.liuqiqi.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * 二叉搜索树
 *
 * @author liuqiqi
 * @date 2020/5/12 18:19
 */
public class BinarySearchTree<K, V> extends AbstractBinarySearchTree<K, V> implements Tree<K, V> {

    private int size = 0;

    private Node<K, V> root;

    private Comparator<? super K> comparator;


    public BinarySearchTree() {

    }

    public BinarySearchTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }


    public Node<K, V> getRoot() {
        return root;
    }

    public Comparator<? super K> getComparator() {
        return comparator;
    }

    @Override
    void setRoot(Node<K, V> node) {
        this.root = node;
    }

    @Override
    public V delete(K k) {
        V val = super.delete(k);
        size--;
        return val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        super.put(key, value);
        size++;
    }

    public static void main(String[] args) {
        Tree<Integer, Integer> tree = new BinarySearchTree<>();
        Random random = new Random();
        Integer[] data = new Integer[]{402, 949, 565, 140, 891, 792,
                18, 100, 265, 988, 122, 592, 73, 978, 462, 902, 506,
                8, 432, 78,};
        for (int i = 0; i < data.length; i++) {
            tree.put(data[i], data[i]);
        }
        System.out.println(Arrays.toString(tree.getIncreaseKeys()));
        System.out.println(Arrays.toString(tree.getDecreaseKeys()));
        System.out.println(tree.get(73));
        System.out.println(tree.getMax());
        System.out.println(tree.getMin());
        System.out.println(tree.size());
        System.out.println(tree.getNext(140));
        System.out.println(tree.getPrevious(140));

        Node<Integer, Integer>[] decreaseKeys = tree.getDecreaseKeys();
        for (int i = decreaseKeys.length - 1; i > 0; i--) {
            tree.delete(decreaseKeys[i].key);
            System.out.println(Arrays.toString(tree.getIncreaseKeys()));
        }

    }
}
