package by.epam.multithreading.parser.impl;

import by.epam.multithreading.parser.ShipParser;

import java.util.Arrays;
import java.util.List;

public class ShipParserImpl implements ShipParser {
    private static final String PARAMETERS_DELIMITER_REGEX = "\\s+";
    private static final String NUMBER_REGEX = "\\d+";


    @Override
    public List<Integer> parseShipParameters(String arguments) {
        List<Integer> parameters = Arrays
                .stream(arguments.split(PARAMETERS_DELIMITER_REGEX))
                .filter(s -> s.matches(NUMBER_REGEX))
                .map(Integer::parseInt).toList();
       return parameters;
    }
}
