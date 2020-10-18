package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyCallable implements Callable {

    private int i=0;

    @Override
    public Object call() throws Exception {
        while (i++ < 10) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
        return i;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask myFutureTask = new FutureTask(new MyCallable());
        Thread myThread = new Thread(myFutureTask);
        myThread.start();
        System.out.println("子线程的返回值为："+myFutureTask.get());
    }


}
