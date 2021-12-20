package by.epam.multithreading.main;

import by.epam.multithreading.entity.Ship;
import by.epam.multithreading.exception.SeaportException;
import by.epam.multithreading.factory.ShipFactory;
import by.epam.multithreading.factory.impl.ShipFactoryImpl;
import by.epam.multithreading.parser.ShipParser;
import by.epam.multithreading.parser.impl.ShipParserImpl;
import by.epam.multithreading.reader.ShipReader;
import by.epam.multithreading.reader.impl.ShipReaderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws SeaportException {
        ShipReader reader = new ShipReaderImpl();
        List<String> lines = reader.readAllLines("input/example.txt");
        ShipParser parser = new ShipParserImpl();
        ShipFactory factory = new ShipFactoryImpl();
        List<Ship> ships = new ArrayList<>();
        for (String line : lines) {
           List<Integer> parameters = parser.parseShipParameters(line);
           Ship ship = factory.createShip(parameters);
           if (ship != null){
               ships.add(ship);
           }
        }
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (Ship ship : ships) {
            service.submit(ship);
        }
        service.shutdown();

    }
}
