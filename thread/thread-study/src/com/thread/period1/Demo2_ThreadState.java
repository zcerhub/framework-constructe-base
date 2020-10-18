package com.thread.period1;

/*
* 示例2- 多线程运行状态切换示例
* */
public class Demo2_ThreadState {

    public static Thread thread;
    public static Demo2_ThreadState obj;

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
        test3();
    }

    private static void test3() throws InterruptedException {
        System.out.println("第三种：新建-》运行-》阻塞-》运行-》终止");
//        创建thread，暂不启动
        Thread thread = new Thread(() -> {
            System.out.println("2、抢锁之前，thread状态：" + Thread.currentThread().getState());
//            3子线程强锁，阻塞
            synchronized (Demo2_ThreadState.class) {
                System.out.println("5、thread拿到锁，继续运行执行，thread状态" + Thread.currentThread().getState());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        synchronized (Demo2_ThreadState.class) {
            System.out.println("1.主线程拿到锁，启动thread");
            thread.start();//2.启动子线程
            Thread.sleep(5000L);
            System.out.println("3.thread的状态："+thread.getState());
            Thread.sleep(5000L);
        }
        System.out.println("4主线程释放锁");
    }

    private static void test2() throws InterruptedException {
        System.out.println("#########第二种：新建-》运行-》等待-》运行-》终止（sleep方式）#####");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000L);
                System.out.println("3.sleep结束，" + Thread.currentThread() + "当前状态为：" + Thread.currentThread().getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("1.调用start后，thread状态：" + Thread.currentThread() + "当前状态为：" + Thread.currentThread().getState());

        //等待2000毫秒，再看状态
        Thread.sleep(2000L);
        System.out.println("2.等待2s后，thread状态：" + Thread.currentThread() + "当前状态为：" + Thread.currentThread().getState());

    }

    private static void test1() throws InterruptedException {
//        第一种状态切换-新建-运行-终止
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程发来贺电。。。");
            }
        });
        System.out.println("1、调用start方法前，thread状态：" + thread.getState());
        Thread.sleep(2000L);
        thread.start();
        System.out.println("2、调用start方法后，thread状态：" + thread.getState());

        Thread.sleep(4000L);//等待thread执行结束，再看状态
        System.out.println("3、4s后，thread状态：" + thread.getState());
        thread.start();//线程终止后，再进行调用，会抛出IllegalThreadStateException
    }

}
