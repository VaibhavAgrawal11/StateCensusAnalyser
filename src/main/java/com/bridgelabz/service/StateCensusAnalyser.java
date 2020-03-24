package com.bridgelabz.service;

import com.bridgelabz.exception.stateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStateCode;
import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;
    List censusCSVList;

    //READING AND PRINTING DATA FROM STATE CENSUS CSV FILE
    public int loadCensusCSVData(String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
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

    //METHOD TO SORT STATE NAME
    public String getStateWiseSortedCensusData() throws stateCensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0)
            throw new stateCensusAnalyserException("No Census Data", stateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CSVStateCensus> censusCSVComparator = Comparator.comparing(census -> census.getState());
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CODE
    public String getStateWiseSortedStateCode() throws stateCensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0)
            throw new stateCensusAnalyserException("No State Data", stateCensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
        Comparator<CSVStateCode> censusCSVComparator = Comparator.comparing(census -> census.getStateCode());
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT THE LIST IN ALPHABETICAL ORDER
    private <E> void sort(Comparator censusComparator) {
        for (int iterate = 0; iterate < censusCSVList.size() - 1; iterate++) {
            for (int innerIterate = 0; innerIterate < censusCSVList.size() - iterate - 1; innerIterate++) {
                E census1 = (E) censusCSVList.get(innerIterate);
                E census2 = (E) censusCSVList.get(innerIterate + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(innerIterate, census2);
                    censusCSVList.set(innerIterate + 1, census1);
                }
            }

        }
    }
}