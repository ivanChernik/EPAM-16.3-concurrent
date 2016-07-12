package by.epam.tc.concurrents_example.phaser;

import java.util.concurrent.Phaser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PhaseThread implements Runnable {

	private Phaser phaser;
	private String name;

	private final static Logger LOGGER = LogManager.getRootLogger();
	
	public PhaseThread(Phaser p, String n) {
		this.phaser = p;
		this.name = n;
	}

	public void run() {

		LOGGER.debug(name + " выполняет фазу " + phaser.getPhase());
		phaser.arriveAndAwaitAdvance(); // сообщаем, что первая фаза достигнута

		LOGGER.debug(name + " выполняет фазу " + phaser.getPhase());
		phaser.arriveAndAwaitAdvance(); // сообщаем, что вторая фаза достигнута

		LOGGER.debug(name + " выполняет фазу " + phaser.getPhase());
		phaser.arriveAndDeregister(); // сообщаем о завершении фаз и удаляем с
										// регистрации объекты
	}
}