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
import java.util.Comparator;
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


    // Sample usage of Anime Class [To Be Deleted]
    private void addAnime() {
        System.out.println("===Running Sample Anime Class===");
        String[] releaseDate = {"2020", "12", "30"};
        String[] genre = {"Science", "Action", "Dance"};
        String animeName = "Adventures of Adventurers";
        int animeID = 0;
        int rating = 65;
        int avgEpisodeLength = 30;
        int totalEpisodes = 24;
        Anime testAnime = new Anime(animeName, releaseDate,
                rating, genre, avgEpisodeLength, totalEpisodes);
        System.out.println("Release Date in String: " + testAnime.getReleaseDateInString());
        System.out.println("===End of Sample Anime Class===");
    }


    /**
     * Browses the list of anime.
     */
    public void browseAnime(String description) {
        Scanner input = new Scanner(System.in);
        //Browse Feature (Bare and un-refactored)
        System.out.println("Browse Options:"
                + " 1 - Just Browse / 2 - List from (A-Z) / 3 - List from (Z-A) / 4 - Sort "
                + "Rating Ascending / 5 - Sort Rating Descending");

        ArrayList<Anime> usableList = animeData.animeDataList;
        switch (description.trim()) {
        case "1":
            break;
        case "2":
            usableList.sort(Comparator.comparing(Anime::getAnimeName));
            break;
        case "3":
            usableList.sort(Comparator.comparing(Anime::getAnimeName).reversed());
            break;
        case "4":
            usableList.sort(Comparator.comparing(Anime::getRating));
            break;
        case "5":
            usableList.sort(Comparator.comparing(Anime::getRating).reversed());
            break;
        default:
            System.out.println("Invalid Option!");
            return;
        }

        int browseIndex = 0;
        boolean isBrowsing = true;
        //Looping Mechanism
        System.out.println("Happy Browsing!");
        for (Anime anime : usableList) {
            if (!isBrowsing) {
                break;
            }
            browseIndex++;
            System.out.println("\t" + browseIndex + ". " + anime.getAnimeName());

            //Print 20 first then wait for user input
            if (browseIndex % 20 == 0) {
                while (true) {
                    System.out.println("Enter ID : More info on that series");
                    System.out.println("Enter 'c': Continue browsing");
                    System.out.println("Enter 'q': Stop browsing");
                    String browseOption = input.nextLine();

                    if (browseOption.matches("-?\\d+(\\.\\d+)?")) {
                        int indexObtained = Integer.parseInt(browseOption);
                        System.out.println("Requested List ID: " + indexObtained);
                        if (indexObtained > browseIndex || indexObtained < 1) {
                            System.out.println("Out of Range query!");
                        } else {
                            Anime browseAnime = usableList.get(indexObtained - 1);
                            System.out.println("ID          :   " + browseAnime.getAnimeID());
                            System.out.println("Title       :   " + browseAnime.getAnimeName());
                            System.out.println("Rating      :   " + browseAnime.getRating());
                            System.out.println("Episodes    :   " + browseAnime.getTotalEpisodes());
                            System.out.println("Release Date:   " + browseAnime.getReleaseDateInString());
                        }
                    }
                    if (browseOption.trim().equals("c")) {
                        break;
                    }
                    if (browseOption.trim().equals("q")) {
                        isBrowsing = false;
                        break;
                    }
                }
            }
        }
        System.out.println("Total results found: " + browseIndex);
        //Put the list back in its original form
        if (!description.trim().equals("1")) {
            usableList.sort(Comparator.comparing(Anime::getAnimeID));
        }
    }

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
