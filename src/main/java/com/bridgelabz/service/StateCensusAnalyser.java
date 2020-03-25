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
import java.util.*;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;
    Map<Object, Object> censusCSVMap = new HashMap<Object, Object>();
    Map<Object, Object> stateCodeCSVMap = new HashMap<Object, Object>();

    public StateCensusAnalyser() {
    }

    //READING AND PRINTING DATA FROM STATE CENSUS CSV FILE
    public int loadCensusCSVData(String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVMap = csvBuilder.getCSVFileMap(reader, CSVStateCensus.class);
            return censusCSVMap.size();
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
            stateCodeCSVMap = csvBuilder.getCSVFileMap(reader, CSVStateCode.class);
            return stateCodeCSVMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
    }

    //GENERIC METHOD TO ITERATE THROUGH CSV FILE.
    private <E> int getCount(Iterator<E> iterator, E nameClass) {
        while (iterator.hasNext()) {
            nameClass = iterator.next();
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
        if (censusCSVMap == null || censusCSVMap.size() == 0)
            throw new stateCensusAnalyserException("No Census Data", stateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CSVStateCensus> censusCSVComparator = Comparator.comparing(census -> census.getState());
        this.sort(censusCSVComparator,censusCSVMap);
        Collection<Object> list1 =  censusCSVMap.values();
        String sortedStateCensusJson = new Gson().toJson(list1);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CODE
    public String getStateWiseSortedStateCode() throws stateCensusAnalyserException {
        if (stateCodeCSVMap == null || stateCodeCSVMap.size() == 0)
            throw new stateCensusAnalyserException("No State Data", stateCensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
        Comparator<CSVStateCode> stateCodeComparator = Comparator.comparing(census -> census.getStateCode());
        this.sort(stateCodeComparator,stateCodeCSVMap);
        Collection<Object> list2 =  stateCodeCSVMap.values();
        String sortedStateCensusJson = new Gson().toJson(list2);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT THE LIST IN ALPHABETICAL ORDER
    private <E> void sort(Comparator censusComparator, Map<Object, Object> CSVMap) {
        for (int iterate = 0; iterate < CSVMap.size() - 1; iterate++) {
            for (int innerIterate = 0; innerIterate < CSVMap.size() - iterate - 1; innerIterate++) {
                E census1 = (E) CSVMap.get(innerIterate);
                E census2 = (E) CSVMap.get(innerIterate + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    CSVMap.put(innerIterate, census2);
                    CSVMap.put(innerIterate + 1, census1);
                }
            }

        }
    }
}