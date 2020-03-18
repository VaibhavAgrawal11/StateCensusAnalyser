package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCSVData(String getPath) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            //WITH THE HELP OF POJO FILE ITERATING AND PRINTING THE  CONTENTS OF THE CSV FILE.
            CsvToBean csvStateCensuses = new CsvToBeanBuilder(reader)
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
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        }catch (RuntimeException e){
            throw new StateCensusAnalyserException("Number of data fields does not match number of headers.",StateCensusAnalyserException.ExceptionType.INVALID_HEADER_COUNT);
        }
        return count;
    }

    public void getFileExtension(File file) throws StateCensusAnalyserException {
        boolean result = false;
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            result = fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv");
        }
        if (!result)
            throw new StateCensusAnalyserException("Wrong file type", StateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
    }

    public void checkDelimiter(File file) throws StateCensusAnalyserException {
        Pattern pattern = Pattern.compile("^[\\w ]*,[\\w ]*,[\\w ]*,[\\w ]*");
        BufferedReader br = null;
        boolean delimiterResult = true;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                delimiterResult = pattern.matcher(line).matches();
                if (!delimiterResult) {
                    throw new StateCensusAnalyserException("Invalid Delimiter found", StateCensusAnalyserException.ExceptionType.INVALID_DELIMITER);
                }
            }
        } catch (FileNotFoundException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE_EXIST);
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        }
    }
}

