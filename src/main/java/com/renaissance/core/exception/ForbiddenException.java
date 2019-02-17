/**
 *
 */
package com.renaissance.core.exception;

/**
 * @author Wilson
 */
public class ForbiddenException extends RuntimeException {

    private String code;

    /**
     *
     */
    public ForbiddenException() {
        super();
    }

    /**
     * @param message
     */
    public ForbiddenException(String message) {
        super(message);
    }

    /**
     * @param code
     * @param message
     */
    public ForbiddenException(String code, String message) {
        this(message);
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public static ForbiddenException codeOf(String code) {
        ForbiddenException exception = new ForbiddenException();
        exception.setCode(code);
        return exception;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

}
