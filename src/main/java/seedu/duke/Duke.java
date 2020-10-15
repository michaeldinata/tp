package seedu.duke;

import seedu.duke.anime.AnimeData;
import seedu.duke.anime.AnimeStorage;
import seedu.duke.command.Command;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.UserManagement;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.io.IOException;
import java.util.ArrayList;

public class Duke {
    private static final String USER_PROFILE_FILE_NAME = "userprofile.txt";
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final String ANIME_DATA_SOURCE_FOLDER = "/data/AniListData";

    private final Ui ui;
    private final Parser parser;

    private AnimeStorage animeStorage;
    private AnimeData animeData;
    private UserManagement userManagement;

    public Duke() {
        ui = new Ui();
        parser = new Parser();
        Storage storage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);
        userManagement = new UserManagement(storage);

        // Load user and watchlist list.
        ui.printWelcomeMessage();
        ui.printHorizontalLine();
        userManagement.setActiveUser(storage.loadUser(ui));
        final ArrayList<Watchlist> watchlistList = storage.loadWatchlist(ui);
        ui.printHorizontalLine();

        // Initial Set up
        assert userManagement != null : "User management should not be null!";
        User activeUser = userManagement.getActiveUser();
        if (activeUser == null) {
            userManagement.addUserDialogue(ui);
            activeUser = userManagement.getActiveUser();
            assert userManagement.getActiveUser() != null : "User should have been created";
        }

        activeUser.setWatchlistList(watchlistList);
        if (watchlistList.isEmpty()) {
            Watchlist activeWatchlist = new Watchlist("Default");
            watchlistList.add(activeWatchlist);
            activeUser.setActiveWatchlist(activeWatchlist);
            activeUser.setWatchlistList(watchlistList);

            try {
                storage.saveWatchlist(watchlistList);
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        } else {
            activeUser.setActiveWatchlist(watchlistList.get(0));
            assert activeUser.getActiveWatchlist() != null : "Active watchlist should not be null.";
        }

        try {
            animeStorage = new AnimeStorage(ANIME_DATA_SOURCE_FOLDER);
            animeData = new AnimeData(animeStorage.readAnimeDatabase());
        } catch (IOException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    public void run() {
        boolean shouldExit = false;
        while (!shouldExit) {
            try {
                String userInput = ui.readUserInput(userManagement.getActiveUser());
                Command command = parser.getCommand(userInput);
                String commandOutput = command.execute(animeData, userManagement);
                
                ui.printMessage(commandOutput);
                shouldExit = command.getShouldExit();
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        }

        String goodbyeName = userManagement.getActiveUser().getHonorificName();
        ui.printGoodbyeMessage(goodbyeName);
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
