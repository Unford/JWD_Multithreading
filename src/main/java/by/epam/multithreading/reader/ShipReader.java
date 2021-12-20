package by.epam.multithreading.reader;

import by.epam.multithreading.exception.SeaportException;

import java.util.List;

public interface ShipReader {
    List<String> readAllLines(String filepath) throws SeaportException;
}
