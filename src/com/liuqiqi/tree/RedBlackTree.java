package com.liuqiqi.tree;

import java.util.Comparator;
import java.util.Random;

/**
 * 红黑树，大致平衡二叉搜索树
 *
 * @author liuqiqi
 * @date 2020/5/24 20:52
 */
public class RedBlackTree<K, V> extends AbstractBinarySearchTree<K, V> implements Tree<K, V> {

    private int size = 0;

    private Node<K, V> root;

    private Comparator<? super K> comparator;


    public RedBlackTree() {
        comparator = null;
    }

    public RedBlackTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    @Override
    void setRoot(Node<K, V> node) {
        this.root = node;
    }

    @Override
    Node<K, V> getRoot() {
        return this.root;
    }

    @Override
    Comparator<? super K> getComparator() {
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
    public int size() {
        return this.size;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key can not null");
        }
        Node<K, V> node = new Node<>(key, value, 0);
        V oldVal = super.putNode(node);
        /*不是覆盖*/
        if (oldVal == null) {
            fixAfterInsertion(node);
        }
        return oldVal;
    }

    private void fixAfterInsertion(Node<K, V> node) {
        Node<K, V> temp = node;
        if (temp == root) {
            temp.mark = ColorEnum.BLACK.getColor();
            return;
        }
        /*叔节点为黑色*/
        while (temp.parent != null && temp.parent.mark == ColorEnum.RED.getColor()) {
            /*根节点设置黑色*/
            Node<K, V> ppNode = temp.parent.parent;
            if (getUncle(temp) == null || getUncle(temp).mark == ColorEnum.BLACK.getColor()) {
                if (temp == temp.parent.right && temp.parent == ppNode.left) {
                    lRotate(temp.parent);
                    rRotate(ppNode);
                    temp.mark = ColorEnum.BLACK.getColor();
                } else if (temp == temp.parent.left && temp.parent == ppNode.right) {
                    rRotate(temp.parent);
                    lRotate(ppNode);
                    temp.mark = ColorEnum.BLACK.getColor();
                } else if (ppNode.left != null && temp == ppNode.left.left) {
                    rRotate(ppNode);
                    temp.parent.mark = ColorEnum.BLACK.getColor();
                } else if (ppNode.right != null && temp == ppNode.right.right) {
                    lRotate(ppNode);
                    temp.parent.mark = ColorEnum.BLACK.getColor();
                }
                ppNode.mark = ColorEnum.RED.getColor();
                break;
            } else if (getUncle(temp).mark == ColorEnum.RED.getColor()) {
                /*叔节点为红色*/
                getUncle(temp).mark = ColorEnum.BLACK.getColor();
                temp.parent.mark = ColorEnum.BLACK.getColor();
                ppNode.mark = (ppNode == root ? ColorEnum.BLACK.getColor() : ColorEnum.RED.getColor());
                temp = ppNode;

            }
        }
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

    private Node<K, V> getUncle(Node<K, V> node) {
        if (node.parent == node.parent.parent.left) {
            return node.parent.parent.right;
        } else {
            return node.parent.parent.left;
        }
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

    @Override
    protected void deleteNode(Node<K, V> node) {
        if (node.left != null && node.right != null) {
            Node<K, V> successor = this.successor(node);
            node.key = successor.key;
            node.val = successor.val;
            node = successor;
        }
        Node<K, V> replaceNode = node.left != null ? node.left : node.right;
        if (replaceNode != null) {
            replaceNode.parent = node.parent;
            if (node.parent == null) {
                root = replaceNode;
            } else if (node == node.parent.left) {
                node.parent.left = replaceNode;
            } else {
                node.parent.right = replaceNode;
            }

            node.left = node.right = node.parent = null;
            if (node.mark == ColorEnum.BLACK.getColor()) {
                fixAfterDeletion(replaceNode);
            }

        } else if (node.parent == null) {
            root = null;
        } else {
            if (node.mark == ColorEnum.BLACK.getColor()) {
                fixAfterDeletion(node);
            }

            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else if (node == node.parent.right) {
                    node.parent.right = null;
                }
                node.parent = null;
            }
        }


    }

    private void fixAfterDeletion(Node<K, V> replaceNode) {
        while (replaceNode != root && getColor(replaceNode) == ColorEnum.BLACK.getColor()) {
            Node<K, V> parentNode = replaceNode.parent;
            if (replaceNode == replaceNode.parent.left) {
                Node<K, V> brotherNode = replaceNode.parent.right;
                if (getColor(brotherNode) == ColorEnum.RED.getColor()) {
                    brotherNode.mark = ColorEnum.BLACK.getColor();
                    parentNode.mark = ColorEnum.RED.getColor();
                    lRotate(parentNode);
                    if (parentNode == root) {
                        root = parentNode.parent;
                    }
                    continue;
                } else if (getColor(brotherNode) == ColorEnum.BLACK.getColor()) {
                    if (getColor(brotherNode.left) == ColorEnum.BLACK.getColor() && getColor(brotherNode.right) == ColorEnum.BLACK.getColor()) {
                        brotherNode.mark = ColorEnum.RED.getColor();
                        replaceNode = parentNode;
                    } else if (getColor(brotherNode.right) == ColorEnum.RED.getColor()) {
                        brotherNode.mark = parentNode.mark;
                        parentNode.mark = ColorEnum.BLACK.getColor();
                        brotherNode.right.mark = ColorEnum.BLACK.getColor();
                        lRotate(parentNode);
                        if (parentNode == root) {
                            root = parentNode.parent;
                        }
                        break;
                    } else if (getColor(brotherNode.left) == ColorEnum.RED.getColor() && getColor(brotherNode.right) == ColorEnum.BLACK.getColor()) {
                        brotherNode.mark = ColorEnum.RED.getColor();
                        brotherNode.left.mark = ColorEnum.BLACK.getColor();
                        rRotate(brotherNode);
                    }
                }
            } else if (replaceNode == replaceNode.parent.right) {
                Node<K, V> brotherNode = replaceNode.parent.left;
                if (brotherNode.mark == ColorEnum.RED.getColor()) {
                    brotherNode.mark = ColorEnum.BLACK.getColor();
                    parentNode.mark = ColorEnum.RED.getColor();
                    rRotate(parentNode);
                    if (parentNode == root) {
                        root = parentNode.parent;
                    }
                    continue;
                } else if (brotherNode.mark == ColorEnum.BLACK.getColor()) {
                    if (getColor(brotherNode.left) == ColorEnum.BLACK.getColor() && getColor(brotherNode.right) == ColorEnum.BLACK.getColor()) {
                        brotherNode.mark = ColorEnum.RED.getColor();
                        replaceNode = parentNode;
                    } else if (getColor(brotherNode.left) == ColorEnum.RED.getColor()) {
                        brotherNode.mark = parentNode.mark;
                        parentNode.mark = ColorEnum.BLACK.getColor();
                        brotherNode.left.mark = ColorEnum.BLACK.getColor();
                        rRotate(parentNode);
                        if (parentNode == root) {
                            root = parentNode.parent;
                        }
                        break;
                    } else if (getColor(brotherNode.right) == ColorEnum.RED.getColor() && getColor(brotherNode.left) == ColorEnum.BLACK.getColor()) {
                        brotherNode.mark = ColorEnum.RED.getColor();
                        brotherNode.right.mark = ColorEnum.BLACK.getColor();
                        lRotate(brotherNode);
                    }
                }
            }
        }
        replaceNode.mark = ColorEnum.BLACK.getColor();
    }

    private int getColor(Node<K, V> node) {
        return (node == null ? ColorEnum.BLACK.getColor() : node.mark);
    }

    Integer bh = null;

    public void checkBh(Node<K, V> temp) {
        if (temp == null) {
            return;
        }
        if (temp.left == null && temp.right == null) {
            Integer tempBh = getBh(temp);
            if (bh == null) {
                bh = tempBh;
            }
            if (bh != tempBh) {
                throw new IllegalStateException("黑高不一致");
            }
        }
        checkBh(temp.left);
        checkBh(temp.right);
    }

    public void checkRoot() {
        if (root.mark == ColorEnum.RED.getColor()) {
            throw new IllegalStateException("root is red");
        }
    }

    public void checkRedNode() {
        Node<K, V> min = this.getMin();
        while (min != null) {
            Node<K, V> successor = this.successor(min);
            if (min.mark == ColorEnum.RED.getColor()) {
                if (min.left != null && min.left.mark == ColorEnum.RED.getColor()) {
                    throw new IllegalStateException("red node has Red Child");
                }
                if (min.right != null && min.right.mark == ColorEnum.RED.getColor()) {
                    throw new IllegalStateException("red node has Red Child");
                }
            }
            min = successor;
        }

    }

    private Integer getBh(Node<K, V> temp) {
        int bh = 0;
        while (temp != null) {
            if (temp.mark == ColorEnum.BLACK.getColor()) {
                bh++;
            }
            temp = temp.parent;
        }
        return bh;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            int data = random.nextInt
                    (100000);
            tree.put(data, data);
            System.out.println(i);
        }
        for (int i = 0; i < 1000000; i++) {
            int i1 = random.nextInt(100000);
            tree.delete(i1);
            check(tree);
            System.out.println(i);
        }
    }

    private static void check(RedBlackTree<Integer, Integer> tree) {
        tree.bh = null;
        tree.checkBh(tree.getRoot());
        tree.checkRoot();
        tree.checkRedNode();
    }
}
