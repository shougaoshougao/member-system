/**
 *
 */
package com.renaissance.core.exception;

/**
 * @author Wilson
 */
public class AuthorizationException extends RuntimeException {

    private String code;

    /**
     *
     */
    public AuthorizationException() {
        super();
    }

    /**
     * @param message
     */
    public AuthorizationException(String message) {
        super(message);
    }

    /**
     * @param code
     * @param message
     */
    public AuthorizationException(String code, String message) {
        this(message);
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public static AuthorizationException codeOf(String code) {
        AuthorizationException exception = new AuthorizationException();
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
