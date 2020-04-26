package com.liuqiqi.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 最大子数组
 * @author liuqiqi
 * @date 2020/4/24 15:02
 */
public class LongestSubSeqSum {

    public int[] getLongestSubSeqSum(int begin, int end, int[] a) {
        int mid = begin + (end - begin) / 2;
        System.out.println("这批次的 begin mid end : "+ begin+ "," + mid + "," + end);
        int[] seqIndex = new int[3];
        if (begin == end) {
            seqIndex[0] = begin;
            seqIndex[1] = begin;
            seqIndex[2] = a[begin];
            return seqIndex;
        }


        int[] leftLongestSubSeqSum = getLongestSubSeqSum(begin, mid, a);

        int[] rightLongestSubSeqSum = getLongestSubSeqSum(mid + 1, end, a);

        int[] midLongestSubSeqSum = LongestSubSeqByMid(begin, end, a);

        int[] temp = (leftLongestSubSeqSum[2] > rightLongestSubSeqSum[2]) ? leftLongestSubSeqSum : rightLongestSubSeqSum;
        int[] max = (temp[2] > midLongestSubSeqSum[2]) ? temp : midLongestSubSeqSum;
        return max;
    }

    public int[] LongestSubSeqByMid(int begin, int end, int[] a) {
        int[] seqIndex = new int[3];
        int mid = begin + (end - begin) / 2;

        int leftSum = a[mid];
        int leftMaxSum = a[mid];
        int leftMaxIndex = mid;
        for (int i = mid - 1; i >= begin && i >= 0; i--) {
            leftSum += a[i] ;
            if (leftSum > leftMaxSum) {
                leftMaxSum = leftSum;
                leftMaxIndex = i;
            }

        }

        int rightSum = a[mid + 1];
        int rightMaxSum = a[mid + 1];
        int rightMaxIndex = mid + 1;
        for (int i = mid + 2; i <= end; i++) {
            rightSum += a[i] ;
            if (rightSum > rightMaxSum) {
                rightMaxSum = rightSum;
                rightMaxIndex = i;
            }

        }
        seqIndex[0] = leftMaxIndex;
        seqIndex[1] = rightMaxIndex;
        seqIndex[2] = leftMaxSum + rightMaxSum;
        return seqIndex;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] data = new int[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = 5 - random.nextInt(10);
        }
        System.out.println(Arrays.toString(data));
        LongestSubSeqSum longestSubSeqSum = new LongestSubSeqSum();
        int[] result = longestSubSeqSum.getLongestSubSeqSum(0, data.length - 1, data);
        System.out.println(Arrays.toString(result));
    }
}
