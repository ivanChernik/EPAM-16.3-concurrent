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

import by.epam.tc.concurrents_example.countdownlatch.Employee;
import by.epam.tc.concurrents_example.cyclicbarrier.Printer;
import by.epam.tc.concurrents_example.cyclicbarrier.ServiceMan;
import by.epam.tc.concurrents_example.exchanger.MakeString;
import by.epam.tc.concurrents_example.exchanger.UseString;
import by.epam.tc.concurrents_example.lock.Workman;
import by.epam.tc.concurrents_example.phaser.PhaseThread;
import by.epam.tc.concurrents_example.readwritelock.SafeListTestThread;
import by.epam.tc.concurrents_example.readwritelock.ThreadSafeArrayList;
import by.epam.tc.concurrents_example.semaphore.Worker;

public class Main {

	private final static Logger LOGGER = LogManager.getRootLogger();

	public static void main(String[] args) {

		ExampleName exampleName = ExampleName.valueOf("READWRITELOCK");
		switch (exampleName) {
		case SEMAPHORE:
			// example with Semaphore, detailed comment in class Worker
			doSemaphoreExample();
			break;

		case COUNTDOWNLATCH:
			// example with CountDownLatch, detailed comment in class Employee
			doCountDownLatchExample();
			break;

		case CYCLICBARRIER:
			// example with CyclicBarrier, detailed comment in class ServiceMan
			doCyclicBarrierExample();
			break;

		case EXCHANGER:
			// example with Exchanger, detailed comment in method
			// doExchangerExample()
			doExchangerExample();
			break;

		case PHASER:
			// example with Phaser, detailed comment in class PhaserThread and
			// in method doPhaserExample()
			doPhaserExample();
			break;

		case LOCK:
			// example with Phaser, detailed comment in class Workman
			doLockExample();
			break;

		case READWRITELOCK:
			// example with ReadWriteLock, detailed comment in class
			// ThreadSafeArrayList
			doReadWriteLockExample();
			break;

		}

	}

	private static void doSemaphoreExample() {
		// задаем количсество потоков которое может пропустить семафор
		Semaphore semaphore = new Semaphore(1);
		new Worker(semaphore, "Adder", true).start();
		new Worker(semaphore, "Reducer", false).start();
	}

	private static void doCountDownLatchExample() {

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

	}

	private static void doCyclicBarrierExample() {
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

	private static void doExchangerExample() {
		// обмениваетс€ строками
		Exchanger<String> exgr = new Exchanger<String>();

		// сначало ждем строку пока создастьс€ потом отправлем туда пустую а
		// принимаем
		// со значением
		new Thread(new UseString(exgr)).start();
		new Thread(new MakeString(exgr)).start();
	}

	private static void doPhaserExample() {
		// 2 потока дл€ выполнени€ + главный поток
		Phaser phaser = new Phaser(3);

		// потоки выполн€ют действи€ , состовл€ющие фазу.
		// Cинхронизатор Phaser ждет, пока все стороны(2 потока) не завершат
		// выполнение
		// фазы.
		new Thread(new PhaseThread(phaser, "PhaseThread 1")).start();
		new Thread(new PhaseThread(phaser, "PhaseThread 2")).start();

		// ждем завершени€ фазы 0
		int phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		LOGGER.debug("‘аза " + phase + " завершена");
		// ждем завершени€ фазы 1
		phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		LOGGER.debug("‘аза " + phase + " завершена");

		// ждем завершени€ фазы 2
		phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		LOGGER.debug("‘аза " + phase + " завершена");

		phaser.arriveAndDeregister();
	}

	private static void doLockExample() {
		// задаем количсество потоков которое может пропустить семафор
		Lock lock = new ReentrantLock();
		new Workman(lock, "Adder", true).start();
		new Workman(lock, "Reducer", false).start();
	}

	private static void doReadWriteLockExample() {
		ThreadSafeArrayList<String> list = new ThreadSafeArrayList<String>();
		for(int i =0; i< 10; i++){
			list.set(Integer.toString(i));
		}
		
		LOGGER.debug("-------------------------");
		
		new SafeListTestThread(list).start();
		new SafeListTestThread(list).start();
		new SafeListTestThread(list).start();
		new SafeListTestThread(list).start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOGGER.debug("-------------------------");
		
		for(int i = 0; i< list.getLenth(); i++){
			list.get((i));
		}
	}
}
