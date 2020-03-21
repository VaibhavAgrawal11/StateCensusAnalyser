package com.bridgelabz.service;

import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStateCode;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;
import com.bridgelabz.exception.stateCensusAnalyserException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;

    //READING AND PRINTING DATA FROM STATE CENSUS CSV FILE
    public int loadCensusCSVData(String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> censusCSVList;
            censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
            System.out.println(censusCSVList);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
    }

    //READING AND PRINTING DATA FROM STATE CODE CSV FILE
    public int loadStateCodeData(String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCode> censusCSVList;
            censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCode.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
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
    public void getFileExtension(File file) throws stateCensusAnalyserException {
        boolean result = false;
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            result = fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv");
        }
        if (!result)
            throw new stateCensusAnalyserException("Wrong file type",
                    stateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
    }

    //METHOD TO CHECK DELIMITER IN CSV FILE
    public void checkDelimiter(File file) throws stateCensusAnalyserException, CSVBuilderException {
        Pattern pattern = Pattern.compile("^[\\w ]*,[\\w ]*,[\\w ]*,[\\w ]*");
        BufferedReader br = null;
        boolean delimiterResult = true;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                delimiterResult = pattern.matcher(line).matches();
                if (!delimiterResult) {
                    throw new stateCensusAnalyserException("Invalid Delimiter found",
                            stateCensusAnalyserException.ExceptionType.INVALID_DELIMITER);
                }
            }
        } catch (FileNotFoundException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.NO_SUCH_FILE_EXIST);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        }
    }
}