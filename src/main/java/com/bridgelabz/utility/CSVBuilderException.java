package com.bridgelabz.utility;

public class CSVBuilderException extends Exception {

    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(Throwable cause, ExceptionType type) {
        super(cause);
        this.type = type;
    }

    public enum ExceptionType {
        INPUT_OUTPUT_OPERATION_FAILED, INVALID_DELIMITER, INVALID_HEADER_COUNT, NO_SUCH_FILE_EXIST, UNABLE_TO_PARSE, WRONG_FILE_TYPE
    }
}