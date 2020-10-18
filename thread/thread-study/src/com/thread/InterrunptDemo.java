package com.thread;

public class InterrunptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("运行中");
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
//                        isInterrunpted状态被清除
                        e.printStackTrace();
                        System.out.println("线程" + Thread.currentThread().getName() + "的中断状态为：" + Thread.currentThread().isInterrupted());
//                        将擦除掉的isInterrupted状态补上
                        Thread.currentThread().interrupt();
                        System.out.println("线程" + Thread.currentThread().getName() + "的中断状态为：" + Thread.currentThread().isInterrupted());
                    }
                }
            }
        });
        workThread.start();

        Thread.sleep(3000L);
        workThread.interrupt();
    }

}
