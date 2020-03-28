package com.bridgelabz.service;

import com.bridgelabz.adaptor.CensusAdapter;
import com.bridgelabz.adaptor.CensusAdapterFactory;
import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.utility.CSVBuilderException;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class StateCensusAnalyser {
    int count = 0;
    Map<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();

    public enum COUNTRY {INDIA,US}

    //GENERIC METHOD LOADING EVERY FILE DATA
    public int loadCensusData(COUNTRY country, String... csvFilePath) throws CSVBuilderException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOMap = censusDataLoader.loadCensusData(csvFilePath);
        return censusDAOMap.size();
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
    public void checkDelimiter(File file) throws StateCensusAnalyserException, CSVBuilderException {
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
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.NO_SUCH_FILE_EXIST);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        }
    }

    //METHOD TO SORT STATE NAME
    public String getStateWiseSortedCensusData() throws StateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new StateCensusAnalyserException("No Census Data", StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
        Collection<CensusDAO> list1 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list1);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CODE
    public String getStateWiseSortedStateCode() throws StateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new StateCensusAnalyserException("No State Data", StateCensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
        Comparator<Map.Entry<String, CensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().stateCode);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeComparator);
        Collection<CensusDAO> list2 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list2);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE ON BASIS OF POPULATION
    public String getStateWiseSortedSPopulation() throws StateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new StateCensusAnalyserException("No Population State Data", StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, CensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().population);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE ON BASIS OF POPULATION DENSITY PER SQUARE KM
    public String getStateWiseSortedSPopulationDensity() throws StateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new StateCensusAnalyserException("No DensityPerSquareKM State Data", StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, CensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().densityPerSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE ON BASIS OF STATE AREA
    public String getStateWiseSortedStateArea() throws StateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new StateCensusAnalyserException("No Area of State Data", StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, CensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().areaInSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<CensusDAO> list = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT THE LIST IN ALPHABETICAL ORDER
    private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, CensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, CensusDAO>> listOfEntries = new ArrayList<Map.Entry<String, CensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, CensusDAO> sortedByValue = new LinkedHashMap<String, CensusDAO>(listOfEntries.size());
        for (Map.Entry<String, CensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}