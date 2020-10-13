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
    private ArrayList<Watchlist> watchlists;
    private AnimeData animeData;
    private Bookmark bookmark;
    private Watchlist activeWatchlist;

    public Duke() {
        ui = new Ui();
        ui.printWelcomeMessage();
        parser = new Parser();
        storage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);
        userManagement = new UserManagement(ui, storage);
        userManagement.setCurrentUser(storage.readUserProfileFile(ui));
        watchlists = storage.readWatchlistFile(ui);
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
            storage.writeWatchlistFile(ui, watchlists);
        } else {
            activeWatchlist = watchlists.get(0);
        }

        // Assert
        assert userManagement != null;
    }

    public void run() {
        Command command = null;
        if (userManagement.getCurrentUser() == null) {
            userManagement.addUserDialogue();
            assert userManagement.getCurrentUser() != null;
        }

        do {
            String userInput = ui.readUserInput(userManagement.getCurrentUser().getName(), activeWatchlist.getName());

            try {
                command = Parser.getCommand(userInput);
                // now passing in many parameters into execute,
                // but maybe can reduce in the future after refactoring?
                command.execute(ui, storage, animeData, activeWatchlist, watchlists, bookmark, userManagement);
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        } while (!Command.isExit(command));
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
