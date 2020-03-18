package com.bridgelabz.servicetest;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestStateCensusAnalyser {
    String STATE_CENSUS_CSV_DATA = "./src/test/resources/StateCensusData.csv";
    StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();

    @Test
    public void givenStateCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws  StateCensusAnalyserException {
        int count = censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        Assert.assertEquals(29, count);
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException() {
        try {
            STATE_CENSUS_CSV_DATA = "./src/test/resources/EstateCensusData.csv";
            censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectType_ReturnCustomException() {
        STATE_CENSUS_CSV_DATA = "./src/test/resources/EstateCensusData.pdf";
        try {
            File fileExtension = new File(STATE_CENSUS_CSV_DATA);
            censusAnalyser.getFileExtension(fileExtension);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectDelimiter_ReturnCustomException() {
        try {
            STATE_CENSUS_CSV_DATA = "./src/test/resources/StateCensusDataCopy.csv";
            File delimiterCheck = new File(STATE_CENSUS_CSV_DATA);
            censusAnalyser.checkDelimiter(delimiterCheck);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenIncorrectHeader_ReturnCustomException() {
        try {
            STATE_CENSUS_CSV_DATA = "./src/test/resources/StateCensusDataHeaderCopy.csv";
            censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals("Number of data fields does not match number of headers.", e.getMessage());
        }
    }

}
