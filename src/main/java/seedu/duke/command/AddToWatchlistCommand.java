package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class AddToWatchlistCommand extends Command {
    private static final String ADD_OPTION = "-a";
    
    private String option;
    private String animeName;

    public AddToWatchlistCommand(String description) {
        String[] descriptionSplit = description.split(" ", 2);
        this.option = descriptionSplit[0];
        if(descriptionSplit.length == 2) {
            this.animeName = descriptionSplit[1];
        }
    }

    /**
     * Adds an anime to current watchlist.
     */
    @Override
    public void execute(Ui ui, Storage storage, AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark, UserManagement userManagement) 
                        throws AniException {
  
        if (option.equals(ADD_OPTION)) {
            addToWatchlist(storage, currentWatchlist);
        } else {
            throw new AniException("Add command only accepts the option: \"-a\".");
        }
    }
    
    public void addToWatchlist(Storage storage, Watchlist currentWatchlist) throws AniException {
        if(animeName == null || animeName.trim().isEmpty()) {
            throw new AniException("Anime name cannot be empty.");
        }
        
        currentWatchlist.addAnimeToList(animeName);
    }
}
