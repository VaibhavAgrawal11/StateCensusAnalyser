package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.dto.USCensusCSV;
import com.bridgelabz.utility.CSVBuilderException;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
        return super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
    }
}
