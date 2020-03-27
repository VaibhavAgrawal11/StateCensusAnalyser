package com.bridgelabz.servicetest;

import com.bridgelabz.dao.IndianCensusDAO;
import com.bridgelabz.exception.stateCensusAnalyserException;
import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.service.StateCensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestStateCensusAnalyser {

    String stateCensusCsvData = "./src/test/resources/StateCensusData.csv";
    String stateCodeCsvData = "./src/test/resources/StateCode.csv";
    StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();

    @Test
    public void givenStateCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int count = censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
        Assert.assertEquals(29, count);
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException() {
        try {
            stateCensusCsvData = "./src/test/resources/EstateCensusData.csv";
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectType_ReturnCustomException() {
        stateCensusCsvData = "./src/test/resources/EstateCensusData.pdf";
        try {
            File fileExtension = new File(stateCensusCsvData);
            censusAnalyser.getFileExtension(fileExtension);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals(stateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataCopy.csv";
            File delimiterCheck = new File(stateCensusCsvData);
            censusAnalyser.checkDelimiter(delimiterCheck);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals(stateCensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectHeader_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataHeaderCopy.csv";
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(e.getMessage(), "Number of data fields does not match number of headers.");
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int count = censusAnalyser.loadIndiaCensusData(stateCensusCsvData, stateCodeCsvData);
        Assert.assertEquals(37, count);
    }

    @Test
    public void givenStateCodeCsvFile_WhenImproperFileName_ReturnsException() {
        try {
            stateCodeCsvData = "./src/test/resources/EstateCode.csv";
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData,stateCodeCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectType_ReturnCustomException() {
        stateCodeCsvData = "./src/test/resources/StateCode.pdf";
        try {
            File fileExtension = new File(stateCodeCsvData);
            censusAnalyser.getFileExtension(fileExtension);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals(stateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCodeCsvData = "./src/test/resources/StateCodeDelimiterCopy.csv";
            File delimiterCheck = new File(stateCodeCsvData);
            censusAnalyser.checkDelimiter(delimiterCheck);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals(stateCensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectHeader_ReturnCustomException() {
        try {
            stateCodeCsvData = "./src/test/resources/StateCodeHeaderCopy.csv";
            censusAnalyser.loadIndiaCensusData(stateCodeCsvData);
        } catch (CSVBuilderException e) {
            Assert.assertEquals("Number of data fields does not match number of headers.", e.getMessage());
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedList_2() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Assam", censusCSV[2].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertNotEquals("Gujarat", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateCode();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("AD", stateCensuses[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedList_2() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateCode();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("WB", stateCensuses[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenNotSortedOnState_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateCode();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertNotEquals("GJ", stateCensuses[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedSPopulation();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedSPopulation();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Sikkim", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenReturnsNull_ShouldThrowException() {
        try {
            String sortedCensusData = censusAnalyser.getStateWiseSortedSPopulation();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals("No Population State Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedSPopulationDensity();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Bihar", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList_2() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedSPopulationDensity();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldThrowException() {
        try {
            String sortedCensusData = censusAnalyser.getStateWiseSortedSPopulationDensity();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals("No DensityPerSquareKM State Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateArea();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Rajasthan", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList_2() {
        try {
            censusAnalyser.loadIndiaCensusData(stateCensusCsvData);
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateArea();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (stateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldThrowException() {
        try {
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateArea();
            IndianCensusDAO[] stateCensuses = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
        } catch (stateCensusAnalyserException e) {
            Assert.assertEquals("No Area of State Data", e.getMessage());
        }
    }
}