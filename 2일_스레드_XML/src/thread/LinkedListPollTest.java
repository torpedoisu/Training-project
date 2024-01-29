package thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedListPollTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input Initial Work Number: ");
        int numberOfWork = sc.nextInt();

        System.out.print("Input Initial Consumer Number: ");
        int numberOfConsumer = sc.nextInt();

        Queue<Work> queue = new LinkedList<Work>();

        // Producer 스레드
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < numberOfWork; i++) {
                Work work = new Work();
                queue.add(work); // 작업을 큐에 추가
				System.out.println("Work" + work.getWorkNumber() + " enqueued");
            }
        });
        producerThread.start();


        for (int i = 0; i < numberOfConsumer; i++) {
            Thread consumerThread = new Thread(() -> {
                while (true) {
                    try {
                        Work work = queue.poll(); // 큐에서 작업을 가져옴
                        work.execute();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("=== (interrupted) While Consumer Using Work ===");
                    } catch (NullPointerException e) {
                    	try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e1) {
						}
                    	e.printStackTrace();
                    	System.out.println("Queue - " + queue);
                    	System.out.println("=== ConsumerThread-" + Thread.currentThread().getName() + ": [Error] No work left in queue ===");
                    }
                }
            });
            consumerThread.start();
        }
    }
}

