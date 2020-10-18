package com.thread;

public class IceCreamDemo1 {

    public static Object iceCream=null;

    public static void main(String[] args) throws InterruptedException {
        new IceCreamDemo1().deadLocke2();
//        new IceCreamDemo1().deadLocke1();
//        new IceCreamDemo1().suspendAndResume();
    }

    private void deadLocke2() throws InterruptedException {
        //        启动线程
        Thread consumer = new Thread(() -> {
            if (iceCream == null) {
                try {
                    Thread.sleep(5000L);//小朋友出去happy
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("没有买到冰淇淋，小朋友不开心，等待。。。");
                Thread.currentThread().suspend();
            }
            System.out.println("小朋友买到了冰淇淋，开心回家");
        });
        consumer.start();
//        店员开始制作冰淇淋
        Thread.sleep(3000L);
//        制作好冰淇淋
        iceCream = new Object();
        consumer.resume();//通知小朋友,但是没有生效
        System.out.println("通知下朋友");
    }


    private void deadLocke1() throws InterruptedException {
        //        启动线程
        Thread consumer = new Thread(() -> {
            if (iceCream == null) {
                System.out.println("没有买到冰淇淋，小朋友不开心，等待。。。");
                synchronized (this) {//小朋友获得了该对象锁，然后睡着
                    Thread.currentThread().suspend();
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
            consumer.resume();//通知小朋友
        }
        System.out.println("通知下朋友");
    }

    private void suspendAndResume() throws InterruptedException {
        //        启动线程
        Thread consumer = new Thread(() -> {
            if (iceCream == null) {
                System.out.println("没有买到冰淇淋，小朋友不开心，等待。。。");
                Thread.currentThread().suspend();
            }
            System.out.println("小朋友买到了冰淇淋，开心回家");
        });
        consumer.start();
//        店员开始制作冰淇淋
        Thread.sleep(3000L);
//        制作好冰淇淋
        iceCream = new Object();
        consumer.resume();//通知小朋友
        System.out.println("通知下朋友");
    }

}
