package com.bridgelabz.utility;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder(){
        return new OpenCsvImpl();
    }
}
