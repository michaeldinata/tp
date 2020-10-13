package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class ExitCommand extends Command {
    
    public ExitCommand() {
        
    }

    @Override
    public void execute(Ui ui, Storage storage, AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark, UserManagement userManagement) {
        ui.printGoodbyeMessage();
    }
}
