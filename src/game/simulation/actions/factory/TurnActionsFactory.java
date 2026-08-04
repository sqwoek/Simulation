package game.simulation.actions.factory;

import game.simulation.actions.Action;
import game.simulation.actions.AddGrassAction;
import game.simulation.actions.AddHerbivoreAction;
import game.simulation.actions.MoveAnimalsAction;

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
