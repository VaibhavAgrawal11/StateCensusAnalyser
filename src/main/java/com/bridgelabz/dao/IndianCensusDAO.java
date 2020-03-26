package com.bridgelabz.dao;

import com.bridgelabz.model.*;

public class IndianCensusDAO {
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Integer population;
    public String stateCode;
    public Integer srNo;
    public Integer tin;

    public IndianCensusDAO(CSVStateCensus indiaCensusCSV) {
        state = indiaCensusCSV.getState();
        areaInSqKm = indiaCensusCSV.getAreaInSqKm();
        densityPerSqKm = indiaCensusCSV.getDensityPerSqKm();
        population = indiaCensusCSV.getPopulation();
    }

    public IndianCensusDAO(CSVStateCode csvStateCode) {
        stateCode = csvStateCode.getStateCode();
        srNo = csvStateCode.getSrNo();
        state = csvStateCode.getStateName();
        tin = csvStateCode.getTIN();
    }
}
