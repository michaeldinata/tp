package seedu.duke.ui;

import seedu.duke.human.User;

import java.util.Scanner;

public class Ui {
    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final String LOGO =
                    "                 _  _____ _\n"
                    + "      /\\         (_)/ ____| |\n"
                    + "     /  \\   _ __  _| |    | |__   __ _ _ __\n"
                    + "    / /\\ \\ | '_ \\| | |    | '_ \\ / _` | '_ \\\n"
                    + "   / ____ \\| | | | | |____| | | | (_| | | | |\n"
                    + "  /_/    \\_\\_| |_|_|\\_____|_| |_|\\__,_|_| |_|\n";
    private static final String HORIZONTAL_LINE =
            "-------------------------------------------------------------";

    public void printMessage(String message) {
        if (!message.isBlank()) {
            System.out.println(" " + message);
        }
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(" ☹ OOPS!!! " + errorMessage);
    }

    public void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void printWelcomeMessage() {
        printMessage("Welcome to AniChan!");
        printMessage(LOGO);
    }

    public void printGoodbyeMessage(String name) {
        printMessage("Sayonara " + name + "!");
    }

    public String readInput() {
        System.out.print(" #> ");
        return CONSOLE.nextLine();
    }

    public String readUserInput(User activeUser) {
        String userName = activeUser.getHonorificName();
        String watchlistName = activeUser.getActiveWatchlistName();
        System.out.print(System.lineSeparator() + " " + userName + " (" + watchlistName + ") #> ");
        return CONSOLE.nextLine();
    }
}
