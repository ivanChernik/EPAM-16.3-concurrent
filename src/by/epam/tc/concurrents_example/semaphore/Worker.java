package by.epam.tc.concurrents_example.semaphore;

import java.util.concurrent.Semaphore;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//работник
public class Worker extends Thread {

    private Semaphore semaphore;
    private String workerName;
    //проверяет рабочий грузит или выгружает
    private boolean isAdder;
    private final static Logger LOGGER = LogManager.getRootLogger();
    
    public Worker(Semaphore semaphore, String workerName, boolean isAdder) {
        this.semaphore = semaphore;
        this.workerName = workerName;
        this.isAdder = isAdder;
    }

    @Override
    public void run() {
    	LOGGER.debug(workerName + " started working...");
        try {
        	LOGGER.debug(workerName + " waiting for cart...");
            semaphore.acquire();
            LOGGER.debug(workerName + " got access to cart...");
            for (int i = 0 ; i < 5 ; i++) {
                if (isAdder)
                	//загрузить
                	Cart.load();
                else
                	//выгрузить
                	Cart.release();
                    
                
                LOGGER.debug(workerName + " changed weight to: " + Cart.getWeight());
                Thread.sleep(10L);
            }
            LOGGER.debug(workerName + " finished working with cart...");
        } catch (Exception e) {
        	LOGGER.error(e);
        } finally {
            semaphore.release(); 
        }
    }
}