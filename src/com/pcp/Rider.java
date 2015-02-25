package com.pcp;

public final class Rider {

	private int id;

	public Rider(int id) {
		this.id = id;
	}

	public synchronized void arrive() {
		try {
			System.out.println("@Rider_WAITING____Rider " + id);
			Settings.MUTEX.acquire(1);
			Settings.waitingCount++;
			Settings.MUTEX.release(1);

			Settings.WAITING.acquire(1);

			System.out.println("@Rider_STOPED_WAITING____Rider " + id);
			Settings.MUTEX.acquire(1);
			Settings.riderCount++;
			Settings.boardCount++;
			Settings.MUTEX.release(1);
			Settings.SEATS.acquire(1);
			boardBus();

			Settings.MUTEX.acquire(1);
			Settings.riderCount--;
			Settings.MUTEX.release(1);
			if (Settings.riderCount == 0) {
				Settings.BUS.release(1);
				System.out.println("@Rider_LAST_TO_GET_IN____Rider: " + id);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void boardBus() {
		System.out.println("@Rider_BOARDED____Rider " + id);
	}

	public int getId() {
		return id;
	}

}
