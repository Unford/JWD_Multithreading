package by.epam.multithreading.util;

public class BerthIdGenerator {
    private static long id;

    private BerthIdGenerator(){}

    public static long generateId(){
        return id++;
    }
}
