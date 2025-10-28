package v3BlockingQueue;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Runner {


    public static void main(String[] args) {


        MessageQueue messageQueue = new MessageQueue(new LinkedBlockingQueue<>(10));
        Publisher p1 = new Publisher(messageQueue, "p1");
        Subscriber s1 = new Subscriber(messageQueue, "s1");
        Publisher p2 = new Publisher(messageQueue, "p2");
        Subscriber s2 = new Subscriber(messageQueue, "s2");
        ExecutorService executors = Executors.newFixedThreadPool(4);
        executors.execute(p1);
        executors.execute(s1);
        executors.execute(p2);
        executors.execute(s2);

        try {
            Thread.sleep(5500);
            messageQueue.stopQueue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executors.shutdown();
    }
}
