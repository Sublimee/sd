package org.example.task_6;

import java.util.concurrent.CountDownLatch;
/*
    Пример использования CountDownLatch
 */
public class NaiveLoadTesting {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 50; i++) {
            new ServiceLoadThread(latch).start();
        }

        latch.countDown();
    }
}

class ServiceLoadThread extends Thread {
    private final CountDownLatch latch;

    public ServiceLoadThread(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await();
            // do rest call
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
