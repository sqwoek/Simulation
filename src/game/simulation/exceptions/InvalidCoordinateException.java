package game.simulation.exceptions;

import game.simulation.Coordinates;

public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException(Coordinates coordinates) {
        super(coordinates + " coordinates are not valid.");
    }
}