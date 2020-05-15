package com.liuqiqi.tree;

import java.util.Comparator;

/**
 * AVL树，平衡二叉搜索树
 *
 * @author liuqiqi
 * @date 2020/5/15 10:03
 */
public class BalancedBinarySearchTree<K, V> extends AbstractBinarySearchTree<K, V> implements Tree<K, V> {

    private int size = 0;

    private Node<K, V> root;

    private Comparator<? super K> comparator;


    public BalancedBinarySearchTree() {
        comparator = null;
    }

    public BalancedBinarySearchTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Node<K, V> getRoot() {
        return this.root;
    }

    @Override
    public Comparator<? super K> getComparator() {
        return this.comparator;
    }

    @Override
    void setRoot(Node<K, V> node) {
        this.root = node;
    }

    private void updateRotateFactor(Node<K, V> node) {
        node.factor = 0;
        node.parent.factor = 0;
        node.parent.parent.factor = 0;
    }

    private void rotate(Node<K, V> node) {
        if (node.parent.parent.factor == 2 && node.parent.left == node) {
            rRotate(node);
        } else if (node.parent.parent.factor == 2 && node.parent.right == node) {
            lrRotate(node);
        } else if (node.parent.parent.factor == -2 && node.parent.left == node) {
            rlRotate(node);
        } else if (node.parent.parent.factor == -2 && node.parent.right == node) {
            lRotate(node);
        }
    }

    /*左旋*/
    private void lRotate(Node<K, V> node) {
        /*父节点变为爷爷节点*/
        Node<K, V> pParent = node.parent.parent;
        if (pParent.parent == null) {
            root = node.parent;
        } else {
            if (pParent == pParent.parent.left) {
                pParent.parent.left = node.parent;
            } else if (pParent == pParent.parent.right) {
                pParent.parent.right = node.parent;
            }
        }
        node.parent.parent = pParent.parent;
        /*爷爷节点变为父节点的右子节点*/
        pParent.parent = node.parent;
        node.parent.left = pParent;
        pParent.right = null;
    }

    /*先右旋后左旋*/
    private void rlRotate(Node<K, V> node) {
        Node<K, V> pParent = node.parent.parent;
        Node<K, V> parent = node.parent;
        pParent.right = node;
        node.parent = pParent;

        parent.parent = node;
        node.right = parent;
        node.left = null;

        lRotate(parent);

    }

    /*先左旋后右旋*/
    private void lrRotate(Node<K, V> node) {
        Node<K, V> pParent = node.parent.parent;
        Node<K, V> parent = node.parent;
        pParent.left = node;
        node.parent = pParent;

        parent.parent = node;
        node.left = parent;
        node.right = null;
        rRotate(parent);
    }

    /*右旋*/
    private void rRotate(Node<K, V> node) {
        /*父节点变为爷爷节点*/
        Node<K, V> pParent = node.parent.parent;
        if (pParent.parent == null) {
            root = node.parent;
        } else {
            if (pParent == pParent.parent.left) {
                pParent.parent.left = node.parent;
            } else if (pParent == pParent.parent.right) {
                pParent.parent.right = node.parent;
            }
        }
        node.parent.parent = pParent.parent;
        /*爷爷节点变为父节点的右子节点*/
        pParent.parent = node.parent;
        node.parent.right = pParent;
        pParent.left = null;
    }

    private boolean checkOrUpdateFactor(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        while (parent != null) {
            if (node == parent.left) {
                parent.factor = parent.factor + 1;
                if (parent.factor == 2) {
                    return true;
                }
            }
            if (node == parent.left) {
                parent.factor = parent.factor + 1;
                if (parent.factor == -2) {
                    return true;
                }
            }
            node = parent;
            parent = parent.parent;
        }
        return false;
    }

}
