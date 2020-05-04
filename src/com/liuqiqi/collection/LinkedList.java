package com.liuqiqi.collection;

import java.util.Random;

/**
 * 链表
 *
 * @author liuqiqi
 * @date 2020/5/4 15:33
 */
public class LinkedList<T> implements List<T> {

    private int size;

    private Node<T> head;

    private Node<T> tail;


    @Override
    public void add(T item) {
        Node<T> node = new Node<>(tail, item, null);
        if (head == null) {
            head = node;
        }
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
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
                head = tNode;
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
        Node<T> next = head;
        while (index > 0) {
            next = next.next;
            index--;
        }
        return next;
    }

    @Override
    public boolean delete(T item) {
        /*寻找删除位置*/
        Node<T> delNode = head;
        while (delNode != null) {
            if (delNode.data.equals(item)) {
                deleteNode(delNode);
                size--;
                return true;
            }
            delNode = delNode.next;
        }
        return false;
    }

    private void deleteNode(Node<T> delNode) {
        if (delNode.prev == null) {
            head = delNode.next;
        } else {
            delNode.prev.next = delNode.next;
        }
        if (delNode.next == null) {
            tail = delNode.prev;
        } else {
            delNode.next.prev = delNode.prev;
        }
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
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> node = head;
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
        LinkedList<Integer> linkedList = new LinkedList<>();

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
            linkedList.add(data[i]);
        }

        System.out.println("栈的大小：" + linkedList.size());
        System.out.println("栈的元素：" + linkedList.toString());
        linkedList.delete(new Integer(648));
        System.out.println("删除648后" + linkedList.toString());
        linkedList.delete(2);
        System.out.println("删除索引2元素后" + linkedList.toString());
        linkedList.add(2, 666);
        System.out.println("新增索引2元素后" + linkedList.toString());
    }
}
