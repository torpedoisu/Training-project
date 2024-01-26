package thread;

import java.util.concurrent.TimeUnit;

public class Work {
    private static int nextWorkNumber = 0;
    private final int WORK_NUMBER = nextWorkNumber;
	
	public Work() {
		nextWorkNumber++;
		System.out.println("Work" + WORK_NUMBER + " created");
	}
	
	public void execute() throws InterruptedException {
		// 1-3초 사이 임의의 시간
		long sleepTimeMillis = (long) ((Math.random() * 2000) + 1000);
		System.out.println("Consumer" + Thread.currentThread().getName() + " using Work " + WORK_NUMBER + ", executed sleep time: " + sleepTimeMillis + "ms");
		
		TimeUnit.MILLISECONDS.sleep(sleepTimeMillis);
	}
	
	public int getWorkNumber() {
		return WORK_NUMBER;
	}
}
