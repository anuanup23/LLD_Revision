package src.basicMainThread;

public class DummyReader extends Thread {
    String dummyResult = "Anup's Reader Thread";

    public DummyReader(){
        super("DummyThread");
        System.out.println("creating a dummyReader object");
    }
    public void run(){
        System.out.println("Reading dummy Data " + Thread.currentThread().getName());
        try {
            Thread.sleep(10);
            System.out.println("Completed Dummy thread");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResult(){
        String reader = Thread.currentThread().getName();
        if(reader.contains("Reader")){
            return dummyResult;
        }
        else return "Unknown Thread";
    }
}
