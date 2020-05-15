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
public class BinarySearchTree<K, V> implements Tree<K, V> {

    private int size = 0;

    private Node<K, V> root;

    private Comparator<? super K> comparator;

    public BinarySearchTree() {
        comparator = null;
    }

    public BinarySearchTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key can not null");
        }
        if (root == null) {
            root = new Node<>(key, value);
            size++;
            return;
        }
        Node<K, V> tmp = root;
        Node<K, V> tmpParent = null;
        int cmp = 0;
        /*寻找父节点*/
        if (comparator != null) {
            while (tmp != null) {
                tmpParent = tmp;
                cmp = comparator.compare(key, tmp.key);
                if (cmp < 0) {
                    tmp = tmp.left;
                } else if (cmp > 0) {
                    tmp = tmp.right;
                } else {
                    tmp.setVal(value);
                    break;
                }
            }

        } else {
            /*使用key的compare*/
            while (tmp != null) {
                tmpParent = tmp;
                Comparable<? super K> k = (Comparable<? super K>) key;
                cmp = k.compareTo(tmp.key);
                if (cmp < 0) {
                    tmp = tmp.left;
                } else if (cmp > 0) {
                    tmp = tmp.right;
                } else {
                    tmp.setVal(value);
                    break;
                }
            }
        }
        if (cmp < 0) {
            Node<K, V> node = new Node<>(key, value);
            tmpParent.left = node;
            node.parent = tmpParent;
        } else if (cmp > 0) {
            Node<K, V> node = new Node<>(key, value);
            tmpParent.right = node;
            node.parent = tmpParent;
        }
        size++;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not null");
        }
        if (root == null) {
            return null;
        }
        Node<K, V> node = getNode(key);
        return node == null ? null : node.val;
    }

    private Node<K, V> getNode(K key) {
        Node<K, V> tmp = root;
        if (comparator != null) {
            while (tmp != null) {
                int cmp = comparator.compare(key, tmp.key);
                if (cmp < 0) {
                    tmp = tmp.left;
                } else if (cmp > 0) {
                    tmp = tmp.right;
                } else {
                    return tmp;
                }
            }

        } else {
            /*使用key的compare*/
            while (tmp != null) {
                Comparable<? super K> k = (Comparable<? super K>) key;
                int cmp = k.compareTo(tmp.key);
                if (cmp < 0) {
                    tmp = tmp.left;
                } else if (cmp > 0) {
                    tmp = tmp.right;
                } else {
                    return tmp;
                }
            }
        }
        return null;
    }

    /*最左边的元素*/
    @Override
    public Node<K, V> getMin() {
        Node<K, V> tmp = root;
        return getMinNode(tmp);
    }

    /*最右边的元素*/
    @Override
    public Node<K, V> getMax() {
        Node<K, V> tmp = root;
        return getMaxNode(tmp);
    }

    private Node<K, V> getMinNode(Node<K, V> tmp) {
        while (tmp.left != null) {
            tmp = tmp.left;
        }
        return tmp;
    }

    private Node<K, V> getMaxNode(Node<K, V> tmp) {
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        return tmp;
    }

    @Override
    public Node<K, V>[] getIncreaseKeys() {
        if (size == 0) {
            return null;
        }
        Node<K, V>[] nodes = new Node[size];
        Node<K, V> tmp = this.getMin();
        int i = 0;
        while (tmp != null) {
            nodes[i] = tmp;
            tmp = successor(tmp);
            i++;
        }
        return nodes;
    }

    private Node<K, V> successor(Node<K, V> tmp) {
        if (tmp.right != null) {
            return getMinNode(tmp.right);
        }
        Node<K, V> p = tmp.parent;
        while (p != null && tmp == p.right) {
            tmp = p;
            p = p.parent;
        }
        return p;

    }

    @Override
    public Node<K, V>[] getDecreaseKeys() {
        Node<K, V>[] nodes = new Node[size];
        Node<K, V> tmp = this.getMax();
        int i = 0;
        while (tmp != null) {
            nodes[i] = tmp;
            tmp = previous(tmp);
            i++;
        }
        return nodes;
    }

    private Node<K, V> previous(Node<K, V> tmp) {
        if (tmp.left != null) {
            return getMaxNode(tmp.left);
        }
        Node<K, V> p = tmp.parent;
        while (p != null && tmp == p.left) {
            tmp = p;
            p = p.parent;
        }
        return p;
    }

    @Override
    public V delete(K k) {
        Node<K, V> node = getNode(k);
        Node<K, V> parent = node.parent;
        V val = node.val;
        /*直接删除节点*/
        if (node.left == null && node.right == null) {
            if (parent == null) {
                /*直接删除根节点*/
                root = null;
            } else {
                node.parent = null;
                if (node == parent.left) {
                    parent.left = null;
                } else if (node == parent.right) {
                    parent.right = null;
                }
            }

        } else if (node.left == null) {
            /*左子节点为空，右子节点非空，将右子节点和父节点连接*/
            if (parent == null) {
                root = node.right;
            } else {
                node.parent = null;
                if (parent.left == node) {
                    parent.left = node.right;
                } else {
                    parent.right = node.right;
                }
            }
            node.right.parent = parent;
            node.right = null;

        } else if (node.right == null) {
            /*处理右节点为空，左节点有元素的情况，将左子节点和父节点连接*/
            if (parent == null) {
                root = node.left;
            } else {
                node.parent = null;
                if (parent.left == node) {
                    parent.left = node.left;
                } else {
                    parent.right = node.left;
                }
            }
            node.left.parent = parent;
            node.left = null;

        } else {
            /*存在双节点*/
            Node<K, V> successor = this.successor(node);
            if (successor.parent != node) {
                /*将后继节点的右子树（不存在左子树），子树连接到后继节点得父节点*/
                Node<K, V> sP = successor.parent;
                sP.left = successor.right;
                if (successor.right != null) {
                    successor.right.parent = sP;
                }

            } else {
                node.right = null;
            }
            /*删除后继节点，将后继节点键和值更新到到node（省去复杂的引用维护）*/
            successor.parent = null;
            successor.right = null;
            node.setVal(successor.getVal());
            node.setKey(successor.getKey());
        }
        size--;
        return val;
    }

    @Override
    public Node<K, V> getPrevious(K k) {
        Node<K, V> node = getNode(k);
        return node == null ? null : previous(node);

    }

    @Override
    public Node<K, V> getNext(K k) {
        Node<K, V> node = getNode(k);
        return node == null ? null : successor(node);
    }

    @Override
    public int size() {
        return size;
    }

    static class Node<K, V> {
        K key;
        V val;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
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
