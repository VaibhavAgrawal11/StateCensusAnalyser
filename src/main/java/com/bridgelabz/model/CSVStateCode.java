package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode {

    @CsvBindByName(column = "SrNo")
    private Integer SrNo;

    @CsvBindByName(column = "StateName")
    private String StateName;

    @CsvBindByName(column = "TIN")
    private Integer TIN;

    @CsvBindByName(column = "StateCode")
    private String StateCode;

    public Integer getSrNo() {
        return SrNo;
    }

    public void setSrNo(Integer srNo) {
        SrNo = srNo;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public Integer getTIN() {
        return TIN;
    }

    public void setTIN(Integer TIN) {
        this.TIN = TIN;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }
}
