package V2ThreadSafety;

import java.util.List;

public class MessageQueue {
    private List<String> messageQueue;

    private final Object lock = new Object();
    public MessageQueue(List<String> messageQueue) {
        this.messageQueue = messageQueue;
    }

    public String subscribe(){

        synchronized (lock){
            try {
                while(messageQueue.isEmpty()){
                    System.out.println(Thread.currentThread().getName() + " thread started waiting ");
                    lock.wait();
                }
                return messageQueue.removeFirst();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void publish(String message){
        synchronized (lock){
            messageQueue.add(message);
            lock.notify();
        }
    }



}
