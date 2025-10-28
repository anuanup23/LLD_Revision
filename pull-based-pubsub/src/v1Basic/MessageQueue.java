package v1Basic;

import java.util.List;

public class MessageQueue {
    private List<String> messageQueue;

    public MessageQueue(List<String> messageQueue) {
        this.messageQueue = messageQueue;
    }

    public String subscribe(){
        if(messageQueue.isEmpty()){
            return null;
        }
        return messageQueue.removeFirst();
    }

    public void publish(String message){
        messageQueue.add(message);
    }



}
