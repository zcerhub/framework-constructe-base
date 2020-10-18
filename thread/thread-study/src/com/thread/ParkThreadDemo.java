package com.thread;

import java.util.concurrent.locks.LockSupport;

public class ParkThreadDemo {

    public static Object iceCream=null;

    public static void main(String[] args) throws InterruptedException {
        ParkThreadDemo waitThreadDemo = new ParkThreadDemo();
//        waitThreadDemo.test();
        waitThreadDemo.deadLocke1();
    }

    private void deadLocke1() throws InterruptedException {
        //        启动线程
        Thread consumer = new Thread(() -> {
            if (iceCream == null) {
                System.out.println("没有买到冰淇淋，小朋友不开心，等待。。。");
                synchronized (this) {
                    LockSupport.park();
                }
            }
            System.out.println("小朋友买到了冰淇淋，开心回家");
        });
        consumer.start();
//        店员开始制作冰淇淋
        Thread.sleep(3000L);
//        制作好冰淇淋
        iceCream = new Object();
        synchronized (this) {
            LockSupport.unpark(consumer);
        }
        System.out.println("通知下朋友");
    }

    private void test() throws InterruptedException {
        Thread consumer=new Thread(() -> {
            if (iceCream == null) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("没有冰淇淋，等待中");
                LockSupport.park();
            }
            System.out.println("买到冰激凌");
        });
        consumer.start();
        Thread.sleep(3000L);
        iceCream = new Object();
        LockSupport.unpark(consumer);
        System.out.println("通知完小朋友");
    }

}
