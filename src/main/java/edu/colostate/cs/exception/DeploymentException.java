package edu.colostate.cs.exception;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 5/16/14
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeploymentException extends Exception {

    public DeploymentException() {
    }

    public DeploymentException(String message) {
        super(message);
    }

    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeploymentException(Throwable cause) {
        super(cause);
    }

    public DeploymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
