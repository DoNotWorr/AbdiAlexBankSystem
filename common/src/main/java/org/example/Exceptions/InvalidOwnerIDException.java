package org.example.Exceptions;

/**
 * @author Alex
 */
public class InvalidOwnerIDException extends Exception {
    public InvalidOwnerIDException(String message) {
        super(message);
    }
}