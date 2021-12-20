package by.epam.multithreading.validator.impl;

import by.epam.multithreading.validator.SeaportValidator;

import java.io.File;

public class SeaportValidatorImpl implements SeaportValidator {
    private static SeaportValidatorImpl instance;

    private SeaportValidatorImpl(){}

    public static SeaportValidatorImpl getInstance(){
        if (instance == null){
            instance = new SeaportValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isValidFilepath(String filepath) {
        boolean isValid = false;
        if (filepath != null && !filepath.isBlank()){
            File file = new File(filepath);
            isValid = file.exists();
        }
        return isValid;
    }


}
