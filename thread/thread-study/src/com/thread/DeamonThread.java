package com.thread;

public class DeamonThread extends Thread{

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread deamonThread = new DeamonThread();
        deamonThread.setDaemon(true);
        deamonThread.start();
    }

}
