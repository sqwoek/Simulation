package game;

import game.simulation.Simulation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        Thread simulationThread = new Thread(simulation::startSimulation);
        simulationThread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("Press P to pause the simulation or S to resume simulation.");
            String line = scanner.nextLine();
            if (line.equals("P")) {
                simulation.pauseSimulation();
            }
            if (line.equals("S")) {
                simulation.resumeSimulation();
            }
        }
    }
}
