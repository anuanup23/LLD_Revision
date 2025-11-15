package src.atomicInt.worker;




import src.atomicInt.DummyObject;
import src.atomicInt.runner.AtomicIntegerExperimentRunner;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerIncrementWorker implements Runnable{

    private final DummyObject obj;
    public AtomicIntegerIncrementWorker(DummyObject obj){
        this.obj = obj;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("AtomicIntegerIncrementWorker is Executing Object - " + Thread.currentThread().getName() + " - " + this.obj.getName());
        try {
            Thread.sleep(5);
            // reading a value which is not enclosed in synchronised block can be risky, but here it does not cause any issues
            // as there is a different key for each thread, if this assumption breaks then there will be case of stale reads.
            AtomicInteger currentThreadCount = AtomicIntegerExperimentRunner.threadCount.getOrDefault(Thread.currentThread().getName(), new AtomicInteger(0));
            // here synchronized (this) will not work as the lock is not optimally taken.
            synchronized (AtomicIntegerExperimentRunner.threadCount){
                AtomicIntegerExperimentRunner.threadCount.put(Thread.currentThread().getName(), new AtomicInteger(currentThreadCount.incrementAndGet()));
            }
            AtomicIntegerExperimentRunner.count.incrementAndGet();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
