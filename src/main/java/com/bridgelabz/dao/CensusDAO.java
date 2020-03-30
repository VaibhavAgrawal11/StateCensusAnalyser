package com.bridgelabz.dao;

import com.bridgelabz.dto.*;
import com.bridgelabz.service.CensusAnalyser;

import java.util.Comparator;

public class CensusDAO {
    private Integer tin;
    private Integer srNo;
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Integer population;
    public String stateCode;

    public CensusDAO(){ }

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCodeCSV indiaCensusCSV) {
        stateCode=indiaCensusCSV.StateCode;
        state=indiaCensusCSV.StateName;
        srNo=indiaCensusCSV.SrNo;
        tin=indiaCensusCSV.TIN;
    }

    public CensusDAO(USCensusCSV usCSVData) {
        stateCode=usCSVData.stateId;
        areaInSqKm=usCSVData.area;
        state=usCSVData.state;
        densityPerSqKm=usCSVData.populationDensity;
        population=usCSVData.population;
    }

    public static Comparator<? super CensusDAO> getSortComparator(CensusAnalyser.SortingMode mode) {
        if (mode.equals(CensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(CensusAnalyser.SortingMode.STATECODE))
            return Comparator.comparing(census -> census.stateCode);
        if (mode.equals(CensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getPopulationDensity).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CensusDAO::getTotalArea).reversed();
        return null;
    }

    public double getTotalArea() {
        return areaInSqKm;
    }

    public double getPopulationDensity() {
        return this.densityPerSqKm;
    }

    public int getPopulation() {
        return this.population;
    }


    public Object getCensusDTO(CensusAnalyser.COUNTRY country) {
        if (country.equals(CensusAnalyser.COUNTRY.INDIA))
            return new IndiaCensusCSV(state,stateCode, population, areaInSqKm, densityPerSqKm);
        if (country.equals(CensusAnalyser.COUNTRY.US))
            return new USCensusCSV(state, stateCode, population, areaInSqKm, densityPerSqKm);
        return null;
    }
}
