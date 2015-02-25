package com.pcp;

import java.util.concurrent.Semaphore;

public final class Settings {
	public static final int BUS_CAPACITY = 50;
	public static final int THREAD_POOL_THREAD_COUNT = 50;
	public static final int BUS_COUNT = 3;

	public static final Semaphore WAITING = new Semaphore(BUS_CAPACITY);
	public static final Semaphore SEATS = new Semaphore(BUS_CAPACITY);

	public static final Semaphore BUS = new Semaphore(0);
	public static final Semaphore MUTEX = new Semaphore(1);

	public static int riderCount = 0;
	public static int boardCount = 0;
	public static int waitingCount = 0;

}
