package by.epam.tc.concurrents_example.condition;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Consumer extends Thread {
	private SharedResource sharedObject;
	private final static Logger LOGGER = LogManager.getRootLogger();

	public Consumer(SharedResource sharedObject) {
		super("CONSUMER");
		this.sharedObject = sharedObject;
	}

	@Override
	public void run() {
		try {
			sharedObject.get();
		} catch (InterruptedException e) {
			LOGGER.error(e);
		}
	}
}
