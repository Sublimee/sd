package org.example.task_5;

/*
    В исходном решении thread1 ждёт, пока освободится ресурс, занятый потоком thread2, а поток thread2 в свою очередь
    ждёт, пока освободится ресурс, занятый потоком thread1. Нужно всегда брать блокировку в одном и том же порядке:
    lock1 -> lock2. Если объекты, на которых будем брать блокировку, заранее неизвестны, то нужно иметь возможность
    сравнить объекты. Это нужно для того, чтобы брать блокировки всегда в одном и том же порядке, например, от "меньших"
    к бОльшим.
 */
public class DeadlockFixedExample {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1 acquired lock1");

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("Thread 1 acquired lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 2 acquired lock1");

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("Thread 2 acquired lock2");
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");
    }
}