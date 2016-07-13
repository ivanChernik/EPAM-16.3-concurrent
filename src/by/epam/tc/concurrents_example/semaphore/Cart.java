package by.epam.tc.concurrents_example.semaphore;

//общая тачка для рабочих
public class Cart {
	private static int weight = 0;

	//выгрузить
	public static void release() {
		weight--;
	}

	//загрузить
	public static void load() {
		weight++;
	}

	public static int getWeight() {
		return weight;
	}

}
