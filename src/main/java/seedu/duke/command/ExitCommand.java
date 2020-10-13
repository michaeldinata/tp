package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.human.UserManagement;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class ExitCommand extends Command {

    protected static final String NULL_STRING = "";

    public ExitCommand() {
        
    }

    @Override
    public String execute(AnimeData animeData, Watchlist currentWatchlist, ArrayList<Watchlist> watchlists,
                          Bookmark bookmark, UserManagement userManagement) {
        setExit();
        return NULL_STRING;
    }
}
