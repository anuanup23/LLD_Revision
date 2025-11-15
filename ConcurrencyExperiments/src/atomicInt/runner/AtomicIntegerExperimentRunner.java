package src.atomicInt.runner;

import src.atomicInt.DummyObject;
import src.atomicInt.worker.AtomicIntegerIncrementWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// THis class is demo for AtomicInteger, a thread pool increases the value till n,
// a threadCount map keeps count how many times, each thread has increased
public class AtomicIntegerExperimentRunner {

   public static final Map<String, AtomicInteger> threadCount = new HashMap<String, AtomicInteger>();
   public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService myExecutor = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 5000; ++i){
            AtomicIntegerIncrementWorker atomicIntegerIncrementWorker = new AtomicIntegerIncrementWorker(new DummyObject("Anu " + String.valueOf(i)));
            myExecutor.execute(atomicIntegerIncrementWorker);
        }
        myExecutor.shutdown();
        while (!myExecutor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("Waiting for Executor completion");
        }
        System.out.println("Trying to print thread count");
        int totalCount = 0;
        for (String name : threadCount.keySet())
        {
            AtomicInteger val = threadCount.get(name);
            totalCount += val.get();
        }
        threadCount.forEach((k,v) -> {
            System.out.println("Key = "
                + k + ", Value = " + v);
        });

        System.out.println("Count - " + count + " ThreadCount = " + totalCount);
    }
}
