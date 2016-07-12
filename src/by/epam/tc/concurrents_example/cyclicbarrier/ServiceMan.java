package by.epam.tc.concurrents_example.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/*
 * ¬ офисах содержитс€ определенное количество принтеров, которые надо запрвл€ть.
 *  огда заканчиваетс€ чернила надо вызывать заправщика принтера из сервиса
 * “ к один принтер заправл€ть нерезонно, заправщик ждет когда опутеет 3 принтера и
 *  едет запрвл€ть
 * 
 * 
 * */

public class ServiceMan {
	private CyclicBarrier queue;
	private List<String> inQueue;

	private final static Logger LOGGER = LogManager.getRootLogger();
	
	public ServiceMan(int hardWorking) {
		inQueue = new ArrayList<String>();
		queue = new CyclicBarrier(hardWorking, new Runnable() {
			@Override
			//очищает очередь когда количество принтеров превышает 3
			public void run() {
				LOGGER.debug("Filling " + inQueue);
				inQueue.clear();
			}
		});
	}

	public void recharge(String name) {
		try {
			inQueue.add(name);
			queue.await();
		} catch (InterruptedException e) {
			LOGGER.error(e);
		} catch (BrokenBarrierException e) {
			LOGGER.error(e);
		}
	}

}
