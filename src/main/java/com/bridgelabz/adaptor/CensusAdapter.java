package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.UsCSVData;
import com.bridgelabz.utility.CSVBuilderException;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException ;

     <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... getPath) throws CSVBuilderException {
        Map<String, CensusDAO> censusDAOMap = new HashedMap();
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath[0]))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvStateCensusIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("com.bridgelabz.model.CSVStateCensus"))
                StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                        .map(CSVStateCensus.class::cast)
                        .forEach(CSVStateCensus -> censusDAOMap.put(CSVStateCensus.getState(), new CensusDAO(CSVStateCensus)));
            if (censusCSVClass.getName().equals("com.bridgelabz.model.UsCSVData"))
                StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                        .map(UsCSVData.class::cast)
                        .forEach(UsCSVData -> censusDAOMap.put(UsCSVData.getState(), new CensusDAO(UsCSVData)));
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
