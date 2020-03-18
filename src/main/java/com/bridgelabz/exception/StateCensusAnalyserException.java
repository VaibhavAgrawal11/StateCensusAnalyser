package com.bridgelabz.exception;

public class StateCensusAnalyserException extends Exception {

    public ExceptionType type;

    public StateCensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public StateCensusAnalyserException(Throwable cause, ExceptionType type) {
        super(cause);
        this.type = type;
    }

    public enum ExceptionType {
        No_SUCH_FILE_EXIST, INPUT_OUTPUT_OPERATION_FAILED, WRONG_FILE_TYPE,INVALID_DELIMITER;
    }
}