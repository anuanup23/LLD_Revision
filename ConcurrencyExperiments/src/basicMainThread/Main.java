package src.basicMainThread;


public class Main extends Thread{
    DummyReader dr;
    public Main(DummyReader dr){
        super("MainReaderThread");
        this.dr = dr;
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Thread is  " + Thread.currentThread().getName());
        DummyReader dr = new DummyReader();
        dr.start();
//        System.out.println("Hello world!");
//        Thread.sleep(1000);
//        dr.join();
        Main mn = new Main(dr);
//            System.out.println("started ------");
        mn.start();
        mn.join();
        mn.printThreads();

    }
    public void run(){
        System.out.println("Data is " + dr.getResult());
        try {
            Thread.sleep(1500);
            System.out.println("Completed Main Run method");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printThreads(){
        Thread[] threads = new Thread[Thread.activeCount()];
        int n = Thread.enumerate(threads);
        for(int i = 0; i < n; ++i){
            System.out.println("Thread " + i + " is " + threads[i].getName());
        }

    }
}