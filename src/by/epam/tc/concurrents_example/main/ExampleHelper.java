package by.epam.tc.concurrents_example.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epam.tc.concurrents_example.condition.Consumer;
import by.epam.tc.concurrents_example.condition.Producer;
import by.epam.tc.concurrents_example.condition.SharedResource;
import by.epam.tc.concurrents_example.countdownlatch.Employee;
import by.epam.tc.concurrents_example.cyclicbarrier.Printer;
import by.epam.tc.concurrents_example.cyclicbarrier.ServiceMan;
import by.epam.tc.concurrents_example.exchanger.MakeString;
import by.epam.tc.concurrents_example.exchanger.UseString;
import by.epam.tc.concurrents_example.lock.Workman;
import by.epam.tc.concurrents_example.phaser.PhaseThread;
import by.epam.tc.concurrents_example.readwritelock.ThreadsafeTree;
import by.epam.tc.concurrents_example.semaphore.Worker;

public class ExampleHelper {
	
	private final static Logger LOGGER = LogManager.getRootLogger();
	
	public static void doSemaphoreExample() {
		// задаем количсество потоков которое может пропустить семафор
		Semaphore semaphore = new Semaphore(1);
		new Worker(semaphore, "Adder", true).start();
		new Worker(semaphore, "Reducer", false).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void doCountDownLatchExample() {
		//делаем ограничение на 4 потока
		CountDownLatch latch = new CountDownLatch(4);

		new Employee(latch, "Sand").start();
		new Employee(latch, "Cement").start();
		new Employee(latch, "Water").start();
		new Employee(latch, "Breakstone").start();

		LOGGER.debug("Waiting for all workers");
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.debug("All workers finished. Now we can shake.");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void doCyclicBarrierExample() {
		// ограничение на 3 принтера
		ServiceMan serviceMan = new ServiceMan(3);

		List<Printer> listPrinters = new ArrayList<Printer>();
		Printer printer = null;
		for (int i = 0; i < 6; i++) {
			printer = new Printer(serviceMan, "Printer" + (i + 1));
			listPrinters.add(printer);
			new Thread(printer).start();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			LOGGER.error(e);
		}

		printer = null;
		for (int i = 0; i < 6; i++) {
			printer = listPrinters.get(i);
			printer.stopThread();
		}

	}

	public static void doExchangerExample() {
		// обменивается строками
		Exchanger<String> exgr = new Exchanger<String>();

		
		new Thread(new UseString(exgr)).start();
		new Thread(new MakeString(exgr)).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void doPhaserExample() {
		// 2 потока для выполнения + главный поток
		Phaser phaser = new Phaser(3);
		new Thread(new PhaseThread(phaser, "PhaseThread 1")).start();
		new Thread(new PhaseThread(phaser, "PhaseThread 2")).start();

		// ждем завершения фазы 0
		int phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		LOGGER.debug("Фаза " + phase + " завершена");
		// ждем завершения фазы 1
		phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		LOGGER.debug("Фаза " + phase + " завершена");

		// ждем завершения фазы 2
		phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		LOGGER.debug("Фаза " + phase + " завершена");

		phaser.arriveAndDeregister();
	}

	public static void doLockExample() {
		// задаем количсество потоков которое может пропустить семафор
		Lock lock = new ReentrantLock();
		new Workman(lock, "Adder", true).start();
		new Workman(lock, "Reducer", false).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void doConditionExample() {
		// создаем разделяемый ресурс очередь числами
		SharedResource sharedObject = new SharedResource();
		// creating producer and consumer threads 
		
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Producer(sharedObject).start(); 
		new Consumer(sharedObject).start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}

	public static void doReadWriteLockExample() {
		ThreadsafeTree tree = new ThreadsafeTree();
		tree.put("первый");
		tree.put("второй");
		tree.put("третий");
		tree.put("четвертый");
		LOGGER.debug("found: "+tree.find("третий"));
		tree.printSorted();
	}

}
