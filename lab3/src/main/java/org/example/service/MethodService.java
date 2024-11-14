package org.example.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class MethodService {

    private static final int BUFFER_SIZE = 1, NUM_RANDOMS = 10;
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static final Semaphore semaphore = new Semaphore(1);
    private static final BlockQueue blockQueue = new BlockQueue(BUFFER_SIZE);

    public static void processP() {
        Random rand = new Random();
        for (int i = 0; i < NUM_RANDOMS; i++) {
            try {
                semaphore.acquire();
                if (/*buffer.size()*/blockQueue.size() < BUFFER_SIZE) {
                    int num = rand.nextInt(100);
//                    buffer.put(num);
                    blockQueue.put(num);
                    System.out.println("p (generated): " + num);
                }
                semaphore.release();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void processP1() {
        while (true) {
            try {
                semaphore.acquire();
                if (!/*buffer.isEmpty()*/blockQueue.isEmpty()) {
//                    Integer num = buffer.take();
                    Integer num = blockQueue.take();
                    System.out.println("p1: " + num);
                }
                semaphore.release();
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void processP2() {
        try (FileWriter fileWriter = new FileWriter("output.txt")) {
            while (true) {
                try {
                    semaphore.acquire();
                    if (!/*buffer.isEmpty()*/blockQueue.isEmpty()) {
//                        Integer num = buffer.take();
                        Integer num = blockQueue.take();
                        fileWriter.write("p2: " + num + "\n");
                        fileWriter.flush();
                        System.out.println("p2 (written to file): " + num);
                    }
                    semaphore.release();
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
