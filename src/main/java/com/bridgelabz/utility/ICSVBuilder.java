package com.bridgelabz.utility;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ICSVBuilder {
    public  <E> Iterator<E> getCSVFileIterator(Reader reader,
                                              Class csvClass) throws CSVBuilderException;
    public <E> List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;
    public <E> Map<E,E>getCSVFileMap (Reader reader, Class csvClass) throws CSVBuilderException;
}
