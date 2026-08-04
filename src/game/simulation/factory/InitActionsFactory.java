package game.simulation.factory;

import game.simulation.actions.WorldAction;
import game.simulation.actions.InitializeWorldAction;

import java.util.ArrayList;
import java.util.List;

public class InitActionsFactory {
    public static List<WorldAction> createActions() {
        List<WorldAction> worldActions = new ArrayList<>();
        worldActions.add(new InitializeWorldAction());
        return worldActions;
    }
}
