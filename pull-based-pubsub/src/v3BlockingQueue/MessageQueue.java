package v3BlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageQueue {
    private BlockingQueue<String> messageQueue;
    private boolean running;
    public MessageQueue(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
        this.running = true;
    }

    public String subscribe(){

        try {
            return messageQueue.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void publish(String message){
            messageQueue.add(message);
    }

    public boolean isRunning() {
        return this.running;
    }

    public void stopQueue(){
        System.out.println("stopping the queue");
        this.running = false;
    }


}
