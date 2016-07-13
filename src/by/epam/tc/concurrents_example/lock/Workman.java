package by.epam.tc.concurrents_example.lock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epam.tc.concurrents_example.semaphore.Cart;


public class Workman extends Thread {

	private Lock lock;
	private String workerName;
	private volatile boolean isAdder;
	private final static Logger LOGGER = LogManager.getRootLogger();

	public Workman(Lock lock, String workerName, boolean isAdder) {
		this.lock = lock;
		this.workerName = workerName;
		this.isAdder = isAdder;
	}

	@Override
	public void run() {
		LOGGER.debug(workerName + " started working...");
		LOGGER.debug(workerName + " waiting for cart...");

		lock.lock();
		try {
			for (int i = 0; i < 5; i++) {
				if (isAdder)
					//
					Cart.release();
				else
					Cart.load();

				LOGGER.debug(workerName + " changed weight to: "
						+ Cart.getWeight());
				try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					LOGGER.error(e);
				}
			}
			LOGGER.debug(workerName + " finished working with cart...");
		} finally {
			lock.unlock();
		}
	}

}


