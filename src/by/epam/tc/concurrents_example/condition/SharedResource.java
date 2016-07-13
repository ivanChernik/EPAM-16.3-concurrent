package by.epam.tc.concurrents_example.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SharedResource {
	
	private final static Logger LOGGER = LogManager.getRootLogger();

	// producer consumer problem data
	private static final int CAPACITY = 10;
	private final Queue<Integer> queue = new LinkedList<Integer>();
	private final Random theRandom = new Random();
	
	// lock and condition variables
	private final Lock aLock = new ReentrantLock();
	private final Condition bufferNotFull = aLock.newCondition();
	private final Condition bufferNotEmpty = aLock.newCondition();

	
	//положить число
	public void put() throws InterruptedException {
		aLock.lock();
		try {
			while (queue.size() == CAPACITY) {
				LOGGER.debug(Thread.currentThread().getName()
						+ " : Buffer is full, waiting");
				//останавливается и ждет вызова signalAll в методе get() 
				bufferNotEmpty.await();
			}
			int number = theRandom.nextInt();
			boolean isAdded = queue.offer(number);
			if (isAdded) {
				LOGGER.debug("added into queue"+Thread
						.currentThread().getName()+ number);
				// signal consumer thread that, buffer has element now
				LOGGER.debug(Thread.currentThread().getName()
						+ " : Signalling that buffer is no more empty now");
				//нужен 
				bufferNotFull.signalAll();
			}
		} finally {
			aLock.unlock();
		}
	}

	//получить число
	public void get() throws InterruptedException {
		aLock.lock();
		try {
			while (queue.size() == 0) {
				LOGGER.debug(Thread.currentThread().getName()
						+ " : Buffer is empty, waiting");
				//останавливается и ждет вызова signalAll в методе put()
				bufferNotFull.await();
			}
			Integer value = queue.poll();
			if (value != null) {
				LOGGER.debug("%s consumed %d from queue %n" + Thread
						.currentThread().getName() + value);
				// signal producer thread that, buffer may be empty now
				LOGGER.debug(Thread.currentThread().getName()
						+ " : Signalling that buffer may be empty now");
				bufferNotEmpty.signalAll();
			}
		} finally {
			aLock.unlock();
		}
	}

}