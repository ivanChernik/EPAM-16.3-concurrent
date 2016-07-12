package by.epam.tc.concurrents_example.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Printer implements Runnable {
	private String name;
	private final Random rand;
	private ServiceMan serviceMan;
	private volatile boolean stopThread;
	
	private final static Logger LOGGER = LogManager.getRootLogger();

	public Printer(ServiceMan serviceMan, String name) {
		this.name = name;
		this.serviceMan = serviceMan;
		this.rand = new Random();
	}

	public void run() {
		try {
			while (!stopThread) {
				TimeUnit.SECONDS.sleep(rand.nextInt(10));
				LOGGER.debug(name + " is empty");
				serviceMan.recharge(name);
			}
		} catch (InterruptedException e) {
			LOGGER.error(e);
		}
	}
	
	public void stopThread() {
        stopThread = true;
    }
}
