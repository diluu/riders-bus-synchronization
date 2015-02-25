package com.pcp;

public final class Bus {

	private int id;

	public Bus(int id) {
		this.id = id;
	}

	public synchronized void arrive() {
		try {
			System.out.println("@Bus____ARRVING_Bus " + id);
			Settings.WAITING.release(Settings.BUS_CAPACITY);
			System.out.println("@Bus____STOPPED_WAITING");
			Thread.sleep(100);
			int waitingNotAcquired = Settings.BUS_CAPACITY - Settings.boardCount;
			if (waitingNotAcquired > 0) {
				Settings.WAITING.acquire(waitingNotAcquired);
			}

			Settings.SEATS.release(Settings.BUS_CAPACITY);
			System.out.println("@Bus____SEATS_AVAILABLE");
			Settings.MUTEX.acquire(1);
			Thread.sleep(20);
			if (Settings.waitingCount == 0) {
				Settings.BUS.release(1);
				System.out.println("@Bus____LEAVE_ZERO_RIDERS_Bus " + id);
			}
			Settings.MUTEX.release(1);
			Settings.BUS.acquire(1);
			depart();

			System.out.println("@Bus____DEPARTED_Bus " + id);
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void depart() {
		System.out.println("@Bus_DEPARTING____Bus " + id + ",Rider count: " + Settings.boardCount);
	}

}
