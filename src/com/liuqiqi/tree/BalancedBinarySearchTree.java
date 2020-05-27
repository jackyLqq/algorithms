package com.liuqiqi.tree;

import java.util.Comparator;
import java.util.Random;

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
    void setRoot(Node<K, V> node) {
        this.root = node;
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
    void plusSize() {
        size++;
    }

    @Override
    void reduceSize() {
        size--;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key can not null");
        }
        Node<K, V> node = new Node<>(key, value, 0);
        V oldVal = super.putNode(node);

        /*检查或者更新factor,rotateFlag*/
        Node<K, V> ancestorNode = checkOrUpdateFactorOfPut(node);
        /*rotateFlag为true,存在factor为2或者-2的进行旋转*/
        if (ancestorNode != null) {
            /*先更新factor再旋转*/
            rotateOfPut(node, ancestorNode);
        }
        return oldVal;
    }

    @Override
    public V delete(K k) {
        Node<K, V> node = getNode(k);
        if (node == null) {
            return null;
        }
        V val = node.val;

        Node<K, V> rotateNode = checkOrUpdateFactorOfDelete(node);
        deleteNode(node);
        if (rotateNode != null) {
            deleteRotate(rotateNode);
        }
        reduceSize();
        return val;
    }


    private Node<K, V> checkOrUpdateFactorOfDelete(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        /*直接删除节点*/
        if (node.left == null || node.right == null) {
            while (parent != null) {
                int parentFactor = parent.mark;
                if (node == parent.left) {
                    parent.mark = parent.mark - 1;
                    if (parent.mark == -2) {
                        return parent;
                    }
                }
                if (node == parent.right) {
                    parent.mark = parent.mark + 1;
                    if (parent.mark == 2) {
                        return parent;
                    }
                }
                if (parentFactor == 0) {
                    break;
                }
                node = parent;
                parent = parent.parent;
            }

        } else {
            /*寻找后继，相当于删除后继节点，后继是肯定没有左子树，因此是前面的情况*/
            Node<K, V> successor = successor(node);
            parent = checkOrUpdateFactorOfDelete(successor);
            return parent;

        }
        return null;
    }

    private void updateFactorOfDelete(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        while (parent != null) {
            int parentFactor = parent.mark;
            if (node == parent.left) {
                parent.mark = parent.mark - 1;
                if (parent.mark == -2) {
                    /*删除更新时遇到不平衡的节点继续进行旋转操作*/
                    deleteRotate(parent);
                    /*结束节点factor的更新，旋转后抵消*/
                    return;
                }
            }
            if (node == parent.right) {
                parent.mark = parent.mark + 1;
                if (parent.mark == 2) {
                    /*删除更新时遇到不平衡的节点继续进行旋转操作*/
                    deleteRotate(parent);
                    /*结束节点factor的更新，旋转后抵消*/
                    return;
                }
            }
            /*说明parent的左右子树原来高度相差0，其中一个高度变化了，其高度仍然不变，因此parent节点的祖先节点不需要更新factor*/
            if (parentFactor == 0) {
                break;
            }
            node = parent;
            parent = parent.parent;
        }
    }

    /*删除后的旋转*/
    private void deleteRotate(Node<K, V> node) {
        /*说明左子树较高*/
        if (node.mark == 2) {
            int leftFactor = node.left.mark;
            if (leftFactor == 0 || leftFactor == 1) {
                rRotate(node);
                node.mark = height(node.left) - height(node.right);
                node.parent.mark = height(node.parent.left) - height(node.parent.right);
            } else {
                /*左子节点factor为-1的时候需要先左旋再右旋*/
                lRotate(node.left);
                rRotate(node);
                node.mark = height(node.left) - height(node.right);
                node.parent.mark = height(node.parent.left) - height(node.parent.right);
                node.parent.left.mark = height(node.parent.left.left) - height(node.parent.left.right);
            }
            if (leftFactor != 0) {
                /*旋转后，树高度肯定-1，因此要更新node.left祖先路径上的factor*/
                updateFactorOfDelete(node.parent);
            }
        } else if (node.mark == -2) {
            /*说明右子树较高*/
            int rightFactor = node.right.mark;
            if (rightFactor == 0 || rightFactor == -1) {
                lRotate(node);
                node.mark = height(node.left) - height(node.right);
                node.parent.mark = height(node.parent.left) - height(node.parent.right);
            } else {
                /*左子节点factor为1的时候需要先左旋再右旋*/
                rRotate(node.right);
                lRotate(node);
                node.mark = height(node.left) - height(node.right);
                node.parent.mark = height(node.parent.left) - height(node.parent.right);
                node.parent.right.mark = height(node.parent.right.left) - height(node.parent.right.right);
            }
            if (rightFactor != 0) {
                /*旋转后，树高度肯定-1，因此要更新node.right祖先路径上的factor*/
                updateFactorOfDelete(node.parent);
            }
        }

    }


    private void rotateOfPut(Node<K, V> node, Node<K, V> ancestor) {
        /*左旋和右旋只修改移动的两个节点，ancestor和其父节点，而左右旋和右左旋则改变三个，也是移动的三个节点*/
        if (ancestor.mark == -2 && judge(node, ancestor.right, ancestor.right.right)) {
            /*ancestor左旋*/
            lRotate(ancestor);
        } else if (ancestor.mark == 2 && judge(node, ancestor.left, ancestor.left.left)) {
            /*ancestor右旋*/
            rRotate(ancestor);
        } else if (ancestor.mark == -2 && judge(node, ancestor.right, ancestor.right.left)) {
            /*ancestor.right右旋*/
            rRotate(ancestor.right);
            /*ancestor.左旋*/
            lRotate(ancestor);
            ancestor.parent.right.mark = height(ancestor.parent.right.left) - height(ancestor.parent.right.right);
        } else if (ancestor.mark == 2 && judge(node, ancestor.left, ancestor.left.right)) {
            /*ancestor.left左旋*/
            lRotate(ancestor.left);
            /*ancestor.右旋*/
            rRotate(ancestor);
            ancestor.parent.left.mark = height(ancestor.parent.left.left) - height(ancestor.parent.left.right);
        }
        ancestor.mark = height(ancestor.left) - height(ancestor.right);
        ancestor.parent.mark = height(ancestor.parent.left) - height(ancestor.parent.right);
    }

    int height(Node<K, V> node) {
        if (node != null) {
            return Math.max(height(node.left), height(node.right)) + 1;
        }
        return 0;
    }

    boolean judge(Node<K, V> node, Node<K, V> ancestorRight, Node<K, V> matchNode) {
        while (node != ancestorRight) {
            if (node == matchNode) {
                return true;
            }
            node = node.parent;
        }
        return false;
    }


    /*左旋*/
    private void lRotate(Node<K, V> node) {
        Node<K, V> right = node.right;
        Node<K, V> parent = node.parent;
        Node<K, V> rightLeft = node.right.left;
        /*父节点和右子节点连接*/
        if (parent == null) {
            root = right;
        } else {
            if (node == parent.left) {
                parent.left = right;
            } else if (node == parent.right) {
                parent.right = right;
            }
        }
        right.parent = parent;
        /*变为右子节点的左节点*/
        node.parent = right;
        right.left = node;
        /*右子节点的左子节点变为node的右节点*/
        if (rightLeft != null) {
            rightLeft.parent = node;
        }
        node.right = rightLeft;

    }

    /*右旋*/
    private void rRotate(Node<K, V> node) {
        Node<K, V> left = node.left;
        Node<K, V> parent = node.parent;
        Node<K, V> leftRight = node.left.right;
        /*父节点和右子节点连接*/
        if (parent == null) {
            root = left;
        } else {
            if (node == parent.left) {
                parent.left = left;
            } else if (node == parent.right) {
                parent.right = left;
            }
        }
        left.parent = parent;
        /*变为左子节点的右节点*/
        node.parent = left;
        left.right = node;
        /*右子节点的左子节点变为node的右节点*/
        if (leftRight != null) {
            leftRight.parent = node;
        }
        node.left = leftRight;
    }

    private Node<K, V> checkOrUpdateFactorOfPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        if (parent == null) {
            return null;
        }
        while (parent != null) {
            if (node == parent.left) {
                parent.mark = parent.mark + 1;
                if (parent.mark == 2) {
                    return parent;
                }
            }
            if (node == parent.right) {
                parent.mark = parent.mark - 1;
                if (parent.mark == -2) {
                    return parent;
                }
            }
            /*说明parent左右子树相差高度相差1，新增的节点刚好让左右子树相等，因此parent的祖先不再改变factor*/
            if (parent.mark == 0) {
                break;
            }
            node = parent;
            parent = parent.parent;
        }


        return null;
    }

    /*检查树是否平衡*/
    public void checkFactor() {
        Node<K, V> node = this.getMin();
        while (node != null) {
            if (node.mark > 1 || node.mark < -1 || node.mark != (height(node.left) - height(node.right))) {
                throw new IllegalArgumentException();
            }
            node = this.successor(node);
        }

    }

    /*检查大小是否正确*/
    public void checkSize() {
        Node<K, V> node = this.getMin();
        int i = 0;
        while (node != null) {
            node = this.successor(node);
            i++;
        }
        if (i != size()) {
            System.out.println("元素丢失了 麻痹的" + i);
        }

    }

    public static void main(String[] args) {
        BalancedBinarySearchTree<Integer, Integer> tree = new BalancedBinarySearchTree<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            int data = random.nextInt
                    (100000);
            tree.put(data, data);
            System.out.println(i);
        }
        for (int i = 0; i < 100000; i++) {
            int i1 = random.nextInt(100000);
            tree.delete(i1);
            tree.checkFactor();

        }


/*        Integer[] data = new Integer[]{14, 4, 20, 5, 8, 1, 35, 19, 16, 23, 13, 9, 47, 6, 37, 16, 33, 26, 43, 42, 32, 3, 23, 18, 19, 24, 6, 18, 31, 43, 8, 21, 37, 10, 5, 38, 45, 15, 37, 34, 11, 6, 46, 40, 10, 10, 38, 30, 9, 36};
        Integer[] data2 = new Integer[]{25, 26, 20, 21, 21, 36, 40, 29, 27, 11, 34, 46, 3, 45, 12, 26, 37, 5, 2, 8, 41, 3, 22, 26, 2, 48, 47, 7, 36, 16, 44, 12, 14, 43, 7, 29, 32, 31, 39, 6, 38, 32, 11, 40, 7, 24, 25, 37, 44, 15};
        for (int i = 0; i < data.length; i++) {
            tree.put(data[i], data[i]);
            tree.checkFactor();
        }

        for (int i = 0; i < data2.length; i++) {
            tree.delete(data2[i]);
            if (i == 18) {
                System.out.println(i);
            }
            tree.checkSize();
            tree.checkFactor();
        }*/


    }


}
