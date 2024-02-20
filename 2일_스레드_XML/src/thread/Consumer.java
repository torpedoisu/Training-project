package thread;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

	private final BlockingQueue<Work> queue;

	public Consumer(BlockingQueue<Work> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Work work = queue.take();
				work.execute();
				System.out.println("Work" + work.getWorkNumber() + " executed");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("=== (interrupted) While Consumer Using Work ===");
			}
		}
		
	}


}
