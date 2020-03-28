package com.bridgelabz.dao;

import com.bridgelabz.model.*;

public class IndianCensusDAO {
    private Integer tin;
    private Integer srNo;
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Integer population;
    public String stateCode;

    public IndianCensusDAO(CSVStateCensus indiaCensusCSV) {
        state = indiaCensusCSV.getState();
        areaInSqKm = indiaCensusCSV.getAreaInSqKm();
        densityPerSqKm = indiaCensusCSV.getDensityPerSqKm();
        population = indiaCensusCSV.getPopulation();
    }

    public IndianCensusDAO(CSVStateCode indiaCensusCSV) {
        stateCode=indiaCensusCSV.getStateCode();
        state=indiaCensusCSV.getStateName();
        srNo=indiaCensusCSV.getSrNo();
        tin=indiaCensusCSV.getTIN();
    }

    public IndianCensusDAO(UsCSVData usCSVData) {
        stateCode=usCSVData.getStateId();
        areaInSqKm=usCSVData.getArea();
        state=usCSVData.getState();
        densityPerSqKm=usCSVData.getPopulationDensity();
        population=usCSVData.getPopulation();
    }
}
