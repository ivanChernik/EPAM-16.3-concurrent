package by.epam.tc.concurrents_example.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/*Работники должны замесить раствор, для этого нужно 4 компонента, которые 
 * находятся в разных частях стройки(на разном расстоянии и их надо принести): песок, 
 * цемент, щебень и вода. Нам надо принести все компоненты и замешать.У нас есть  4 работника
 * для переноса
 * и тогда время наполнения емкости, готовой к замесу,
 * становится равным времени выполнения максимальной операции.
 * 
 * */

public class Employee extends Thread {

	private final Random random = new Random();
	private CountDownLatch cdl;
	private String name;

	private final static Logger LOGGER = LogManager.getRootLogger();

	public Employee(CountDownLatch cdl, String name) {
		this.cdl = cdl;
		this.name = name;
	}

	@Override
	public void run() {

		LOGGER.debug(name + " working...");
		try {
			Thread.sleep(random.nextInt(500) + 500);
		} catch (InterruptedException e) {
			LOGGER.error(e);
		}
		cdl.countDown();

	}

}
