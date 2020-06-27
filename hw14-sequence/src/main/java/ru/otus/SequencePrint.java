package ru.otus;

public class SequencePrint implements Runnable {

    private final Object monitor = new Object();

    @Override
    public void run() {
        try {
            count();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void count() throws InterruptedException {
        int i = 1;
        boolean direction = true;
        while (true) {
            synchronized (monitor) {
                monitor.notify();
                System.out.println(Thread.currentThread().getName() + ": " + i);
                i = direction ? (i + 1) : (i - 1);
                if (i == 10)
                    direction = false;
                if (i == 1)
                    direction = true;
                Thread.sleep(1000);
                monitor.wait();
            }
        }

    }
}
