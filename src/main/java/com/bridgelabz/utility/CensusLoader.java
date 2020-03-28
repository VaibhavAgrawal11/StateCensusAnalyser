package com.bridgelabz.utility;

import com.bridgelabz.dao.IndianCensusDAO;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStateCode;
import com.bridgelabz.model.UsCSVData;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusLoader {

    //READING AND PRINTING DATA FROM STATE CENSUS CSV FILE
    public static HashMap<String, IndianCensusDAO> loadCensusCSVData(HashMap<String, IndianCensusDAO> censusDAOMap, String... getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath[0]))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            Iterable<CSVStateCensus> csvStateCensusIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCensusIterable.spliterator(),false)
                    .map(CSVStateCensus.class::cast)
                    .forEach(csvStateCensus -> censusDAOMap.put(csvStateCensus.getState(),new IndianCensusDAO(csvStateCensus)));
            if (getPath.length == 1)
                return censusDAOMap;
            return loadStateCodeData(censusDAOMap, getPath[1]);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
    }

    //READING AND PRINTING DATA FROM STATE CODE CSV FILE
    public static HashMap<String, IndianCensusDAO> loadStateCodeData(HashMap<String, IndianCensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCode.class);
            Iterable<CSVStateCode> csvStateCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCodeIterable.spliterator(),false)
                    .map(CSVStateCode.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.getStateName(),new IndianCensusDAO(csvStateCode)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INPUT_OUTPUT_OPERATION_FAILED);
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Number of data fields does not match number of headers.",
                    CSVBuilderException.ExceptionType.INVALID_HEADER_COUNT);
        }
    }

    public static HashMap<String, IndianCensusDAO> loadUsCensusData(HashMap<String, IndianCensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<UsCSVData> csvFileIterator = csvBuilder.getCSVFileIterator(reader, UsCSVData.class);
            Iterable<UsCSVData> csvStateCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCodeIterable.spliterator(),false)
                    .map(UsCSVData.class::cast)
                    .forEach(usCSVData -> censusDAOMap.put((usCSVData).getState(),new IndianCensusDAO(usCSVData)));
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