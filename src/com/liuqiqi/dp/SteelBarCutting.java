package com.liuqiqi.dp;

/**
 * @author liuqiqi
 * @date 2020/8/9 21:57
 */
public class SteelBarCutting {
    private static  int TIMES = 0;
    private static final int[] PRICE = new int[]{1,5,8,9,10,17,17,20,24,30};

    private static int getPrice(int length) {
        if(length < 1 || length > 10) {
            throw new IllegalArgumentException("length not support");
        }
        return PRICE[length - 1];
    }


    public static int getBestPrice(int length) {
        if(length == 0) {
            return 0;
        }
        int maxPrice = 0;
        for (int i = 1; i <= length;i++) {
            TIMES ++ ;
            int price = getPrice(i) + getBestPrice(length - i);
            if(price > maxPrice) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }

    public static void main(String[] args) {
        int bestPrice = getBestPrice(10);
        System.out.println(bestPrice);
        System.out.println(TIMES);
    }
}
