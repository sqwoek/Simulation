package game.simulation.fieldObjects;

import game.simulation.Entity;

public class Rock extends FieldObject {
    private static final String name = "\uD83E\uDEA8";

    @Override
    public String getName() {
        return name;
    }
}