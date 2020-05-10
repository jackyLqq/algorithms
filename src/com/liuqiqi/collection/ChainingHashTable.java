package com.liuqiqi.collection;

/**
 * 链地址法实现的哈希表
 *
 * @author liuqiqi
 * @date 2020/5/9 21:23
 */
public class ChainingHashTable<K, V> implements HashTable<K, V> {

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

    /*节点*/
    static class Node<K, V> {

        K key;

        V val;

        int hash;

        Node<K, V> next;

        public Node(int hash, K key, V val, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }
    }

    /**
     * 构造函数
     */
    public ChainingHashTable() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * this.loadFactor);
        this.table = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 构造函数
     *
     * @param initialCapacity 初始化大小
     */
    public ChainingHashTable(int initialCapacity) {
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
    public ChainingHashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity can not less than 0");
        }
        this.loadFactor = loadFactor;
        this.threshold = (int) (initialCapacity * loadFactor);
        this.table = (Node<K, V>[]) new Node[initialCapacity];
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

        if (table[i] == null) {
            table[i] = new Node<>(hash, key, value, null);
        } else {
            /*判断元素是否已经存在*/
            Node<K, V> node = table[i];
            while (node != null) {
                /*先判断hash*/
                if (node.hash == hash && node.getKey().equals(key)) {
                    /*覆盖val*/
                    node.val = value;
                    break;
                }
                node = node.next;
            }
            /*说明没有元素已经存在*/
            if (node == null) {
                /*直接插入开头*/
                table[i] = new Node<>(hash, key, value, table[i]);
            }
        }
        size++;

    }

    /*两倍扩容*/
    private Node<K, V>[] reSize() {
        int oldLength = table.length;
        Node<K, V>[] oldTable = this.table;
        int newLength = oldLength << 1;
        /*newLength小于0发生溢出或者大于int的二分之一，则不进行扩容*/
        if (newLength < 0 || newLength > Integer.MAX_VALUE >> 1) {
            return table;
        }

        this.table = (Node<K, V>[]) new Node[newLength];
        /*数据迁移*/
        for (int i = 0; i < oldLength; i++) {
            Node<K, V> node = oldTable[i];
            /*定义两个链表，一个存储位置不变的，一个存储位置变化的*/
            Node<K, V> lowHead = null, lowTail = null;
            Node<K, V> highHead = null, highTail = null;
            while (node != null) {
                /*二进制判断位置*/
                if ((node.hash & oldTable.length) == 0) {
                    /*位置不变*/
                    if (lowHead == null) {
                        lowHead = node;
                    } else {
                        /*插入*/
                        lowTail.next = node;
                    }
                    lowTail = node;
                } else {
                    /*位置=当前位置+oldTable.length*/
                    if (highHead == null) {
                        highHead = node;
                    } else {
                        /*插入*/
                        highTail.next = node;
                    }
                    highTail = node;
                }
                Node<K, V> preNode = node;
                node = node.next;
                preNode.next = null;
            }
            this.table[i] = lowHead;
            this.table[i + oldLength] = highHead;

        }
        this.threshold = (int) (newLength * loadFactor);
        return this.table;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node<K, V> node = getNode(key);
        return node == null ? null : node.getVal();

    }

    private Node<K, V> getNode(K key) {
        int length = this.table.length;
        int hash = hash(key);
        int i = hash & (length - 1);
        if (table[i] == null) {
            return null;
        } else {
            Node<K, V> node = table[i];
            while (node != null) {
                /*先判断hash*/
                if (node.hash == hash && node.getKey().equals(key)) {
                    /*覆盖val*/
                    break;
                }
                node = node.next;
            }
            return node;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contain(K key) {
        return this.getNode(key) != null;
    }

    @Override
    public V remove(K key) {
        int length = this.table.length;
        int hash = hash(key);
        int i = hash & (length - 1);
        if (this.table[i] != null) {
            Node<K, V> node = this.table[i];
            /*指向前一个节点*/
            Node<K, V> preNode = null;
            while (node != null) {
                /*先判断hash*/
                if (node.hash == hash && node.getKey().equals(key)) {
                    /*覆盖val*/
                    break;
                }
                preNode = node;
                node = node.next;
            }
            if (node != null) {
                /*说明第一个元素*/
                if (preNode == null) {
                    this.table[i] = node.next;
                } else {
                    preNode.next = node.next;
                }
                node.next = null;
                node.key = null;
                V val = node.val;
                node.val = null;
                size--;
                return val;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new ChainingHashTable<>(16);
        for (int i = 0; i < 12; i++) {
            hashTable.put(String.valueOf(i), i);
        }
        System.out.println(hashTable.get(String.valueOf(11)));
        System.out.println(hashTable.contain(String.valueOf(11)));
        System.out.println(hashTable.remove(String.valueOf(11)));
        System.out.println(hashTable.contain(String.valueOf(11)));
        System.out.println(hashTable.contain(String.valueOf(110)));

    }

}
