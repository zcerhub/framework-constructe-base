package com.thread.period2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/*
*
* 效果：
* 1.当做任务执行--》Runnable
* 2.获取返回值--》get（）=Callable.call
*   遇到需要阻塞：wait/notify park/unpark
*
* */
public class MyFutureTask<T> implements Runnable{

    private T result;
    private Callable<T> target;
    private String state = "NEW";
    private BlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public MyFutureTask(Callable<T> caller) {
        target = caller;
    }

    @Override
    public void run() {
        try {
            T localResult = target.call();
            result = localResult;
            state = "DONE";
        } catch (Exception e) {
            e.printStackTrace();
            state = "ERROR";
        }

//        任务执行完毕，唤醒所有等待的线程，生产者-消费者模式
        if ("DONE".equals(state)) {
            while (true) {
                Thread th = waiters.poll();
                if (th != null) {
                    LockSupport.unpark(th);
                }else {
                    break;
                }
            }
        }
    }


    /*
     * 通过get获得结果值
     * */
    public T get() {
        //能不能获取得到？不定
        if (!"DONE".equals(state)) {
            //记录被等待的线程
            waiters.offer(Thread.currentThread());
        }
        //自旋，应用地方，简单内存基本的操作
//        假如任务正在运行，等待
        while (!"DONE".equals(state)) {
            LockSupport.park();
        }
        return result;
    }

}
