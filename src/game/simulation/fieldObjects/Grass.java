package game.simulation.fieldObjects;

import game.simulation.Entity;

public class Grass extends FieldObject {
    private static final String name = "\uD83C\uDF31";

    @Override
    public String getName() {
        return name;
    }
}