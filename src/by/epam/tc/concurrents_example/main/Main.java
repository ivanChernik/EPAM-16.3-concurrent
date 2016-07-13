package by.epam.tc.concurrents_example.main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		while (true) {
			System.out.println("Enter 1. SEMAPHORE");
			System.out.println(" 2. COUNTDOWNLATCH");
			System.out.println(" 3. CYCLICBARRIER");
			System.out.println(" 4. EXCHANGER");
			System.out.println(" 5. PHASER");
			System.out.println(" 6. LOCK");
			System.out.println(" 7. CONDITION");
			System.out.println(" 8. READWRITELOCK");

			Scanner scanner = new Scanner(System.in);

			if (scanner.hasNext("EXIT"))
				break;

			ExampleName exampleName = ExampleName.valueOf(scanner.nextLine());
			switch (exampleName) {
			case SEMAPHORE:
				/*
				 * У работников есть одна общая тележка, один загружает ее,
				 * другой выгружает, семафор блокирует тележку когда она занята
				 * одним из работников.
				 */
				ExampleHelper.doSemaphoreExample();
				break;

			case COUNTDOWNLATCH:
				/*
				 * Работники должны замесить раствор, для этого нужно 4
				 * компонента, которые находятся в разных частях стройки(на
				 * разном расстоянии и их надо принести): песок, цемент, щебень
				 * и вода. Нам надо принести все компоненты и замешать.У нас
				 * есть 4 работника для переноса и тогда время наполнения
				 * емкости, готовой к замесу, становится равным времени
				 * выполнения максимальной операции (т е при последовательном
				 * выполнении - время1+время2+время3+время4 при CountDownLatch -
				 * макс время из 4).
				 */
				ExampleHelper.doCountDownLatchExample();
				break;

			case CYCLICBARRIER:
				/*
				 * В офисах содержится определенное количество принтеров,
				 * которые надо запрвлять. Когда заканчивается чернила надо
				 * вызывать заправщика принтера из сервиса Т к один принтер
				 * заправлять нерезонно, заправщик ждет когда опустеет 3
				 * принтера и едет запрвлять
				 */
				ExampleHelper.doCyclicBarrierExample();
				break;

			case EXCHANGER:
				/*
				 * У нас есть два класса. Один создает строку и отправляет ее
				 * Другой принимет созданную и отправлет пустую
				 */
				ExampleHelper.doExchangerExample();
				break;

			case PHASER:
				/*
				 * потоки выполняют действия , состовляющие фазу. Cинхронизатор
				 * Phaser ждет, пока все стороны(2 потока) не завершат
				 * выполнение фазы.
				 */
				ExampleHelper.doPhaserExample();
				break;

			case LOCK:
				/*
				 * У работников есть одна общая тележка, один загружает ее,
				 * другой выгружает, Lock блокирует тележку когда она занята
				 * одним из работников.
				 */
				ExampleHelper.doLockExample();
				break;

			case CONDITION:
				/*
				 * Задача Потребитель - производитель. Потребитель может взять
				 * число, производитель может взять мы ограничиваем размерность
				 * очереди 10 числами. Когда очередь полна производитель отдает
				 * блокировку и ждет signalAll тоже самое с потребителем только
				 * когда очередб пуста
				 */
				ExampleHelper.doConditionExample();
				break;

			case READWRITELOCK:
				/*
				 * Синхронизированное дерево лочит операции записи и поиска
				 * (чтения) Принимает сроку и располагает на каждый символ на
				 * новом уровне на последнем уровне распалагает лист
				 */
				ExampleHelper.doReadWriteLockExample();
				break;

			}

		}
	}

}
