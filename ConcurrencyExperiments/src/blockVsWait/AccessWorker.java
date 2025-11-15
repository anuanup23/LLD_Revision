package src.blockVsWait;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessWorker implements Callable<DummyObjectWithVersion> {

    private final DummyObjectWithVersion dummyObject;
    private final LockByKey<DummyObjectWithVersion> accessLock;

    public AccessWorker(DummyObjectWithVersion dummyObject, LockByKey<DummyObjectWithVersion> accessLock) {
        this.dummyObject = dummyObject;
        this.accessLock = accessLock;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DummyObjectWithVersion call() throws Exception {
        accessLock.lock(dummyObject);
        dummyObject.incrementVersion();
        AtomicInteger currentThreadCount = WaitVsBlockRunner.threadCount.getOrDefault(Thread.currentThread().getName(), new AtomicInteger(0));
        WaitVsBlockRunner.threadCount.put(Thread.currentThread().getName(), new AtomicInteger(currentThreadCount.incrementAndGet()));
        Thread.sleep(200);
        accessLock.unlock(dummyObject);
        return this.dummyObject;
    }
}
