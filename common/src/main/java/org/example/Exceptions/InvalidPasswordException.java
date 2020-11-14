package org.example.Exceptions;

/**
 * @author Alex
 */
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
