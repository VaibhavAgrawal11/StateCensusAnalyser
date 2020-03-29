package com.bridgelabz.servicetest;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.UsCSVData;
import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.service.StateCensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static com.bridgelabz.service.StateCensusAnalyser.COUNTRY.INDIA;
import static com.bridgelabz.service.StateCensusAnalyser.COUNTRY.US;

public class TestStateCensusAnalyser {

    String stateCensusCsvData = "./src/test/resources/StateCensusData.csv";
    String stateCodeCsvData = "./src/test/resources/StateCode.csv";
    String usCensusCsvData = "./src/test/resources/USCensusData.csv";
    StateCensusAnalyser indiaCensusAnalyser = new StateCensusAnalyser(INDIA);
    StateCensusAnalyser usCensusAnalyser = new StateCensusAnalyser(US);

    @Test
    public void givenStateCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int count = indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
        Assert.assertEquals(29, count);
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException() {
        try {
            stateCensusCsvData = "./src/test/resources/EstateCensusData.csv";
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectType_ReturnCustomException() {
        stateCensusCsvData = "./src/test/resources/EstateCensusData.pdf";
        try {
            File fileExtension = new File(stateCensusCsvData);
            indiaCensusAnalyser.getFileExtension(fileExtension);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataCopy.csv";
            File delimiterCheck = new File(stateCensusCsvData);
            indiaCensusAnalyser.checkDelimiter(delimiterCheck);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectHeader_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataHeaderCopy.csv";
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(e.getMessage(), "Number of data fields does not match number of headers.");
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int count = indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
        Assert.assertEquals(37, count);
    }

    @Test
    public void givenStateCodeCsvFile_WhenImproperFileName_ReturnsException() {
        try {
            stateCodeCsvData = "./src/test/resources/EstateCode.csv";
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData,stateCodeCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectType_ReturnCustomException() {
        stateCodeCsvData = "./src/test/resources/StateCode.pdf";
        try {
            File fileExtension = new File(stateCodeCsvData);
            indiaCensusAnalyser.getFileExtension(fileExtension);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCodeCsvData = "./src/test/resources/StateCodeDelimiterCopy.csv";
            File delimiterCheck = new File(stateCodeCsvData);
            indiaCensusAnalyser.checkDelimiter(delimiterCheck);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectHeader_ReturnCustomException() {
        try {
            stateCodeCsvData = "./src/test/resources/StateCodeHeaderCopy.csv";
            indiaCensusAnalyser.loadCensusData(INDIA,stateCodeCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals("Number of data fields does not match number of headers.", e.getMessage());
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATE);
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATE);
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Assam", censusCSV[2].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATE);
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertNotEquals("Gujarat", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATECODE);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("AD", stateCensuses[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATECODE);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("WB", stateCensuses[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenNotSortedOnState_ShouldNotReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATECODE);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertNotEquals("GJ", stateCensuses[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Sikkim", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenReturnsNull_ShouldThrowException() {
        try {
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.DENSITY);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Bihar", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.DENSITY);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldThrowException() {
        try {
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.DENSITY);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.AREA);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Rajasthan", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.AREA);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldThrowException() {
        try {
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.AREA);
            CSVStateCensus[] stateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenUsCensusData_WhenLoadedCorrect_ShouldReturnExactCountOfList() {
        try{
            int count = usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            Assert.assertEquals(51,count);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_WhenLoadedIncorrect_ShouldReturnWrongCountOfList() {
        try{
            int count = usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            Assert.assertNotEquals(52,count);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_WhenLoadedIncorrect_ShouldReturnWrongCountOfList_2() {
        try{
            int count = usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            Assert.assertNotEquals(50,count);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            UsCSVData[] stateCensuses = new Gson().fromJson(sortedCensusData, UsCSVData[].class);
            Assert.assertEquals("California", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            UsCSVData[] stateCensuses = new Gson().fromJson(sortedCensusData, UsCSVData[].class);
            Assert.assertEquals("Wyoming", stateCensuses[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenReturnsNull_ShouldThrowException() {
        try {
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            UsCSVData[] stateCensuses = new Gson().fromJson(sortedCensusData, UsCSVData[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }
}