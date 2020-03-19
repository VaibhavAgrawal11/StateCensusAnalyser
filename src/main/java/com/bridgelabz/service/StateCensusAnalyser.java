package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStateCode;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;
import com.bridgelabz.utility.OpenCsvImpl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;

    //READING AND PRINTING DATA FROM STATE CENSUS CSV FILE
    public int loadCensusCSVData(String getPath) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvStateCensusIterator;
            csvStateCensusIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            CSVStateCensus csvStateCensus = null;
            count = getCount(csvStateCensusIterator, csvStateCensus);
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),
                    StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException("Number of data fields does not match number of headers.",
                    StateCensusAnalyserException.ExceptionType.INVALID_HEADER_COUNT);
        }
        return count;
    }

    //READING AND PRINTING DATA FROM STATE CODE CSV FILE
    public int loadStateCodeData(String getPath) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvStateCodeIterator;
            csvStateCodeIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCode.class);
            CSVStateCode csvStateCode = null;
            count = getCount(csvStateCodeIterator, csvStateCode);
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(),
                    StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException("Number of data fields does not match number of headers.",
                    StateCensusAnalyserException.ExceptionType.INVALID_HEADER_COUNT);
        }
        return count;
    }

    //GENERIC METHOD TO ITERATE THROUGH CSV FILE.
    private <E> int getCount(Iterator iterator, E nameClass) {
        while (iterator.hasNext()) {
            nameClass = (E) iterator.next();
            count++;
        }
        return count;
    }

    //METHOD TO CHECK FILE EXTENSION
    public void getFileExtension(File file) throws StateCensusAnalyserException {
        boolean result = false;
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            result = fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv");
        }
        if (!result)
            throw new StateCensusAnalyserException("Wrong file type",
                    StateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
    }

    //METHOD TO CHECK DELIMITER IN CSV FILE
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
                    throw new StateCensusAnalyserException("Invalid Delimiter found",
                            StateCensusAnalyserException.ExceptionType.INVALID_DELIMITER);
                }
            }
        } catch (FileNotFoundException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE_EXIST);
        } catch (IOException e) {
            throw new StateCensusAnalyserException(e.getMessage(), StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        }
    }
}