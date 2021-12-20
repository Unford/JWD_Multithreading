package by.epam.multithreading.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Seaport {
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_BERTHS_SIZE = 6;

    private static Seaport instance;

    private static final AtomicBoolean initCheck = new AtomicBoolean(false);
    private static final ReentrantLock initLock = new ReentrantLock(true);
    private static final ReentrantLock berthLock = new ReentrantLock(true);

    private final Deque<Berth> availableBerths = new ArrayDeque<>();
    private final Deque<Berth> usedBerths = new ArrayDeque<>();
    private final Condition berthCondition = berthLock.newCondition();

    private final AtomicInteger currentVolume = new AtomicInteger();


    private Seaport(){
        for (int i = 0; i < MAX_BERTHS_SIZE; i++) {
            availableBerths.add(new Berth());
        }
    }

    public static Seaport getInstance(){
        if (!initCheck.get()){
            try {
                initLock.lock();
                if (instance == null){
                    instance = new Seaport();
                    initCheck.set(true);
                }
            } finally {
                initLock.unlock();
            }
        }
        return instance;
    }

    public Berth takeBerth(){
        Berth berth = null;
        try {
            berthLock.lock();
            while (availableBerths.isEmpty()){
                berthCondition.await();
            }
            berth = availableBerths.removeFirst();
            usedBerths.offer(berth);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        } finally {
            berthLock.unlock();
        }
        logger.log(Level.INFO, "taken: {}", berth);
        return berth;
    }

    public void releaseBerth(Berth berth){
        logger.log(Level.INFO, "release: {}", berth);
        try {
            berthLock.lock();
            availableBerths.add(berth);
            usedBerths.remove(berth);
            berthCondition.signal();
        }finally {
            berthLock.unlock();
        }
    }

    public AtomicInteger getCurrentVolume() {
        return currentVolume;
    }

}
