package seedu.duke;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.anime.AnimeStorage;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.command.Command;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String USER_PROFILE_FILE_NAME = "userprofile.txt";
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final String ANIME_DATA_SOURCE_FOLDER = "/data/AniListData";

    private Ui ui;
    private Parser parser;
    private Storage storage;
    private UserManagement userManagement;
    private AnimeStorage animeStorage;
    private Bookmark bookmark;
    private ArrayList<Watchlist> watchlists;
    private Watchlist activeWatchlist;
    private AnimeData animeData;

    public Duke() {
        ui = new Ui();
        ui.printWelcomeMessage(); //TODO: Move this out of constructor
        parser = new Parser();
        storage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);
        userManagement = new UserManagement(storage);
        userManagement.setCurrentUser(storage.readUserProfileFile(ui));
        watchlists = storage.readWatchlistFile(ui);

        //Initial SET UP for AnimeStorage / WatchLists / UserManagement / bookmark
        bookmark = new Bookmark();

        try {
            animeStorage = new AnimeStorage(ANIME_DATA_SOURCE_FOLDER);
            animeData = new AnimeData(animeStorage.readAnimeDatabase());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (watchlists.isEmpty()) {
            activeWatchlist = new Watchlist("Default");
            watchlists.add(activeWatchlist);
            try {
                storage.writeWatchlistFile(watchlists);
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        } else {
            activeWatchlist = watchlists.get(0);
        }

        // Assert
        assert userManagement != null;
        // Creates new User if no detected user
        if (userManagement.getCurrentUser() == null) {
            userManagement.addUserDialogue(ui);
            assert userManagement.getCurrentUser() != null;
        }
    }

    public void run() {
        boolean shouldExit = false;
        do {
            String userInput = ui.readUserInput(userManagement.getCurrentUser().getName(), activeWatchlist.getName());
            try {
                Command command = parser.getCommand(userInput);
                String output = command.execute(animeData, activeWatchlist, watchlists, bookmark, userManagement);
                ui.printCommandOutput(output);
                shouldExit = command.isExit();
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        } while (!shouldExit);

        //Program Terminates here
        ui.printGoodbyeMessage();
    }

    public static void main(String[] args) {
        new Duke().run();
    }

    // TODO: Organize the methods below to their respective class.
    // TEMPORARY, REMOVE ALL BELOW WHEN DONE REFACTORING!
    private static final Scanner CONSOLE = new Scanner(System.in);

    //Sample Usage of AnimeList Class [To Be Deleted]
    private void createAnimeList() {
        System.out.println("===Running Sample Anime List Class===");
        ArrayList<Anime> animeStorageList = new ArrayList<>();
        String[] releaseDate = {"2020", "12", "30"};
        String[] genre = {"Science", "Action", "Dance"};
        String animeName = "Adventures of Adventurers";
        int animeID = 1;
        int rating = 65;
        int avgEpisodeLength = 30;
        int totalEpisodes = 24;
        Anime testAnime = new Anime(animeName, releaseDate, rating, genre,
                avgEpisodeLength, totalEpisodes);

        String[] releaseDate2 = {"1997", "4", "15"};
        String[] genre2 = {"Adventure", "Action", "Dance"};
        String animeName2 = "Actions in Action";
        animeID = 2;
        int rating2 = 80;
        int avgEpisodeLength2 = 20;
        int totalEpisodes2 = 13;
        Anime testAnime2 = new Anime(animeName2, releaseDate2, rating2, genre2,
                avgEpisodeLength2, totalEpisodes2);

        animeStorageList.add(testAnime);
        animeStorageList.add(testAnime2);

        AnimeData animeData = new AnimeData(animeStorageList);
        animeData.printAll();

        System.out.println("===End of Sample Anime List Class===");
    }
}
