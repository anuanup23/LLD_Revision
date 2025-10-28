package v3BlockingQueue;

public class Publisher implements Runnable{
    String name;
    public Publisher(MessageQueue messageQueue, String name) {
        this.messageQueue = messageQueue;
        this.name = name;
    }

    private MessageQueue messageQueue;
    private void publish(String message){
        if(messageQueue.isRunning()){
            System.out.println(name + " Publishing message " + message);
            messageQueue.publish(message);
        }
        else{
            System.out.println("Message queue is not running, hence not publishing");
        }
    }
    @Override
    public void run() {
        try {
            for(int i = 0; i < 5; ++i){
                publish(name + " Publisher Message " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
