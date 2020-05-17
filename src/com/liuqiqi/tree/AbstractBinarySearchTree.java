package com.liuqiqi.tree;

import java.util.Comparator;

/**
 * AbstractBinarySearchTree抽象类
 *
 * @author liuqiqi
 * @date 2020/5/15 13:40
 */
public abstract class AbstractBinarySearchTree<K, V> implements Tree<K, V> {


    abstract void setRoot(Node<K, V> node);

    abstract Node<K, V> getRoot();

    abstract Comparator<? super K> getComparator();

    abstract void plusSize();

    abstract void reduceSize();

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not null");
        }
        if (getRoot() == null) {
            return null;
        }
        Node<K, V> node = getNode(key);
        return node == null ? null : node.val;
    }

    protected Node<K, V> getNode(K key) {
        Node<K, V> tmp = getRoot();
        Comparator<? super K> comparator = getComparator();
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

    @Override
    public Node<K, V> getMin() {
        Node<K, V> tmp = getRoot();
        if (this.getRoot() == null) {
            return null;
        }
        return getMinNode(tmp);
    }

    private Node<K, V> getMinNode(Node<K, V> tmp) {
        while (tmp.left != null) {
            tmp = tmp.left;
        }
        return tmp;
    }

    @Override
    public Node<K, V> getMax() {
        Node<K, V> tmp = getRoot();
        return getMaxNode(tmp);
    }

    private Node<K, V> getMaxNode(Node<K, V> tmp) {
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        return tmp;
    }

    @Override
    public Node<K, V>[] getIncreaseKeys() {
        if (size() == 0) {
            return null;
        }
        Node<K, V>[] nodes = new Node[size()];
        Node<K, V> tmp = this.getMin();
        int i = 0;
        while (tmp != null) {
            nodes[i] = tmp;
            tmp = successor(tmp);
            i++;
        }
        return nodes;
    }

    protected Node<K, V> successor(Node<K, V> tmp) {
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
        Node<K, V>[] nodes = new Node[size()];
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
    public V delete(K k) {
        Node<K, V> node = getNode(k);
        if (node == null) {
            return null;
        }
        V val = node.val;
        deleteNode(node);
        reduceSize();
        return val;
    }

    protected void deleteNode(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        /*直接删除节点*/
        if (node.left == null && node.right == null) {
            if (parent == null) {
                /*直接删除根节点*/
                setRoot(null);
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
                setRoot(node.right);
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
                setRoot(node.left);
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
            K key = successor.key;
            V val = successor.val;
            deleteNode(successor);
            node.setVal(val);
            node.setKey(key);
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key can not null");
        }
        Node<K, V> node = new Node<>(key, value);
        putNode(node);
    }

    protected void putNode(Node<K, V> node) {
        if (getRoot() == null) {
            setRoot(node);
            plusSize();
            return;
        }
        Node<K, V> tmp = getRoot();
        Node<K, V> tmpParent = null;
        int cmp = 0;
        /*寻找父节点*/
        Comparator<? super K> comparator = getComparator();
        if (comparator != null) {
            while (tmp != null) {
                tmpParent = tmp;
                cmp = comparator.compare(node.key, tmp.key);
                if (cmp < 0) {
                    tmp = tmp.left;
                } else if (cmp > 0) {
                    tmp = tmp.right;
                } else {
                    tmp.setVal(node.val);
                    break;
                }
            }

        } else {
            /*使用key的compare*/
            while (tmp != null) {
                tmpParent = tmp;
                Comparable<? super K> k = (Comparable<? super K>) node.key;
                cmp = k.compareTo(tmp.key);
                if (cmp < 0) {
                    tmp = tmp.left;
                } else if (cmp > 0) {
                    tmp = tmp.right;
                } else {
                    tmp.setVal(node.val);
                    break;
                }
            }
        }
        if (cmp < 0) {
            tmpParent.left = node;
            node.parent = tmpParent;
            plusSize();
        } else if (cmp > 0) {
            tmpParent.right = node;
            node.parent = tmpParent;
            plusSize();
        }
    }


}
