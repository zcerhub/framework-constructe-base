package com.thread;

public class FlagControl {

    private volatile static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (flag) {
                System.out.println("运行中");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(30000L);
        flag = false;
        System.out.println("程序运行结束");
    }

}
