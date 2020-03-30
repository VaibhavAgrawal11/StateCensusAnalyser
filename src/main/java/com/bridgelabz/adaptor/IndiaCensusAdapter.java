package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.dto.IndiaCensusCSV;
import com.bridgelabz.dto.IndiaStateCodeCSV;
import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public  Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return  censusStateMap;
        return this.loadStateCodeData(censusStateMap, csvFilePath[1]);
    }

    //READING AND PRINTING DATA FROM STATE CODE CSV FILE
    public static Map<String, CensusDAO> loadStateCodeData(Map<String, CensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvStateCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCodeIterable.spliterator(), false)
                    .map(IndiaStateCodeCSV.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.StateName, new CensusDAO(csvStateCode)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
    }
}
