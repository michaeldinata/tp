package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.human.UserManagement;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class HelpCommand extends Command {

    /**
     * Shows help function.
     */
    @Override
    public String execute(AnimeData animeData, ArrayList<Watchlist> activeWatchlistList, Watchlist activeWatchlist,
                          UserManagement userManagement) {
        

        // Print for testing
        return "Help showed";
    }
}
