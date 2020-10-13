package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public abstract class Command {
    protected String description;
    private boolean isExit = false;

    public Command(String description) {
        setDescription(description);
    }

    public Command() {

    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }

    public String execute(AnimeData animeData, Watchlist currentWatchlist,
                          ArrayList<Watchlist> watchlists,
                          Bookmark bookmark, UserManagement userManagement) throws AniException {
        throw new AniException("This method should be implemented in the child class");
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit() {
        this.isExit = true;
    }
}
