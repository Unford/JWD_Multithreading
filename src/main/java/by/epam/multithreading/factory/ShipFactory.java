package by.epam.multithreading.factory;

import by.epam.multithreading.entity.Ship;

import java.util.List;

public interface ShipFactory {
    Ship createShip(List<Integer> parameters);
}
