package by.epam.multithreading.entity;

import by.epam.multithreading.state.ShipState;
import by.epam.multithreading.util.BerthIdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Berth {
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_CAPACITY = 100;
    private long berthId;

    public Berth(){
        berthId = BerthIdGenerator.generateId();
    }

    public long getBerthId() {
        return berthId;
    }

    public void unloadShip(Ship ship){
        ship.setState(ShipState.UNLOADING);
        AtomicInteger portCapacity = Seaport.getInstance().getCurrentVolume();
        logger.info("berth id {}: unload {} ship started: ship - current volume - {}, port capacity - {}",
                this.getBerthId(), ship.getShipId(), ship.getCurrentVolume(), portCapacity.get());
        try {
            while (portCapacity.get() < MAX_CAPACITY && ship.getCurrentVolume() > 0){
                portCapacity.incrementAndGet();
                ship.decrementCurrentVolume();
                TimeUnit.MILLISECONDS.sleep(300);
            }
        }catch (InterruptedException e) {
            logger.log(Level.ERROR, "interrupted exception while unloading ship {}", ship.getShipId(), e);
            Thread.currentThread().interrupt();
        }
        logger.info("berth id {}: unload {} ship finished: ship - current volume - {}, port capacity - {}",
               this.getBerthId(), ship.getShipId(), ship.getCurrentVolume(), portCapacity.get());
    }

    public void loadShip(Ship ship){
        ship.setState(ShipState.LOADING);
        AtomicInteger portCapacity = Seaport.getInstance().getCurrentVolume();
        logger.info("berth id {}: load {} ship started: ship capacity - {} port capacity - {}",
               this.getBerthId(), ship.getShipId(), ship.getCapacity(), portCapacity.get());
        try {
            while (portCapacity.get() > 0 && ship.getCurrentVolume() < ship.getCapacity()){
                portCapacity.getAndDecrement();
                ship.incrementCurrentVolume();
                TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "berth id {}: interrupted exception while loading ship {}",
                    this.getBerthId(), ship.getShipId(), e);
            Thread.currentThread().interrupt();
        }
        logger.info("berth id {}: load {} ship finished: ship currentVolume - {} port capacity - {}",
                this.getBerthId(), ship.getShipId(), ship.getCurrentVolume(), portCapacity.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Berth berth = (Berth) o;

        return berthId == berth.berthId;
    }

    @Override
    public int hashCode() {
        return (int) (berthId ^ (berthId >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Berth{");
        sb.append("berthId=").append(berthId);
        sb.append('}');
        return sb.toString();
    }
}
