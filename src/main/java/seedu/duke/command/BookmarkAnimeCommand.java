package seedu.duke.command;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class BookmarkAnimeCommand extends Command {

    public BookmarkAnimeCommand(String description) {
        super(description);
    }


    @Override
    public void execute(Ui ui, Storage storage, AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark, UserManagement userManagement) {
        if (description.contains(" ")) {
            String[] descriptionSplit = description.split(" ", 2);
            // Code to be added
            String commandOption = descriptionSplit[0];
            String commandArgument = descriptionSplit[1];
            if (commandOption.equals("-a")) {
                if (isInteger(commandArgument)) {
                    int animeDataListIndex = Integer.parseInt(commandArgument);
                    Anime anime = animeData.getAnimeByID(animeDataListIndex);
                    System.out.println("Saving " + anime.getAnimeID() + ". "
                            + anime.getAnimeName() + " " + " to bookmark.");
                    bookmark.addAnimeBookmark(anime.getAnimeName());
                } else {
                    ArrayList<Anime> findList = animeData.findName(commandArgument);
                    for (Anime anime : findList) {
                        System.out.println("\t" + anime.getAnimeID() + ". " + anime.getAnimeName());
                    }
                }
            } else if (commandOption.equals("-d")) {
                int bookmarkIndex = Integer.parseInt(commandArgument);
                String animeName = bookmark.getAnimeBookmarkByIndex(bookmarkIndex - 1);
                System.out.println("Removing " + animeName + "! :(");
                bookmark.removeAnimeBookmark(bookmarkIndex - 1);
            } else {
                int bookmarkIndex = Integer.parseInt(commandOption);
                String[] commandArgumentSplit = commandArgument.split(" ", 2);
                String commandOption2 = commandArgumentSplit[0];
                String commandArgument2 = commandArgumentSplit[1];
                if (commandOption2.equals("-e")) {
                    int episode = Integer.parseInt(commandArgument2);
                    bookmark.editAnimeBookmarkEpisode(bookmarkIndex - 1, episode);
                    String animeName = bookmark.getAnimeBookmarkByIndex(bookmarkIndex - 1);
                    System.out.println("Editing " + animeName + " to have " + episode + " episode");
                }
            }
        } else {
            if (description.equals("-l")) {
                String bookmarks = bookmark.animeListInString();
                System.out.println(bookmarks);
            }
        }
    }

    public boolean isInteger(String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
