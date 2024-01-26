package thread;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input Initial Work Number: ");
		int numberOfWork = sc.nextInt();
		
		System.out.print("Input Initial Consumer Number: ");
		int numberOfConsumer = sc.nextInt(); 
		
		BlockingQueue<Work> queue = new LinkedBlockingQueue<>();
		
		Runnable producer = new Producer(queue, numberOfWork);
		Thread producerThread = new Thread(producer);
		producerThread.start();
		
		for (int i = 0; i < numberOfConsumer; i++) {
			Runnable consumer = new Consumer(queue);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
		}

	}

}
