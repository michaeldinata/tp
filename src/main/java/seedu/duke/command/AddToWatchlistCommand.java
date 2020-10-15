package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddToWatchlistCommand extends Command {
    private static final String ADD_OPTION = "-a";
    
    private String option;
    private String animeName = "";
    private static final Logger LOGGER = Logger.getLogger(AddToWatchlistCommand.class.getName());

    public AddToWatchlistCommand(String description) {
        LOGGER.setLevel(Level.WARNING);
        
        String[] descriptionSplit = description.split(" ", 2);
        
        option = descriptionSplit[0];
        if (descriptionSplit.length == 2) {
            animeName = descriptionSplit[1];
        }
    }

    /**
     * Adds an anime to current watchlist.
     */
    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) throws AniException {
        Storage storage = userManagement.getStorage();
        User activeUser = userManagement.getActiveUser();
        Watchlist activeWatchlist = activeUser.getActiveWatchlist();
        ArrayList<Watchlist> activeWatchlistList = activeUser.getWatchlistList();

        if (!option.equals(ADD_OPTION)) {
            LOGGER.log(Level.WARNING, "Option type given is wrong");
            throw new AniException("Watchlist command only accepts the option: \"-a\".");
        }
        assert option.equals("-a") == true : "option type should have been \"-a\".";
        addToWatchlist(storage, activeWatchlistList, activeWatchlist);

        return "Anime added to watchlist!";
    }
    
    public void addToWatchlist(Storage storage, ArrayList<Watchlist> activeWatchlistList, 
                               Watchlist activeWatchlist) throws AniException { 
        if (animeName == null || animeName.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Anime name is empty, exception thrown");
            throw new AniException("Anime name cannot be empty.");
        }

        int activeWatchlistIndex = activeWatchlistList.indexOf(activeWatchlist);
        activeWatchlist.addAnimeToList(animeName);
        activeWatchlistList.set(activeWatchlistIndex, activeWatchlist);

        storage.saveWatchlist(activeWatchlistList);
        LOGGER.log(Level.INFO, "Successfully added and stored anime into active watchlist");
    }
}
