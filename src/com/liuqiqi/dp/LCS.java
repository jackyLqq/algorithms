package com.liuqiqi.dp;

/**
 * @author liuqiqi
 * @date 2020/8/23 11:51
 */
public class LCS {

    static class Node {
        static final int MATCH_FLAG = 1;
        static final int MATCH_LEFT = 2;
        static final int MATCH_UP = 3;
        int[][] length;
        int[][] flag;

        public Node(int x, int y) {
            this.length = new int[x + 1][y + 1];
            this.flag = new int[x + 1][y + 1];
        }


    }

    private static Node getLcsNode(char[] x, char[] y) {
        if (x.length == 0 || y.length == 0) {
            return new Node(0, 0);
        }
        Node node = new Node(x.length, y.length);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                if (x[i] == y[j]) {
                    node.length[i + 1][j + 1] = node.length[i][j] + 1;
                    node.flag[i + 1][j + 1] = Node.MATCH_FLAG;
                    continue;
                }
                if (node.length[i][j + 1] >= node.length[i + 1][j]) {
                    node.length[i + 1][j + 1] = node.length[i][j + 1];
                    node.flag[i + 1][j + 1] = Node.MATCH_UP;
                } else {
                    node.length[i + 1][j + 1] = node.length[i + 1][j];
                    node.flag[i + 1][j + 1] = Node.MATCH_LEFT;
                }
            }
        }
        return node;
    }

    public static int getLcsLength(char[] x, char[] y) {
        Node lcsNode = getLcsNode(x, y);
        return lcsNode.length[x.length][y.length];
    }

    public static void printLcsMatch(char[] x, char[] y) {
        Node lcsNode = getLcsNode(x, y);
        int[][] flag = lcsNode.flag;
        char[] match = new char[lcsNode.length[x.length][y.length]];
        doPrintLcsMatch(flag, x, x.length, y.length);
    }

    private static void doPrintLcsMatch(int[][] flag, char[] x, int xLength, int yLength) {
        if (xLength == 0 || yLength == 0) {
            return;
        }
        if (flag[xLength][yLength] == Node.MATCH_FLAG) {
            doPrintLcsMatch(flag, x, xLength - 1, yLength - 1);
            System.out.println(x[xLength - 1]);
        } else if (flag[xLength][yLength] == Node.MATCH_UP) {
            doPrintLcsMatch(flag, x, xLength - 1, yLength);
        } else {
            doPrintLcsMatch(flag, x, xLength, yLength - 1);
        }
    }

    public static void main(String[] args) {
        char[] x = new char[]{'A', 'B', 'C', 'B', 'D', 'A', 'B'};
        char[] y = new char[]{'B', 'D', 'C', 'A', 'B', 'A'};
        int lcsLength = getLcsLength(x, y);
        System.out.println(lcsLength);
        printLcsMatch(x, y);
    }
}
