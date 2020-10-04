package com.liuqiqi.offer;

/**
 * @author liuqiqi
 * @date 2020/10/4 14:36
 */
public class TwoOrderedDimensionalArray {

    public static void main(String[] args) {
        int[][] array = new int[4][4];
        array[0] = new int[]{1, 2, 4, 8};
        array[1] = new int[]{2, 4, 9, 12};
        array[2] = new int[]{4, 7, 10, 13};
        array[3] = new int[]{6, 8, 11, 15};
        int num = 3;
        boolean hasNumber = hasNumber(array, num);
        System.out.println("是否存在值" + num + ":" + hasNumber);
    }

    public static boolean hasNumber(int[][] array, int num) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array can not null and empty");
        }
        int row = 0;
        int column = array[0].length - 1;
        while (row < array.length && column >= 0) {
            if (array[row][column] == num) {
                return true;
            } else if (array[row][column] > num) {
                column--;
            } else {
                row++;
            }
        }
        return false;
    }
}
