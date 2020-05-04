package com.liuqiqi.collection;

import java.util.Random;

/**
 * 哨兵实现的链表
 *
 * @author liuqiqi
 * @date 2020/5/4 18:58
 */
public class SentinelLinkedList<T> implements List<T> {
    private int size;

    private Node<T> sentinel = new Node<>(null, null, null);


    @Override
    public void add(T item) {
        Node<T> node = new Node<>(sentinel.prev, item, null);
        if (sentinel.next == null) {
            sentinel.next = node;
        }
        if (sentinel.prev != null) {
            sentinel.prev.next = node;
        }
        sentinel.prev = node;
        size++;
    }

    @Override
    public void add(int index, T item) {
        Node<T> next = getNodeByIndex(index);
        if (next == null) {
            /*插入末尾*/
            add(item);
        } else {
            /*插入中间*/
            Node<T> prev = next.prev;
            Node<T> tNode = new Node<>(prev, item, next);
            if (prev == null) {
                sentinel.next = tNode;
            } else {
                prev.next = tNode;
            }
            next.prev = tNode;
        }
        size++;

    }

    private Node<T> getNodeByIndex(int index) {
        /*判断参数*/
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        /*寻找位置*/
        Node<T> next = sentinel.next;
        while (index > 0) {
            next = next.next;
            index--;
        }
        return next;
    }

    @Override
    public boolean delete(T item) {
        /*寻找节点*/
        Node<T> node = this.getNode(item);
        if (node == null) {
            return false;
        }
        deleteNode(node);
        size--;
        return true;
    }

    private void deleteNode(Node<T> delNode) {
        delNode.prev.next = delNode.next;
        delNode.next.prev = delNode.prev;
    }

    @Override
    public boolean delete(int index) {
        Node<T> node = this.getNodeByIndex(index);
        if (node == null) {
            return false;
        }
        deleteNode(node);
        return true;
    }

    @Override
    public T get(int index) {
        return this.getNodeByIndex(index).data;
    }

    @Override
    public T get(T item) {
        Node<T> node = getNode(item);
        return node.data;
    }

    private Node<T> getNode(T item) {
        Node<T> node = sentinel.next;
        while (node != null && !node.data.equals(item)) {
            node = node.next;
        }
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> node = sentinel.next;
        while (node != null) {
            sb.append(node.data).append(",");
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }

    private static class Node<T> {
        private final T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

    }

    public static void main(String[] args) {
        List<Integer> list = new SentinelLinkedList<>();

        Random random = new Random();
        Integer[] data = new Integer[]{933,
                357,
                589,
                648,
                265,
                610,
                550,
                710,
                433,
                520};
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
            list.add(data[i]);
        }

        System.out.println("栈的大小：" + list.size());
        System.out.println("栈的元素：" + list.toString());
        list.delete(new Integer(648));
        System.out.println("删除648后" + list.toString());
        list.delete(2);
        System.out.println("删除索引2元素后" + list.toString());
        list.add(2, 666);
        System.out.println("新增索引2元素后" + list.toString());
        System.out.println("获取索引为2元素" + list.get(2));
        System.out.println("获取为520元素" + list.get(new Integer(520)));
    }

}
