package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {

    //BINDING THE COLUMN NAME IN CsvBindByName CLASS
    @CsvBindByName(column = "State")
    private String State;

    @CsvBindByName(column = "population")
    private Integer Population;

    @CsvBindByName
    private Double AreaInSqKm;

    @CsvBindByName
    private Double DensityPerSqKm;

    public void setState(String state) {
        State = state;
    }

    //GETTER AND SETTER METHOD TO ENCAPSULATE AND PROVIDE WAY TO USER TO USE PRIVATE DATA
    public String getState() {
        return State;
    }

    public void setPopulation(Integer population) {
        Population = population;
    }

    public Integer getPopulation() {
        return Population;
    }

    public void setAreaInSqKm(Double areaInSqKm) {
        AreaInSqKm = areaInSqKm;
    }

    public Double getAreaInSqKm() {
        return AreaInSqKm;
    }

    public void setDensityPerSqKm(Double densityPerSqKm) {
        DensityPerSqKm = densityPerSqKm;
    }

    public Double getDensityPerSqKm() {
        return DensityPerSqKm;
    }
}
