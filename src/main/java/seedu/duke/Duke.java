package seedu.duke;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Duke {
    private static ArrayList<Watchlist> watchlists;
    private static Ui ui;
    private static AnimeData animeData;
    private static Storage storage;
    private static final String USER_PROFILE_FILE_NAME = "userprofile.txt";
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final Scanner CONSOLE = new Scanner(System.in);

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        ui = new Ui();
        storage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);

        UserProfile userProfile = null;
        try {
            userProfile = storage.readUserProfileFile();
        } catch (ParseException | AniException exception) {
            System.out.println("User profile data is corrupted or not found!");
        }

        watchlists = storage.readWatchlistFile();
        if (userProfile == null) {
            userProfile = quickStart();

            storage.writeUserProfileFile(userProfile);
        } else {
            ui.greetExisting(userProfile);
        }

        addAnime();

        try {
            System.out.println("===Running Anime Data Print check===");
            AnimeStorage animeStorage = new AnimeStorage("/data/AniListData");
            animeData = new AnimeData(animeStorage.readAnimeDatabase());
            //animeData.printAll();
            //animeData.printOne(1);
            System.out.println("===End of Anime Data Print check===");
        } catch (IOException e) {
            e.printStackTrace();
        }

        createAnimeList();
        getCommand();
    }

    private static UserProfile quickStart() {
        ui.greetFirstTime();

        UserProfile userProfile = null;
        boolean profileMade = false;
        while (!profileMade) {
            try {
                userProfile = createProfile();
                profileMade = true;
            } catch (ParseException e) {
                System.out.println("Is your date in dd/MM/yyyy format?");
            } catch (AniException e) {
                e.printStackTrace();
            }
        }

        return userProfile;
    }

    private static UserProfile createProfile() throws ParseException, AniException {
        System.out.println("What's your name?");
        String name = CONSOLE.nextLine();
        System.out.println("Hello " + name + "! What might your date of birth be?");
        String dob = CONSOLE.nextLine();
        System.out.println("What might your gender be? (Male/Female/Others)");
        String gender = CONSOLE.nextLine();

        UserProfile newProfile = new UserProfile(name, dob, gender);
        System.out.println(newProfile);
        return newProfile;
    }

    /**
     * Prints the main menu of the application
     * and requests for command.
     */
    private static void getCommand() {
        System.out.println("What would you like to do today?");
        Parser parser = new Parser();
        ui.showMainMenu();

        while (CONSOLE.hasNextLine()) {
            String fullCommand = CONSOLE.nextLine();
            try {
                String[] fullCommandSplit = parser.parseUserInput(fullCommand);

                String description = "";
                String command = fullCommandSplit[0];
                if (fullCommandSplit.length > 1) {
                    description = fullCommandSplit[1];
                }

                switch (command) {
                case "addprofile":
                    addProfile(description);
                    break;
                case "editprofile":
                    editProfile(description);
                    break;
                case "browse":
                    browseAnime(description);
                    break;
                case "watchlist":
                    createWatchlist(description);
                    break;
                case "add":
                    addToWatchlist(description);
                    break;
                case "bookmark":
                    bookmarkAnime(description);
                    break;
                case "help":
                    showHelp();
                    break;
                case "exit":
                    ui.bye();
                    return;
                default:
                    throw new AniException("??");
                }
            } catch (AniException e) {
                e.printStackTrace();
                // ui.showInvalidCommand();
            }
        }
    }

    // Sample usage of Anime Class [To Be Deleted]
    private static void addAnime() {
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

    private static void createWatchlist(String description) {
        String[] descriptionSplit = description.split(" ", 2);
        String commandOption = descriptionSplit[0];
        String watchlistName = descriptionSplit[1];

        if (commandOption.equals("-n")) {
            Watchlist newWatchlist = new Watchlist(watchlistName);
            watchlists.add(newWatchlist);
        }

        storage.writeWatchlistFile(watchlists);
        System.out.println("Watchlist created successfully!");
    }

    /**
     * Adds a new user profile.
     */
    private static void addProfile(String description) {
        // Code to be added

        // Print for testing
        System.out.println("New profile added!");
    }

    /**
     * Edits an existing user profile.
     */
    private static void editProfile(String description) {
        // Code to be added

        // Print for testing
        System.out.println("Profile edited!");
    }

    /**
     * Browses the list of anime.
     */
    public static void browseAnime(String description) {
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

    /**
     * Adds an anime to current watchlist.
     */
    private static void addToWatchlist(String description) {
        String[] descriptionSplit = description.split(" ", 2);

        try {
            String commandOption = descriptionSplit[0];
            String animeName = descriptionSplit[1];

            if (commandOption.equals("-a") && animeName != null && !animeName.trim().isEmpty()) {
                // currently only adding anime to default watchlist before
                // implementing the selecting of watchlist
                Watchlist currentWatchlist = watchlists.get(0);
                currentWatchlist.addAnimeToList(animeName);
            } else {
                ui.showInvalidDescription("addToWatchlist");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showInvalidDescription("addToWatchlist");
        }
    }

    /**
     * Bookmarks an anime.
     */
    private static void bookmarkAnime(String description) {
        // Code to be added

        // Print for testing
        System.out.println("Anime bookmarked");
    }

    /**
     * Shows help function.
     */
    private static void showHelp() {
        // Code to be added

        // Print for testing
        System.out.println("Help showed");
    }

    //Sample Usage of AnimeList Class [To Be Deleted]
    private static void createAnimeList() {
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
