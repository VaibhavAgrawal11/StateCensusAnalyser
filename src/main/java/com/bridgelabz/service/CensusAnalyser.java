package com.bridgelabz.service;

import com.bridgelabz.adaptor.CensusAdapter;
import com.bridgelabz.adaptor.CensusAdapterFactory;
import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.utility.CSVBuilderException;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CensusAnalyser {
    Map<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();

    public CensusAnalyser() {
    }

    public enum COUNTRY {INDIA, US}

    private COUNTRY country;

    public CensusAnalyser(COUNTRY country) {
        this.country = country;
    }

    public enum SortingMode {AREA, STATE, STATECODE, DENSITY, POPULATION}

    //GENERIC METHOD LOADING EVERY FILE DATA
    public int loadCensusData(COUNTRY country, String... csvFilePath) throws CSVBuilderException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOMap = censusDataLoader.loadCensusData(csvFilePath);
        return censusDAOMap.size();
    }

    //METHOD TO CHECK FILE EXTENSION
    public void getFileExtension(File file) throws CensusAnalyserException {
        boolean result = false;
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            result = fileName.substring(fileName.lastIndexOf(".") + 1).equals("csv");
        }
        if (!result)
            throw new CensusAnalyserException("Wrong file type",
                    CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
    }

    //METHOD TO CHECK DELIMITER IN CSV FILE
    public void checkDelimiter(File file) throws CensusAnalyserException, CSVBuilderException {
        Pattern pattern = Pattern.compile("^[\\w ]*,[\\w ]*,[\\w ]*,[\\w ]*");
        BufferedReader br = null;
        boolean delimiterResult = true;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                delimiterResult = pattern.matcher(line).matches();
                if (!delimiterResult) {
                    throw new CensusAnalyserException("Invalid Delimiter found",
                            CensusAnalyserException.ExceptionType.INVALID_DELIMITER);
                }
            }
        } catch (FileNotFoundException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.NO_SUCH_FILE_EXIST);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        }
    }

    //GENERIC METHOD TO SORT CENSUS DATA
    public String getSortedCensusData(SortingMode mode) throws CensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        ArrayList censusDTO = censusDAOMap.values().stream()
                .sorted(CensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }

    public String getDualSortByPopulationDensity() throws CensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        ArrayList censusDTO = censusDAOMap.values().stream()
                .sorted(Comparator.comparingInt(CensusDAO::getPopulation).thenComparingDouble(CensusDAO::getPopulationDensity).reversed())
                .map(c -> c.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }
}
