package com.bridgelabz.utility;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public  <E> Iterator<E> getCSVFileIterator(Reader reader,
                                              Class<E> csvClass) throws CSVBuilderException;
}
