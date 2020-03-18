package com.bridgelabz.servicetest;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class TestStateCensusAnalyser {
    String STATE_CENSUS_CSV_DATA;
    StateCensusAnalyser censusAnalyser;
    @Test
    public void givenStateCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws  StateCensusAnalyserException {
        STATE_CENSUS_CSV_DATA = "./src/test/resources/StateCensusData.csv";
        censusAnalyser = new StateCensusAnalyser();
        int count = censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        Assert.assertEquals(29, count);
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException() {
        STATE_CENSUS_CSV_DATA = "./src/test/resources/EstateCensusData.csv";
        censusAnalyser = new StateCensusAnalyser();
        try {
            censusAnalyser.loadCensusCSVData(STATE_CENSUS_CSV_DATA);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED,e.type);
        }
    }
}