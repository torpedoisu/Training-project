package thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable{
	
	private final BlockingQueue<Work> queue;
	
	public Producer(BlockingQueue<Work> queue, int numberOfWork) {
		this.queue = queue;
		
		for (int i = 0; i < numberOfWork; i++) {
			createWork();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			createWork();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("=== (interrupted) While Producer Sleeping ===");
			}
		}
	
	}
	
	private void createWork() {
		try {
			Work work = new Work();
			queue.put(work);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("=== (interrupted) While Producer Creating Work ===");
		}
	}

}
