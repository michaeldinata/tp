package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public abstract class Command {
    protected String description;
    
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

    public void execute(Ui ui, Storage storage, AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark, UserManagement userManagement)
            throws AniException {
        throw new AniException("This method should be implemented in the child class");
    }

    public static boolean isExit(Command command) {
        return command instanceof ExitCommand;
    }
}
