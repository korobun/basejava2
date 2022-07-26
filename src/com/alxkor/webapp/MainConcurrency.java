package com.alxkor.webapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    //private static final Object LOCK = new Object();
    public static final int THREADS_NUMBER = 10000;
    private static int counter;

    private static final Lock LOCK = new ReentrantLock();
    private static final AtomicInteger atomicCounter = new AtomicInteger();

    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(SimpleDateFormat::new);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    inc();
//                }
//            });
//            threads.add(thread);
//            thread.start();
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    //inc();
                    atomicCounter.incrementAndGet();
                }
                System.out.println(threadLocal.get().format(new Date()));
                latch.countDown();
            });
        }

//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        executorService.shutdown();
        latch.await();
        System.out.println(atomicCounter.get());
        final String lock1 = "lock1";
        final String lock2 = "lock2";
        deadLock(lock1, lock2);
        // deadLock(lock2, lock1);
    }

    private static void deadLock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private static void inc() {
        //synchronized (LOCK) {
        LOCK.lock();
        try {
            counter++;
        } finally {
            LOCK.unlock();
        }
        //}
    }
}
