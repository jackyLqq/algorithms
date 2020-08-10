package com.liuqiqi.dp;

/**
 * @author liuqiqi
 * @date 2020/8/9 22:41
 */
public class SteelBarCuttingBottomUp {

    private static  int TIMES = 0;

    private static final int[] PRICE = new int[]{1,5,8,9,10,17,17,20,24,30};

    private static final int[] MAX_PRICE = new int[10];

    private static int getPrice(int length) {
        if(length < 1 || length > 10) {
            throw new IllegalArgumentException("length not support");
        }
        return PRICE[length - 1];
    }

    private static int getMaxPrice(int length) {
        if(length == 0) {
            return 0;
        }
        if(length < 1 || length > 10) {
            throw new IllegalArgumentException("length not support");
        }
        return MAX_PRICE[length - 1];
    }

    private static void setMaxPrice(int length, int price) {
        MAX_PRICE[length - 1] = price;
    }


    public static int getBestPrice(int length) {
        if(length == 0) {
            return 0;
        }
        for(int i = 1;i <= length;i++) {
            int maxPrice = 0;
            for(int j = 1;j <= i;j++) {
                TIMES++;
                int price = getPrice(j) + getMaxPrice(i - j);
                if(price > maxPrice) {
                    maxPrice = price;
                }
            }
            setMaxPrice(i, maxPrice);
        }

        return getPrice(length);
    }

    public static void main(String[] args) {
        int bestPrice = getBestPrice(10);
        System.out.println(bestPrice);
        System.out.println(TIMES);
    }
}
