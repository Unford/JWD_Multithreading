package by.epam.multithreading.entity;

import by.epam.multithreading.state.ShipState;
import by.epam.multithreading.util.ShipIdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.multithreading.state.ShipState.*;

public class Ship implements Runnable{
    private static final Logger logger = LogManager.getLogger();

    private final long shipId;
    private final int capacity;
    private int currentVolume;
    private ShipState state;

    public Ship(int capacity, int currentVolume) {
        this.shipId = ShipIdGenerator.generateId();
        this.capacity = capacity;
        this.currentVolume = currentVolume;
        this.state = ARRIVING;
    }

    public long getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void incrementCurrentVolume() {
        ++currentVolume;
    }

    public void decrementCurrentVolume() {
        --currentVolume;
    }
    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    @Override
    public void run() {
            Seaport port = Seaport.getInstance();
            state.next(this);
            Berth berth = port.takeBerth();
            berth.unloadShip(this);
            berth.loadShip(this);
            state.next(this);
            port.releaseBerth(berth);
            logger.log(Level.INFO, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (shipId != ship.shipId) return false;
        if (capacity != ship.capacity) return false;
        if (currentVolume != ship.currentVolume) return false;
        return state == ship.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (shipId ^ (shipId >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + currentVolume;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipId=").append(shipId);
        sb.append(", capacity=").append(capacity);
        sb.append(", currentVolume=").append(currentVolume);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
