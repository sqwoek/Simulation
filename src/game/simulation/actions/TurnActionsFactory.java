package game.simulation.actions;

import java.util.ArrayList;
import java.util.List;

public class TurnActionsFactory {
    public static List<Action> createActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new AddGrassAction());
        actions.add(new AddHerbivoreAction());
        actions.add(new MoveAnimalsAction());
        return actions;
    }
}
