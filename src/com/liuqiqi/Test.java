package com.liuqiqi;

import java.util.AbstractCollection;
import java.util.PriorityQueue;

/**
 * @author liuqiqi
 * @date 2020/3/25 17:01
 */
public class Test {
    public static void main(String[] args) {
        int hash = hash("2,1");
        System.out.println(hash);
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
