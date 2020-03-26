package com.bridgelabz.service;

import com.bridgelabz.dao.IndianCensusDAO;
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
    HashMap<String, IndianCensusDAO> censusDAOMap = new HashMap<String, IndianCensusDAO>();
    private Set set;

    //READING AND PRINTING DATA FROM STATE CENSUS CSV FILE
    public int loadCensusCSVData(String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            while (csvFileIterator.hasNext()) {
                IndianCensusDAO indianCensusDAO = new IndianCensusDAO(csvFileIterator.next());
                this.censusDAOMap.put(indianCensusDAO.state, indianCensusDAO);
            }
            return this.censusDAOMap.size();
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
            Iterator<CSVStateCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCode.class);
            while (csvFileIterator.hasNext()) {
                IndianCensusDAO indianCensusDAO = new IndianCensusDAO(csvFileIterator.next());
                this.censusDAOMap.put(indianCensusDAO.state, indianCensusDAO);
            }
            return this.censusDAOMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
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
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new stateCensusAnalyserException("No Census Data", stateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, IndianCensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(stateCodeComparator);
        Collection<IndianCensusDAO> list1 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list1);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CODE
    public String getStateWiseSortedStateCode() throws stateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new stateCensusAnalyserException("No State Data", stateCensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
        Comparator<Map.Entry<String, IndianCensusDAO>> stateCodeComparator = Comparator.comparing(census -> census.getValue().stateCode);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(stateCodeComparator);
        Collection<IndianCensusDAO> list2 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list2);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE ON BASIS OF POPULATION
    public String getStateWiseSortedSPopulation() throws stateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new stateCensusAnalyserException("No Population State Data", stateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, IndianCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().population);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<IndianCensusDAO> list = new ArrayList<IndianCensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE ON BASIS OF POPULATION DENSITY PER SQUARE KM
    public String getStateWiseSortedSPopulationDensity() throws stateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new stateCensusAnalyserException("No DensityPerSquareKM State Data", stateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, IndianCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().densityPerSqKm);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<IndianCensusDAO> list = new ArrayList<IndianCensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE ON BASIS OF STATE AREA
    public String getStateWiseSortedStateArea() throws stateCensusAnalyserException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new stateCensusAnalyserException("No Area of State Data", stateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<Map.Entry<String, IndianCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().areaInSqKm);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(censusComparator);
        ArrayList<IndianCensusDAO> list = new ArrayList<IndianCensusDAO>(sortedByValue.values());
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT THE LIST IN ALPHABETICAL ORDER
    private <E extends IndianCensusDAO> LinkedHashMap<String, IndianCensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, IndianCensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, IndianCensusDAO>> listOfEntries = new ArrayList<Map.Entry<String, IndianCensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = new LinkedHashMap<String, IndianCensusDAO>(listOfEntries.size());

        // copying entries from List to Map
        for (Map.Entry<String, IndianCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}