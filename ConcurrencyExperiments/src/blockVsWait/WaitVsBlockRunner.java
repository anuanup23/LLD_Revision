package src.blockVsWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;



public class WaitVsBlockRunner {

    // Goal is to keep one instance of this lock;
	// concurrent hash map is important because there can be a case where 2 threads can write 
	// simultaneously to Map which can be having different lock object which in this case is DummyObjectWithVersion.
    public static ConcurrentHashMap<String, AtomicInteger> threadCount = new ConcurrentHashMap<String, AtomicInteger>();
    private static final LockByKey<DummyObjectWithVersion> dummyLock = new LockByKey<>();
    private static final int noOfDummies = 5;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<DummyObjectWithVersion>> listOfCallableDummy = new ArrayList<>();
        ExecutorService myExecutor = Executors.newFixedThreadPool(3);
        List<DummyObjectWithVersion> myDummies = new ArrayList<>();

        for (int i = 0; i < noOfDummies; ++i) {
            myDummies.add(new DummyObjectWithVersion("DumbObjVersion " + (i + 1), 0));
        }
        for (int i = 0; i < 25; ++i) {
            // worker can be new everytime but dummy object is being rotated.
            AccessWorker worker = new AccessWorker(myDummies.get(i % noOfDummies), dummyLock);
            Future<DummyObjectWithVersion> futureDummy = myExecutor.submit(worker);
            listOfCallableDummy.add(futureDummy);
        }
        myExecutor.shutdown();
        while (!myExecutor.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
            System.out.println("Waiting for Executor completion");
        }
        // this is blocking the thread to get the future.
        int i = 1;
        System.out.println("Printing Future valus");
        for (Future<DummyObjectWithVersion> future : listOfCallableDummy) {
            System.out.println(i++ + " " + future.get().getName() + " " + future.get().getVersion());
        }

        System.out.println("Trying to print thread count " + listOfCallableDummy.size() + " " + threadCount.size());

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

        System.out.println( " ThreadCount = " + totalCount);
    }
}
