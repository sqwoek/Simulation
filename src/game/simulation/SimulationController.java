package game.simulation;

import game.simulation.dialog.Dialog;
import game.simulation.dialog.StringSelectDialog;

import java.util.List;
import java.util.Scanner;

public class SimulationController {
    private static final String START_COMMAND = "START";
    private static final String PAUSE_COMMAND = "PAUSE";
    private static final String RESUME_COMMAND = "RESUME";
    private static final String EXIT_COMMAND = "EXIT";
    private static final String PAUSE_MESSAGE = "Simulation is paused.";
    private static final String RESUME_MESSAGE = "Simulation is running again.";
    private static final String EXIT_MESSAGE = "Termination of Simulation...";

    public static void main(String[] args) {
        Simulation simulation = SimulationInitializer.create();
        Thread simulationThread = new Thread(simulation::startSimulation);
        runSimulationControlCycle(simulation, simulationThread);
    }

    private static void runSimulationControlCycle(Simulation simulation, Thread simulationThread) {
        boolean isPaused = false;
        String command;

        while (true) {
            boolean isSimulationOn = isSimulationRunning(simulationThread);
            if (!isSimulationOn) {
                command = getStartDialog().input();
                if (command.equals(START_COMMAND)) {
                    simulationThread.start();
                }
            } else {
                command = getControlDialog(isPaused).input();
                if (command.equals(PAUSE_COMMAND)) {
                    System.out.println(PAUSE_MESSAGE);
                    simulation.pauseSimulation();
                    isPaused = true;
                }
                if (command.equals(RESUME_COMMAND)) {
                    System.out.println(RESUME_MESSAGE);
                    simulation.resumeSimulation();
                    isPaused = false;
                }
                if (command.equals(EXIT_COMMAND)) {
                    System.out.println(EXIT_MESSAGE);
                    simulation.stopSimulation();
                    try {
                        simulationThread.join();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                }
            }
        }
    }

    private static boolean isSimulationRunning(Thread simulationThread) {
        return simulationThread.isAlive();
    }

    private static Dialog<String> getStartDialog() {
        String failMessage = "Unknown command. Please start the simulation first by entering: %s".formatted(START_COMMAND);
        String startSimulationMessage = """
                Welcome to the Simulation! This simulation demonstrates predators, herbivores, and their feeding behavior.
                Predators can hunt herbivores and have higher movement speed, while defenseless herbivores survive by eating grass.
                Watch this struggle for survival and resources unfold in real time!
                
                You can pause the simulation at any time by entering %s.
                To resume the simulation, enter %s.
                To exit the simulation, enter %s.
                
                To begin, enter %s to start the simulation!
                """.formatted(PAUSE_COMMAND, RESUME_COMMAND, EXIT_COMMAND, START_COMMAND);
        return new StringSelectDialog(startSimulationMessage, failMessage, START_COMMAND);
    }

    private static Dialog<String> getControlDialog(boolean isPaused) {
        List<String> keys;

        String controlMessage;
        if (isPaused) {
            keys = List.of(RESUME_COMMAND, EXIT_COMMAND);
            controlMessage = """
                Enter %s to resume the simulation.
                Enter %s to exit the simulation.
                """.formatted(RESUME_COMMAND, EXIT_COMMAND);
        } else {
            keys = List.of(PAUSE_COMMAND, EXIT_COMMAND);
            controlMessage = """
                Enter %s to pause the simulation.
                Enter %s to exit the simulation.
                """.formatted(PAUSE_COMMAND, EXIT_COMMAND);
        }
        String failMessage = "Unknown command. Please enter one of the following commands: %s".formatted(keys);
        return new StringSelectDialog(controlMessage, failMessage, keys);
    }

    private static void promptForConfirmation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press ENTER, to continue...");
        scanner.nextLine();
    }
}
