package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.human.UserManagement;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class HelpCommand extends Command {
    
    public HelpCommand() {
        
    }

    /**
     * Shows help function.
     */
    @Override
    public String execute(AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark, UserManagement userManagement) {
        // Code to be added

        // Print for testing
        return "Help showed";
        //System.out.println("Help showed");
    }
}
