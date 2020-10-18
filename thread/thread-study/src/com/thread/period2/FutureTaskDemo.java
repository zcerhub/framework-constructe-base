package com.thread.period2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class FutureTaskDemo {

    public static void main(String[] args) {
        final FutureTaskDemo demo = new FutureTaskDemo();
        demo.mapFutureTask();
    }

    public void mapFutureTask() {
//        如何将串行变异步并行
        final FutureTaskDemo demo = new FutureTaskDemo();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //FutureTask实现异步执行逻辑
        //准备map
        Map<String, FutureTask<String>> futureTaskMap = new HashMap<>();
        Callable<String> call1=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return demo.loadUserInfo();
            }
        };
//        futureTaskMap.put("a", task);
        futureTaskMap.put("a", (FutureTask<String>) executorService.submit(call1));
//        提交给线程执行
//        executorService.submit(task);
        //获取返回值
        try {
            System.out.println(futureTaskMap.get("a").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println('d');
    }

    private String loadUserInfo() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "娃哈哈";
    }

}
