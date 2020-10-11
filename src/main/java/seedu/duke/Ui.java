package seedu.duke;

public class Ui {
    private String logo = "                 _  _____ _                 \n"
            + "     /\\         (_)/ ____| |                \n"
            + "    /  \\   _ __  _| |    | |__   __ _ _ __  \n"
            + "   / /\\ \\ | '_ \\| | |    | '_ \\ / _` | '_ \\ \n"
            + "  / ____ \\| | | | | |____| | | | (_| | | | |\n"
            + " /_/    \\_\\_| |_|_|\\_____|_| |_|\\__,_|_| |_|\n"
            + "                                            \n"
            + "                                            ";

    /**
     * Prints AniChan logo.
     */
    public void showLogo() {
        System.out.println(logo);
    }

    /**
     * Greets new user upon opening up application.
     */
    public void greetFirstTime() {
        System.out.println("Welcome to AniChan!");
        showLogo();
        System.out.println("Let's get you started!");
        System.out.println("Please enter your particulars:");
    }

    /**
     * Greets existing user upon opening up application.
     */
    public void greetExisting(UserProfile userProfile) {
        String userName = userProfile.getFancyName();
        showLogo();
        System.out.println("Welcome Back, " + userName);
        System.out.println();
    }

    /**
     * Prints out available commands.
     */
    public void showMainMenu() {
        System.out.println("1. Add a profile (addprofile)");
        System.out.println("2. Edit a profile (editprofile)");
        System.out.println("3. Browse through all anime (browse)");
        System.out.println("4. Create a watchlist (watchlist)");
        System.out.println("5. Add to watchlist (add)");
        System.out.println("6. Bookmark an anime (bookmark)");
        System.out.println("7. Help (help)");
        System.out.println("8. Exit (exit)");
        System.out.println();
    }

    /**
     * Prints out a line telling a user that
     * an invalid command was given.
     */
    public void showInvalidCommand() {
        System.out.println("Please enter a valid command!");
        System.out.println();
    }

    /**
     * Tells the user that the input given is wrong
     * and shows the correct form of the command.
     */
    public void showInvalidDescription(String command) {
        switch (command) {
        case("addToWatchlist"):
            System.out.println("The input needs to be of the form \"add -a AnimeName\"! ");
            break;
        default:
            System.out.println("Please enter a valid input!");
        }
        System.out.println();
    }

    /**
     * Prints out bye message.
     */
    public void bye() {
        System.out.println("Sayonara!");
    }
}
