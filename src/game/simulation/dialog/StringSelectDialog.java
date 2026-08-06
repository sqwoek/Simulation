package game.simulation.dialog;

import java.util.List;
import java.util.Scanner;

public class StringSelectDialog implements Dialog<String> {
    private final String title;
    private final String failMessage;
    private final List<String> keys;
    private final Scanner scanner = new Scanner(System.in);

    public StringSelectDialog(String title, String failMessage, List<String> keys) {
        this.title = title;
        this.failMessage = failMessage;
        this.keys = keys;
    }

    public StringSelectDialog(String title, String failMessage, String keys) {
        this(title, failMessage, List.of(keys));
    }

    @Override
    public String input() {
        System.out.println(title);
        while (true) {
            String s = scanner.nextLine();
            for (String key : keys) {
                if (key.equalsIgnoreCase(s)) {
                    return key;
                }
            }
            System.out.println(failMessage);
        }
    }
}