package game.simulation.actions;

import java.util.ArrayList;
import java.util.List;

public class InitActionsFactory {
    public static List<Action> createActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new InitializeWorldAction());
        return actions;
    }
}
