package by.epam.multithreading.reader.impl;

import by.epam.multithreading.exception.SeaportException;
import by.epam.multithreading.reader.ShipReader;
import by.epam.multithreading.validator.SeaportValidator;
import by.epam.multithreading.validator.impl.SeaportValidatorImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ShipReaderImpl implements ShipReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<String> readAllLines(String filename) throws SeaportException {
        SeaportValidator validator = SeaportValidatorImpl.getInstance();
        if (!validator.isValidFilepath(filename)){
            throw new SeaportException("File path is invalid: " + filename);
        }
        List<String> text;
        Path path = Paths.get(filename);
        try (BufferedReader br = Files.newBufferedReader(path)){
            text = br.lines().toList();
        } catch (IOException e) {
            logger.log(Level.ERROR,"Input error while reading file", e);
            throw new SeaportException("Input error while reading file", e);
        }
        logger.log(Level.INFO, "Read file {} is successful", path.getFileName());
        return text;
    }

}
