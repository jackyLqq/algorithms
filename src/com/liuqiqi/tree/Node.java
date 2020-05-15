package com.liuqiqi.tree;

/**
 * 节点类
 *
 * @author liuqiqi
 * @date 2020/5/15 13:44
 */
public class Node<K, V> {

    K key;
    V val;
    /*平衡因子，-1，0，1，-2或者2则需要进行旋转*/
    int factor;
    Node<K, V> left;
    Node<K, V> right;
    Node<K, V> parent;

    public Node(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public Node(K key, V val, int factor) {
        this.key = key;
        this.val = val;
        this.factor = factor;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public void setLeft(Node<K, V> left) {
        this.left = left;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> right) {
        this.right = right;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                '}';
    }

}
