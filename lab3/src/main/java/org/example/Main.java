package org.example;

import org.example.service.MethodService;

public class Main {

    public static void main(String[] args) {
        Thread pThread = new Thread(MethodService::processP);
        Thread p1Thread = new Thread(MethodService::processP1);
        Thread p2Thread = new Thread(MethodService::processP2);
        pThread.start();
        p2Thread.start();
        p1Thread.start();
        try {
            pThread.join();
            p1Thread.join();
            p2Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
