package com.liuqiqi.sort;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author liuqiqi
 * @date 2020/4/7 16:49
 */
public class Test {

    public static void main(String args[]){

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 30, 10000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        long max = Integer.MAX_VALUE + 20L;
        List<Callable<Integer>> list = new LinkedList<>();
        for(long i = 0; i < max;i++) {
            list.add(()-> {
                try {
                    Thread.sleep(100000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1;
            });
            if(i % 10000000 == 0) {
                System.out.println(i);
            }
        }
        try {
            threadPoolExecutor.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程数"+threadPoolExecutor.getPoolSize());
    }

}
