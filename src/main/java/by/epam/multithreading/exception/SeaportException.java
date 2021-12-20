package by.epam.multithreading.exception;

public class SeaportException extends Exception{

    public SeaportException() {
        super();
    }

    public SeaportException(String message) {
        super(message);
    }

    public SeaportException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeaportException(Throwable cause) {
        super(cause);
    }
}
