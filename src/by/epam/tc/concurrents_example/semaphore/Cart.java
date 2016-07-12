package by.epam.tc.concurrents_example.semaphore;

//общая тележка
public class Cart {
	private static int weight = 0;

	public static void load() {
		weight--;
	}

	public static void release() {
		weight++;
	}

	public static int getWeight() {
		return weight;
	}

}
