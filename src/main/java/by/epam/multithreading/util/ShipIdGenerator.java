package by.epam.multithreading.util;

public class ShipIdGenerator {
    private static long id;

    private ShipIdGenerator(){}

    public static long generateId(){
        return id++;
    }
}
