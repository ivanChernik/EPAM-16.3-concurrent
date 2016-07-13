package by.epam.tc.concurrents_example.condition;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Producer extends Thread {
	private SharedResource sharedObject;
	private final static Logger LOGGER = LogManager.getRootLogger();

	public Producer(SharedResource sharedObject) {
		super("PRODUCER");
		this.sharedObject = sharedObject;
	}

	@Override
	public void run() {
		try {
			sharedObject.put();
		} catch (InterruptedException e) {
			LOGGER.error(e);
		}
	}
}
