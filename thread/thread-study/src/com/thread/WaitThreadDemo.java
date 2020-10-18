package com.thread;

public class WaitThreadDemo {

    public static Object iceCream=null;

    public static void main(String[] args) throws InterruptedException {
        WaitThreadDemo waitThreadDemo = new WaitThreadDemo();
        waitThreadDemo.deadLocke2();
//        waitThreadDemo.deadLocke1();
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
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
            this.notify();//通知小朋友,但是没有生效
        }
        System.out.println("通知下朋友");
    }

    private void deadLocke1() throws InterruptedException {
        new Thread(() -> {
            if (iceCream == null) {
                synchronized (this) {
                    System.out.println("没有冰淇淋，等待中");
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Thread.sleep(3000L);
        iceCream = new Object();
        synchronized (this) {
            this.notify();
            System.out.println("通知完小朋友");
        }
    }

}
