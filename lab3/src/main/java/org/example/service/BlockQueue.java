package org.example.service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BlockQueue {

    private Queue<Integer> queue;
    private Semaphore semaphoreEmpty;
    private Semaphore semaphoreFull;
    private int maxSize;

    public BlockQueue(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
        this.semaphoreEmpty = new Semaphore(0);
        this.semaphoreFull = new Semaphore(maxSize);
    }

    public void put(Integer number) throws InterruptedException {
        semaphoreFull.acquire();
        synchronized (this) {
            queue.add(number);
        }
        semaphoreEmpty.release();
    }

    public Integer take() throws InterruptedException {
        semaphoreEmpty.acquire();
        Integer number;
        synchronized (this) {
            number = queue.poll();
        }
        semaphoreFull.release();
        return number;
    }

    public Integer size() {
        return queue.size();
    }

    public Boolean isEmpty() {
        return queue.isEmpty();
    }
}
