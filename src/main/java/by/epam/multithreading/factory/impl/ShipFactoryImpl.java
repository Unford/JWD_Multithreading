package by.epam.multithreading.factory.impl;

import by.epam.multithreading.entity.Ship;
import by.epam.multithreading.factory.ShipFactory;

import java.util.List;

public class ShipFactoryImpl implements ShipFactory {
    private static final int MIN_PARAMETERS_SIZE = 2;

    @Override
    public Ship createShip(List<Integer> parameters) {
        Ship ship = null;
        if (parameters != null && parameters.size() >= MIN_PARAMETERS_SIZE){

            int capacity = parameters.stream().mapToInt(Integer::intValue).max().getAsInt();
            int currentVolume = parameters.stream().mapToInt(Integer::intValue).min().getAsInt();
            ship = new Ship(capacity, currentVolume);
        }
        return ship;
    }
}
