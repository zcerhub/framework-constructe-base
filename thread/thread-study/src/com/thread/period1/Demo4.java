package com.thread.period1;

public class Demo4 {
    static Demo4 ref=null;

    public static void main(String[] args) {
        new Thread(()->{
            ref = new Demo4();
        }).start();

        new Thread(()->{
            System.out.println(ref);
        }).start();
    }

}
