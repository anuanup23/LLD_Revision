package src.blockVsWait;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class provides the functionality to lock an object.
 * Its package-private since it's not designed to be a generic lock-by-key solution,
 * and should be used only for serialize the calls made to ARC.
 *
 * @param <E>
 */

class LockByKey<E> {
    private final Set<E> lockedKeys;


    public LockByKey() {
        // Unbounded since the use case will have maximum items equal to the number of DAX IO thread.
        // Also for the current use case we cannot remove key from the set unless lock is release,
        // since it will allow multiple calls for same key to go through.
        this.lockedKeys = ConcurrentHashMap.newKeySet();
    }

    /**
     * This method tries to acquire a lock on provided object. If the object is already locked by another thread
     * then it will go into wait/park state. Thread T1 with accessKeyID-1 will lock the set create entry in the set
     * and release the synchronized lock, thread T2 with accessKeyID-1 will lock the set but since lockKeys.add(key)
     * will return false, thread T2 will go into WAITING state [and not in BLOCKING state], this will release
     * synchronized lock. Hence, another thread T3 with accessKeyID-2 will be able to acquire lock on set and create
     * entry in it.
     * Once thread T1 release lock on accessKeyID-1, thread T2 state will be moved from WAITING to BLOCKING and
     * will compete for the lock with other threads on accessKeyID-1 is any.
     *
     * @param key
     * @throws InterruptedException
     */
    public void lock(final E key) throws InterruptedException {
        synchronized (lockedKeys) {
            while (!lockedKeys.add(key)) {
                lockedKeys.wait();
            }
        }
    }

    /**
     * This method releases the lock on the object. After releasing the lock it will notify all the waiting threads
     * so that they can try to acquire lock on the object.
     *
     * @param key
     */
    public void unlock(final E key) {
        synchronized (lockedKeys) {
            lockedKeys.remove(key);
            lockedKeys.notifyAll();
        }
    }
}
