package com.bridgelabz.exception;

public class CensusAnalyserException extends Exception {

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(Throwable cause, ExceptionType type) {
        super(cause);
        this.type = type;
    }

    public enum ExceptionType {
        INPUT_OUTPUT_OPERATION_FAILED, INVALID_DELIMITER, INVALID_HEADER_COUNT, NO_CENSUS_DATA, NO_SUCH_FILE_EXIST, UNABLE_TO_PARSE, WRONG_FILE_TYPE,NO_STATE_CODE_DATA;
    }
}