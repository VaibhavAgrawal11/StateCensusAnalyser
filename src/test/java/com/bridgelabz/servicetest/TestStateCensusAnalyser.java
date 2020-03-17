package com.bridgelabz.servicetest;

import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestStateCensusAnalyser {
    @Test
    public void givenStateCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws IOException {
        StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
        int count = censusAnalyser.loadCensusCSVData();
        Assert.assertEquals(29, count);
    }
}
