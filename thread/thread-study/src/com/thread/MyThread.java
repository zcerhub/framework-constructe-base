package com.thread;

public class MyThread extends Thread{

    private int i=0;

    @Override
    public void run() {
        while (i++ < 10) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }

    public static void main(String[] args) {
        Thread myThread = new MyThread();
        myThread.start();
    }

}
