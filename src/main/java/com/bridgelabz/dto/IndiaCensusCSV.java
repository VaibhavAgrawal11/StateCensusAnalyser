package com.bridgelabz.dto;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV extends IndiaStateCodeCSV {

    public IndiaCensusCSV(){ }

    //BINDING THE COLUMN NAME IN CsvBindByName CLASS
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public Integer population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public Double areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public Double densityPerSqKm;

    public String stateCode = super.StateCode;

    public IndiaCensusCSV(String state, String stateCode, Integer population, Double areaInSqKm, Double densityPerSqKm) {
        this.stateCode = stateCode;
        this.state = state;
        this.areaInSqKm = areaInSqKm;
        this.population = population;
        this.densityPerSqKm = densityPerSqKm;
    }
}
