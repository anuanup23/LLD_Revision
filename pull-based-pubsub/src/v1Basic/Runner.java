package v1Basic;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {


    public static void main(String[] args) {

        MessageQueue messageQueue = new MessageQueue(new ArrayList<>());
        Publisher p1 = new Publisher(messageQueue, "p1");
        Subscriber s1 = new Subscriber(messageQueue, "s1");
        Publisher p2 = new Publisher(messageQueue, "p2");
        Subscriber s2 = new Subscriber(messageQueue, "s2");
        ExecutorService executors = Executors.newFixedThreadPool(4);
        executors.execute(p1);
        executors.execute(s1);
        executors.execute(p2);
        executors.execute(s2);
    }
}
