package src.blockVsWait;

public class DummyObjectWithVersion {
    private final String name;
    private int version ;


    DummyObjectWithVersion(String name, int version){
        this.name = name;
        this.version = version;
    }

    public String getName(){
        return this.name;
    }

    public void incrementVersion() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " Acquire the Lock for updating version");
        Thread.sleep(100);
        this.version++;
        Thread.sleep(100);
    }

    public int getVersion(){
        return this.version;
    }
}
