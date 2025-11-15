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

    @Override
    public DummyObjectWithVersion call() throws Exception {

        try {
            accessLock.lock(dummyObject);

            // business logic
            dummyObject.incrementVersion();

            AtomicInteger currentThreadCount =
                    WaitVsBlockRunner.threadCount.getOrDefault(
                            Thread.currentThread().getName(),
                            new AtomicInteger(0)
                    );

            WaitVsBlockRunner.threadCount.put(
                    Thread.currentThread().getName(),
                    new AtomicInteger(currentThreadCount.incrementAndGet())
            );

            Thread.sleep(200);

            return this.dummyObject;

        } catch (Exception e) {
            // EARLY LOGGING
            System.err.println(
                    "ERROR in AccessWorker (thread=" + Thread.currentThread().getName()
                            + ", key=" + dummyObject + "): " + e
            );
            e.printStackTrace(System.err);

            // rethrow so Future.get() also sees it
            throw e;

        } finally {
            // ALWAYS unlock even if exception happens
            try {
                accessLock.unlock(dummyObject);
            } catch (Exception unlockError) {
                System.err.println(
                        "UNLOCK FAILURE in thread=" + Thread.currentThread().getName()
                                + " for key=" + dummyObject + ": " + unlockError
                );
                unlockError.printStackTrace(System.err);
                throw unlockError;
            }
        }
    }
}
