package com.thread;

public class StopThreadDemo extends Thread{

    private int i=0,j=0;

    @Override
    public void run() {
        synchronized (this) {
            ++i;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++j;
        }
    }

    public void print(){
        System.out.println("i="+i+" j="+j);
    }

    public static void main(String[] args) throws InterruptedException {
        StopThreadDemo stopThreadDemo = new StopThreadDemo();
        stopThreadDemo.start();

        Thread.sleep(1000);
        stopThreadDemo.stop();

        while (stopThreadDemo.isAlive()) {}
        stopThreadDemo.print();
    }
}
