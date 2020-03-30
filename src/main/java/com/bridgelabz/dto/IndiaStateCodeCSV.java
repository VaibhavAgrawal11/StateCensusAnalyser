package com.bridgelabz.dto;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    public IndiaStateCodeCSV() { }
    @CsvBindByName(column = "SrNo")
    public Integer SrNo;

    @CsvBindByName(column = "StateName")
    public String StateName;

    @CsvBindByName(column = "TIN")
    public Integer TIN;

    @CsvBindByName(column = "StateCode")
    public String StateCode;
}
