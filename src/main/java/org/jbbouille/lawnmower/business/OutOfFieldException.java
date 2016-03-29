package org.jbbouille.lawnmower.business;

public class OutOfFieldException extends RuntimeException {
    public OutOfFieldException(String message, ArrayIndexOutOfBoundsException e) {
        super(message, e);
    }
}
