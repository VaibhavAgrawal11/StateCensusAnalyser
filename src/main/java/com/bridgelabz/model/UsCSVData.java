package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class UsCSVData {

    public UsCSVData(){}

    @CsvBindByName (column = "State Id" , required = true)
    public String stateId;

    @CsvBindByName (column = "State")
    public String state;

    @CsvBindByName (column = "Population")
    public Integer population;

    @CsvBindByName (column = "Housing units" , required = true)
    public   Integer housingUnits;

    @CsvBindByName (column = "Total area" , required = true)
    public   Double area;

    @CsvBindByName (column = "Water area" , required = true)
    public Double waterArea;

    @CsvBindByName (column = "Land area" , required = true)
    public Double landArea;

    @CsvBindByName (column = "Population Density" , required = true)
    public   Double populationDensity;

    @CsvBindByName (column = "Housing Density" , required = true)
    public   Double housingDensity;

    public UsCSVData(String state, String stateCode, Integer population, Double areaInSqKm, Double densityPerSqKm) {
        this.area=areaInSqKm;
        this.state=state;
        this.population=population;
        this.populationDensity=densityPerSqKm;
        this.stateId=stateCode;
    }
}
