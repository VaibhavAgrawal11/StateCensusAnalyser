package com.bridgelabz.servicetest;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

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
            censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED,e.type);
        }
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException_2() {
        STATE_CENSUS_CSV_DATA = "./src/test/resources/StateCensusData.pdf";
        try {
            censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED,e.type);
        }
    }
}