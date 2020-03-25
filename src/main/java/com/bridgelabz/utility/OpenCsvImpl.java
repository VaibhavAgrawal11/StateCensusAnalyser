package com.bridgelabz.utility;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OpenCsvImpl<E> implements ICSVBuilder {
    //GENERIC METHOD FOR OPEN CSV TO READ AND ITERATE THE FILE
    @Override
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            Iterator<E> censusCSVIterator = csvToBean.iterator();
            return censusCSVIterator;
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        return  csvToBeanBuilder.build().parse();
    }

    @Override
    public <E> HashMap<E, E> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException {
        List list = getCSVFileList(reader, csvClass);
        Map<Integer,Object> map = new HashMap<Integer, Object>();
        Integer count =0;
        for (Object ob:list) {
            map.put( count, ob);
            count++;
        }
        return (HashMap<E, E>) map;
    }
}
