package by.epam.tc.concurrents_example.exchanger;

import java.util.concurrent.Exchanger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



public class MakeString implements Runnable {
	private Exchanger<String> ex;
	private String str;
	
	private final static Logger LOGGER = LogManager.getRootLogger();

	public MakeString(Exchanger<String> c) {
		ex = c;
		str = new String();
	}

	public void run() {
		char ch = 'A';
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++)
				str += (char) ch++;
			try {
				//создаем строку и отправлем ее, получаем пустую строку
				str = ex.exchange(str);
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
		}
	}
}
