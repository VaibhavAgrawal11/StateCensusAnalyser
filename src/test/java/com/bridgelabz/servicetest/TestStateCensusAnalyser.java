package com.bridgelabz.servicetest;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestStateCensusAnalyser {

    String stateCensusCsvData = "./src/test/resources/StateCensusData.csv";
    String stateCodeCsvData = "./src/test/resources/StateCode.csv";
    StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();

    @Test
    public void givenStateCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws StateCensusAnalyserException {
        int count = censusAnalyser.loadCensusCSVData(stateCensusCsvData);
        Assert.assertEquals(29, count);
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException() {
        try {
            stateCensusCsvData = "./src/test/resources/EstateCensusData.csv";
            censusAnalyser.loadCensusCSVData(stateCensusCsvData);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectType_ReturnCustomException() {
        stateCensusCsvData = "./src/test/resources/EstateCensusData.pdf";
        try {
            File fileExtension = new File(stateCensusCsvData);
            censusAnalyser.getFileExtension(fileExtension);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataCopy.csv";
            File delimiterCheck = new File(stateCensusCsvData);
            censusAnalyser.checkDelimiter(delimiterCheck);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectHeader_ReturnCustomException() {
        try {
            stateCensusCsvData = "./src/test/resources/StateCensusDataHeaderCopy.csv";
            censusAnalyser.loadCensusCSVData(stateCensusCsvData);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("Number of data fields does not match number of headers.", e.getMessage());
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws StateCensusAnalyserException {
        int count = censusAnalyser.loadStateCodeData(stateCodeCsvData);
        Assert.assertEquals(37, count);
    }

    @Test
    public void givenStateCodeCsvFile_WhenImproperFileName_ReturnsException() {
        try {
            stateCensusCsvData = "./src/test/resources/EstateCode.csv";
            censusAnalyser.loadCensusCSVData(stateCensusCsvData);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }
}
