package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {
    int count = 0;
    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCSVData(String getPath) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath));
        ) {
            //WITH THE HELP OF POJO FILE ITERATING AND PRINTING THE  CONTENTS OF THE CSV FILE.
            CsvToBean<CSVStateCensus> csvStateCensuses = new CsvToBeanBuilder(reader)
                    .withType(CSVStateCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStateCensus> csvStateCensusIterator = csvStateCensuses.iterator();
            while (csvStateCensusIterator.hasNext()) {
                CSVStateCensus csvStateCensus = csvStateCensusIterator.next();
                System.out.println("State: " + csvStateCensus.getState());
                System.out.println("Area: " + csvStateCensus.getAreaInSqKm());
                System.out.println("Density: " + csvStateCensus.getDensityPerSqKm());
                System.out.println("Population: " + csvStateCensus.getPopulation());
                count++;
            }
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FALIED);
        }
        return count;
    }
}
