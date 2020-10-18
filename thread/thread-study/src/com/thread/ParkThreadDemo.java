package com.thread;

import java.util.concurrent.locks.LockSupport;

public class ParkThreadDemo {

    public static Object iceCream=null;

    public static void main(String[] args) throws InterruptedException {
        ParkThreadDemo waitThreadDemo = new ParkThreadDemo();
//        waitThreadDemo.test();
//        waitThreadDemo.deadLocke1();
//        waitThreadDemo.parkUnparkTest();
//        waitThreadDemo.parkUnparkTest1();
//        waitThreadDemo.parkUnparkTest3();
//        waitThreadDemo.parkUnparkTest4();
//        waitThreadDemo.parkUnparkTest5();
//        waitThreadDemo.sleepInterruptTest1();
        waitThreadDemo.sleepInterruptTest2();
    }

    private void sleepInterruptTest2() {
        Thread.currentThread().interrupt();
        try {
            Thread.sleep(1000);//消耗掉中断状态，抛出异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.park();//消耗掉permit
    }

    private void sleepInterruptTest1() {
        Thread.currentThread().interrupt();
        try {
            Thread.sleep(1000);//消耗掉中断状态，抛出异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void parkUnparkTest4() {
        Thread.currentThread().interrupt();
        LockSupport.park();//消耗掉permit后，直接返回了
    }

    private void parkUnparkTest5() {
        Thread.currentThread().interrupt();
        LockSupport.park();//消耗掉permit后，直接返回了
        LockSupport.park();//中断状态为true，直接返回
        LockSupport.park();//同上
    }



    private void parkUnparkTest3() {
        Thread main = Thread.currentThread();
        new Thread(() -> {
            System.out.println("子线程开始睡眠");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程睡醒了");
            LockSupport.unpark(main);
        }).start();

        LockSupport.park();//被唤醒后中断状态为true，会被唤醒
        LockSupport.park();//被唤醒后中断状态为true，会被唤醒
    }

    private void parkUnparkTest2() {
        LockSupport.unpark(Thread.currentThread());//置permit为1
        LockSupport.park();//消耗掉permit后，直接返回了
        LockSupport.park();//此时permti为0，中断状态为false，进入阻塞状态
    }

    private void parkUnparkTest1() {
        LockSupport.unpark(Thread.currentThread());//置permit为1
        LockSupport.park();//消耗掉permit后，直接返回了
    }

    private void parkUnparkTest() {
        LockSupport.park();//因为此时permit为0且中断转态为false（默认如此），所以阻塞
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
