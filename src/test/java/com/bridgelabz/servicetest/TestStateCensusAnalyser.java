package com.bridgelabz.servicetest;

import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.dto.IndiaCensusCSV;
import com.bridgelabz.dto.USCensusCSV;
import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static com.bridgelabz.service.CensusAnalyser.COUNTRY.INDIA;
import static com.bridgelabz.service.CensusAnalyser.COUNTRY.US;

public class TestStateCensusAnalyser {

    String stateCensusCsvData = "./src/test/resources/StateCensusData.csv";
    String stateCodeCsvData = "./src/test/resources/StateCode.csv";
    String usCensusCsvData = "./src/test/resources/USCensusData.csv";
    CensusAnalyser indiaCensusAnalyser = new CensusAnalyser(INDIA);
    CensusAnalyser usCensusAnalyser = new CensusAnalyser(US);

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
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataCopy.csv";
            File delimiterCheck = new File(stateCensusCsvData);
            indiaCensusAnalyser.checkDelimiter(delimiterCheck);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
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
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCodeCsvData = "./src/test/resources/StateCodeDelimiterCopy.csv";
            File delimiterCheck = new File(stateCodeCsvData);
            indiaCensusAnalyser.checkDelimiter(delimiterCheck);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
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
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Assam", censusCSV[2].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertNotEquals("Gujarat", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("AD", stateCensuses[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("WB", stateCensuses[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenNotSortedOnState_ShouldNotReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData, stateCodeCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertNotEquals("GJ", stateCensuses[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenReturnsNull_ShouldThrowException() {
        try {
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedOnDensityPerSquareKM_ShouldThrowException() {
        try {
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldReturnSortedList_2() {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", stateCensuses[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedStateAreaList_ShouldThrowException() {
        try {
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
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
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedList_2() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", stateCensuses[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenReturnsNull_ShouldThrowException() {
        try {
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnArea_ShouldReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnArea_ShouldReturnSortedList_2() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", stateCensuses[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenReturnsNullArea_ShouldThrowException() {
        try {
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnStateName_ShouldReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnStateName_ShouldReturnSortedList_2() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", stateCensuses[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenReturnsNullStateName_ShouldThrowException() {
        try {
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", stateCensuses[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedList_2() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", stateCensuses[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenReturnsNullPopulationDensity_ShouldThrowException() {
        try {
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnStateCode_ShouldReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("AK", stateCensuses[0].stateId);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnStateCode_ShouldReturnSortedList_2() {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("WY", stateCensuses[50].stateId);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenReturnsNullStateCode_ShouldThrowException() {
        try {
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No Census Data", e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedPopulationAndDensity_ShouldReturnSortedList() throws CSVBuilderException {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getDualSortByPopulationDensity();
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensuses[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSortedPopulationAndDensity_ShouldReturnSortedList_2() throws CSVBuilderException {
        try {
            indiaCensusAnalyser.loadCensusData(INDIA,stateCensusCsvData);
            String sortedCensusData = indiaCensusAnalyser.getDualSortByPopulationDensity();
            IndiaCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim", stateCensuses[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedPopulationAndDensity_ShouldReturnSortedList() throws CSVBuilderException {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getDualSortByPopulationDensity();
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", stateCensuses[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedPopulationAndDensity_ShouldReturnSortedList_2() throws CSVBuilderException {
        try {
            usCensusAnalyser.loadCensusData(US,usCensusCsvData);
            String sortedCensusData = usCensusAnalyser.getDualSortByPopulationDensity();
            USCensusCSV[] stateCensuses = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", stateCensuses[50].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}