package com.pcp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Main {
	private static ExecutorService executor = Executors.newFixedThreadPool(Settings.THREAD_POOL_THREAD_COUNT);

	public static void main(String[] args) {
		try {
			Settings.WAITING.acquire(Settings.BUS_CAPACITY);
			Settings.SEATS.acquire(Settings.BUS_CAPACITY);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		int random = Settings.BUS_CAPACITY / 2 + (int) (Math.random() * (3 * Settings.BUS_CAPACITY + 1));
		System.out.println("Rider count: " + random);
		for (int i = 1; i <= random; i++) {
			final int id = i;
			Runnable riderRunnable = new Runnable() {
				public void run() {
					Rider rider = new Rider(id);
					rider.arrive();
				}
			};
			executor.execute(riderRunnable);
		}
		for (int i = 1; i <= Settings.BUS_COUNT; i++) {
			new Bus(i).arrive();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Settings.waitingCount = Settings.waitingCount - Settings.boardCount;
			Settings.boardCount = 0;
		}
		executor.shutdown();
		System.out.println("Exiting........");

	}

}
