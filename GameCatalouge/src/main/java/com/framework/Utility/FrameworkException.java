package com.framework.Utility;

/**
 * This class is used all custom exception.
 */
public class FrameworkException extends RuntimeException {
    
    /**
     * @return the exception
     */
    public String getException() {
        return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(String exception) {
        this.exception = exception;
    }
    
    /**
     * default constructor
     */
    public FrameworkException() {
        super();
    }
    
    /**
     * construct exception with given 
     * exception message
     * 
     * @param exception
     */
    public FrameworkException(String exception) {
        super(exception);
        this.exception = exception;
    }

    /**
     * construct exception with given 
     * throwable instance
     * @param e
     */
    public FrameworkException(Throwable e) {
        super(e);
    }
    
    /**
     * construct exception with given 
     * exception message and throwable instance
     * @param exception
     * @param t
     */
    public FrameworkException(String exception, Throwable t) {
        super(exception, t);
        this.exception = exception;
    }
    private String exception;
}
