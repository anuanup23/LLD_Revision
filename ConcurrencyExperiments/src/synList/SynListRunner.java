package src.synList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynListRunner {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        // Thread 1: Add elements to the list
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronizedList.add(i);
                try {
                    Thread.sleep(10); // Simulating some processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Iterate over the list
        Thread thread2 = new Thread(() -> {
            synchronized (synchronizedList) {
                for (Integer num : synchronizedList) {
                    System.out.println(num);
                    try {
                        Thread.sleep(10); // Simulating some processing time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread2.start();
        thread1.start();
        thread1.join();
//        thread2.join();
        System.out.println("   ------    ");
        for (Integer num : synchronizedList) {
            System.out.println(num);
        }

    }
}
