package org.example.task_6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/*
    Пример использования ThreadPoolExecutor и AtomicLong
 */
public class MonitoredThreadPool extends ThreadPoolExecutor {

    private final AtomicLong totalTime = new AtomicLong(0);
    private final AtomicLong taskCount = new AtomicLong(0);
    private final AtomicLong maxTime = new AtomicLong(0);
    private final AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);

    public MonitoredThreadPool(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static void main(String[] args) {
        MonitoredThreadPool threadPool = new MonitoredThreadPool(2, 3, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        for (int i = 0; i < 9; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        threadPool.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        long endTime = System.nanoTime();
        long startTime = ((MonitoredRunnable) r).getStartTime();
        long executeTime = endTime - startTime;

        totalTime.addAndGet(executeTime);
        taskCount.incrementAndGet();

        updateMaxMinTime(executeTime);
    }

    private void updateMaxMinTime(long executeTime) {
        while (true) {
            long currentMax = maxTime.get();
            if (executeTime > currentMax) {
                if (maxTime.compareAndSet(currentMax, executeTime)) {
                    break;
                }
            } else {
                break;
            }
        }

        while (true) {
            long currentMin = minTime.get();
            if (executeTime < currentMin) {
                if (minTime.compareAndSet(currentMin, executeTime)) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    @Override
    protected void terminated() {
        System.out.println("Task Count: " + taskCount.get());
        System.out.println("Total Task Execution Time: " + totalTime.get() + " nanoseconds");
        System.out.println("Average Task Execution Time: " + (totalTime.get() / taskCount.get()) + " nanoseconds");
        System.out.println("Max Task Execution Time: " + maxTime.get() + " nanoseconds");
        System.out.println("Min Task Execution Time: " + minTime.get() + " nanoseconds");
        super.terminated();
    }

    @Override
    public void execute(Runnable command) {
        super.execute(new MonitoredRunnable(command));
    }

    private static class MonitoredRunnable implements Runnable {
        private final Runnable task;
        private final long startTime;

        public MonitoredRunnable(Runnable task) {
            this.task = task;
            this.startTime = System.nanoTime();
        }

        public long getStartTime() {
            return startTime;
        }

        @Override
        public void run() {
            task.run();
        }
    }
}