package com.thread.period1;

public class Demo3_stop {

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();

//        休眠1s，确保i变量自增成功
        Thread.sleep(1000);
        thread.interrupt();

//        确保线程终止
        while (thread.isAlive()){}
        thread.print();
    }

}

class  MyThread extends Thread{
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
        System.out.println("锁释放");
    }

    public void print() {
        System.out.println("i="+i+",j="+j);
    }

}