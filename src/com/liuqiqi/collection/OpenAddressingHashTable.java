package com.liuqiqi.collection;

/**
 * 开放地址实现的哈希表
 *
 * @author liuqiqi
 * @date 2020/5/10 22:07
 */
public class OpenAddressingHashTable<K, V> implements HashTable<K, V> {

    /*默认容量*/
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /*默认负载因子*/
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /*当前元素大小*/
    private int size;

    /*存储数组*/
    private Node<K, V>[] table;

    /*元素扩展阈值*/
    int threshold;

    /*负载因子*/
    final float loadFactor;

    /*hash函数，高位与低位异或运算，高位参与运算，结果更加分散*/
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    /**
     * 构造函数
     */
    public OpenAddressingHashTable() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * this.loadFactor);
        this.table = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 构造函数
     *
     * @param initialCapacity 初始化大小
     */
    public OpenAddressingHashTable(int initialCapacity) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.threshold = (int) (initialCapacity * DEFAULT_LOAD_FACTOR);
        this.table = (Node<K, V>[]) new Node[initialCapacity];
    }

    /**
     * 构造函数
     *
     * @param initialCapacity 初始化大小
     * @param loadFactor      负载因子
     */
    public OpenAddressingHashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0 || loadFactor < 0) {
            throw new IllegalArgumentException("args error");
        }
        this.loadFactor = loadFactor;
        this.threshold = (int) (initialCapacity * loadFactor);
        this.table = (Node<K, V>[]) new Node[initialCapacity];
    }

    /*节点*/
    static class Node<K, V> {

        K key;

        V val;

        int hash;

        boolean deleteFlag;


        public Node(int hash, K key, V val) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.deleteFlag = false;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }

        void delete() {
            this.deleteFlag = true;
        }

        boolean isDelete() {
            return deleteFlag;
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        /*验证元素是否到达阈值*/
        if (size + 1 > threshold) {
            /*数组扩容*/
            table = reSize();
        }
        /*计算hash*/
        int hash = hash(key);
        int length = this.table.length;
        /*寻找插入位置*/
        int i = hash & length - 1;
        while (table[i] != null && !table[i].isDelete()) {
            i = nextIndex(i);
        }
        table[i] = new Node<>(hash, key, value);
        size++;
    }

    private int nextIndex(int i) {
        return i == (this.table.length - 1) ? 0 : i + 1;
    }


    private Node<K, V>[] reSize() {
        int oldLength = table.length;
        Node<K, V>[] oldTable = this.table;
        int newLength = oldLength << 1;
        /*newLength小于0发生溢出或者大于int的二分之一，则不进行扩容*/
        if (newLength < 0 || newLength > Integer.MAX_VALUE >> 1) {
            throw new IllegalStateException();
        }

        this.table = (Node<K, V>[]) new Node[newLength];
        /*数据迁移*/
        for (int i = 0; i < oldLength; i++) {
            Node<K, V> node = oldTable[i];
            if (node != null && !node.isDelete()) {
                /*重新计算插入位置*/
                int index = node.hash & (this.table.length - 1);
                /*寻找插入位置*/
                while (table[index] != null) {
                    index = nextIndex(i);
                }
                this.table[index] = node;
            }


        }
        this.threshold = (int) (newLength * loadFactor);
        return this.table;

    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        /*计算hash*/
        int hash = hash(key);
        int length = this.table.length;
        /*寻找插入位置*/
        int i = hash & length - 1;
        while (table[i] != null) {
            Node<K, V> node = table[i];
            if (node.hash == hash && !node.isDelete() && key.equals(node.key)) {
                return node.val;
            }
            i = nextIndex(i);
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contain(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        /*计算hash*/
        int hash = hash(key);
        int length = this.table.length;
        /*寻找位置*/
        int i = hash & length - 1;
        while (table[i] != null) {
            Node<K, V> node = table[i];
            if (node.hash == hash && !node.isDelete() && key.equals(node.key)) {
                return true;
            }
            i = nextIndex(i);

        }
        return false;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        /*计算hash*/
        int hash = hash(key);
        int length = this.table.length;
        /*寻找位置*/
        int i = hash & length - 1;
        while (table[i] != null) {
            Node<K, V> node = table[i];
            if (node.hash == hash && key.equals(node.key)) {
                node.delete();
                V val = node.val;
                node.key = null;
                node.val = null;
                size--;
                return val;
            }
            i = nextIndex(i);
        }
        return null;
    }

    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new OpenAddressingHashTable<>(16);
        for (int i = 0; i < 20; i++) {
            hashTable.put(String.valueOf(i), i);
        }
        System.out.println(hashTable.get(String.valueOf(11)));
        System.out.println(hashTable.contain(String.valueOf(11)));
        System.out.println(hashTable.remove(String.valueOf(11)));
        System.out.println(hashTable.contain(String.valueOf(11)));
        hashTable.put(String.valueOf(11), 11);
        System.out.println(hashTable.contain(String.valueOf(11)));
    }
}
