package game.simulation.actions.factory;

import game.simulation.actions.Action;
import game.simulation.actions.InitializeWorldAction;

import java.util.ArrayList;
import java.util.List;

public class InitActionsFactory {
    public static List<Action> createActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new InitializeWorldAction());
        return actions;
    }
}
