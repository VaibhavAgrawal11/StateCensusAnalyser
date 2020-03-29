package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode {

    public CSVStateCode() { }
    @CsvBindByName(column = "SrNo")
    public Integer SrNo;

    @CsvBindByName(column = "StateName")
    public String StateName;

    @CsvBindByName(column = "TIN")
    public Integer TIN;

    @CsvBindByName(column = "StateCode")
    public String StateCode;
}
