package by.epam.tc.concurrents_example.exchanger;

import java.util.concurrent.Exchanger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class UseString implements Runnable {
	private Exchanger<String> ex;
	private String str;
	
	private final static Logger LOGGER = LogManager.getRootLogger();

	public UseString(Exchanger<String> c) {
		ex = c;
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			try {
				//принимаем строку и отправляем новую строку
				str = ex.exchange(new String());
				LOGGER.debug("Got: " + str);
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
		}
	}
}
