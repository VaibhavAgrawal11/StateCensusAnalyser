package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class UsCSVData {

    @CsvBindByName (column = "State Id" , required = true)
    private String stateId;

    @CsvBindByName (column = "State")
    private String state;

    @CsvBindByName (column = "Population")
    private Integer population;

    @CsvBindByName (column = "Housing units" , required = true)
    private  Integer housingUnits;

    @CsvBindByName (column = "Total area" , required = true)
    private  Double area;

    @CsvBindByName (column = "Water area" , required = true)
    private Double waterArea;

    @CsvBindByName (column = "Land area" , required = true)
    private Double landArea;

    @CsvBindByName (column = "Population Density" , required = true)
    private  Double populationDensity;

    @CsvBindByName (column = "Housing Density" , required = true)
    private  Double housingDensity;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getHousingUnits() {
        return housingUnits;
    }

    public void setHousingUnits(Integer housingUnits) {
        this.housingUnits = housingUnits;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getWaterArea() {
        return waterArea;
    }

    public void setWaterArea(Double waterArea) {
        this.waterArea = waterArea;
    }

    public Double getLandArea() {
        return landArea;
    }

    public void setLandArea(Double landArea) {
        this.landArea = landArea;
    }

    public Double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(Double populationDensity) {
        this.populationDensity = populationDensity;
    }

    public Double getHousingDensity() {
        return housingDensity;
    }

    public void setHousingDensity(Double housingDensity) {
        this.housingDensity = housingDensity;
    }
}
