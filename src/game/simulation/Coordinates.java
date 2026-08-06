package game.simulation;

public record Coordinates(int x, int y) {
    public Coordinates shift(int x, int y) {
        return new Coordinates(this.x + x, this.y + y);
    }
}
