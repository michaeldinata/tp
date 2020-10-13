package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class CreateWatchlistCommand extends Command {

    public CreateWatchlistCommand(String description) {
        super(description);
    }

    @Override
    public void execute(Ui ui, Storage storage, AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark, UserManagement userManagement) {
        String[] descriptionSplit = description.split(" ", 2);
        String commandOption = descriptionSplit[0];
        String watchlistName = descriptionSplit[1];

        if (commandOption.equals("-n")) {
            Watchlist newWatchlist = new Watchlist(watchlistName);
            watchlists.add(newWatchlist);
        }

        storage.writeWatchlistFile(ui, watchlists);
        ui.printMessage("Watchlist created successfully!");
    }
}
