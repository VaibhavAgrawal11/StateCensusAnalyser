package com.bridgelabz.dao;

import com.bridgelabz.model.*;
import com.bridgelabz.service.StateCensusAnalyser;

import java.util.Comparator;

public class CensusDAO {
    private Integer tin;
    private Integer srNo;
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Integer population;
    public String stateCode;

    public CensusDAO(CSVStateCensus indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(CSVStateCode indiaCensusCSV) {
        stateCode=indiaCensusCSV.StateCode;
        state=indiaCensusCSV.StateName;
        srNo=indiaCensusCSV.SrNo;
        tin=indiaCensusCSV.TIN;
    }

    public CensusDAO(UsCSVData usCSVData) {
        stateCode=usCSVData.stateId;
        areaInSqKm=usCSVData.area;
        state=usCSVData.state;
        densityPerSqKm=usCSVData.populationDensity;
        population=usCSVData.population;
    }

    public static Comparator<? super CensusDAO> getSortComparator(StateCensusAnalyser.SortingMode mode) {
        if (mode.equals(StateCensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(StateCensusAnalyser.SortingMode.STATECODE))
            return Comparator.comparing(census -> census.stateCode);
        if (mode.equals(StateCensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getPopulationDensity).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CensusDAO::getTotalArea).reversed();
        return null;
    }

    private double getTotalArea() {
        return areaInSqKm;
    }

    private double getPopulationDensity() {
        return this.densityPerSqKm;
    }

    private double getPopulation() {
        return this.population;
    }


    public Object getCensusDTO(StateCensusAnalyser.COUNTRY country) {
        if (country.equals(StateCensusAnalyser.COUNTRY.INDIA))
            return new CSVStateCensus(state,stateCode, population, areaInSqKm, densityPerSqKm);
        if (country.equals(StateCensusAnalyser.COUNTRY.US))
            return new UsCSVData(state, stateCode, population, areaInSqKm, densityPerSqKm);
        return null;
    }
}
