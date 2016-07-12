package by.epam.tc.concurrents_example.readwritelock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


//ReadWriteLock используем для блокироваки 
public class ThreadSafeArrayList<E> {

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();
	private final List<E> list = new ArrayList<>();

	private final static Logger LOGGER = LogManager.getRootLogger();
	
	public void set(E o)

	{
		writeLock.lock();
		try {
			list.add(o);
			LOGGER.debug("Adding element"+o+" by thread"
					+ Thread.currentThread().getName());
		} finally {
			writeLock.unlock();
		}
	}

	public E get(int i) {
		readLock.lock();
		try {
			LOGGER.debug("Printing elements "+list.get(i)+" by thread"
					+ Thread.currentThread().getName());
			return list.get(i);
		} finally {
			readLock.unlock();
		}
	}
	
	public int getLenth() {
		return list.size();
	}
}
