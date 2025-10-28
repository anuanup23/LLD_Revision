package v3BlockingQueue;

import java.util.Objects;

public class Subscriber implements Runnable{

    private String name;
    private MessageQueue messageQueue;

    public Subscriber(MessageQueue messageQueue, String name) {
        this.messageQueue = messageQueue;
        this.name = name;
    }

    private void subscribe(){

        String message = messageQueue.subscribe();
        if(Objects.isNull(message)){
            System.out.println(name + " Queue is empty");
            return;
        }
        System.out.println(name + " subscribed to " + message);
    }
    @Override
    public void run() {
        while(messageQueue.isRunning()){
            subscribe();
        }
    }
}
