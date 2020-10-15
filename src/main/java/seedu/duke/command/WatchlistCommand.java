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

public class WatchlistCommand extends Command {
    private static final String CREATE_OPTION = "-n";
    private static final String LIST_OPTION = "-l";

    private final String option;
    private String optionInformation;
    private static final Logger LOGGER = Logger.getLogger(WatchlistCommand.class.getName());

    public WatchlistCommand(String description) {
        LOGGER.setLevel(Level.WARNING);
        String[] descriptionSplit = description.split(" ", 2);
        option = descriptionSplit[0];
        optionInformation = "";
        if (descriptionSplit.length == 2) {
            optionInformation = descriptionSplit[1];
        }
    }

    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) throws AniException {
        Storage storage = userManagement.getStorage();
        User activeUser = userManagement.getActiveUser();
        ArrayList<Watchlist> activeWatchlistList = activeUser.getWatchlistList();

        String commandOutput = "";
        assert option != null : "Command option cannot be null.";
        switch (option) {
        case CREATE_OPTION:
            commandOutput = createWatchlist(storage, activeWatchlistList);
            activeUser.setWatchlistList(activeWatchlistList);
            break;
        case LIST_OPTION:
            commandOutput = listAllWatchlist(activeWatchlistList);
            break;
        default:
            LOGGER.log(Level.WARNING, "Provided invalid option: " + option);
            throw new AniException("Watchlist command only accepts the option: \"-n\" and \"-l\".");
        }

        LOGGER.log(Level.INFO, commandOutput);
        return commandOutput;
    }

    private String createWatchlist(Storage storage, ArrayList<Watchlist> activeWatchlistList) throws AniException {
        if (optionInformation.isBlank()) {
            LOGGER.log(Level.WARNING, "Watchlist name is empty.");
            throw new AniException("Watchlist name cannot be empty.");
        }

        Watchlist newWatchlist = new Watchlist(optionInformation);
        boolean isWatchlistNameUnique = !activeWatchlistList.contains(newWatchlist);
        if (!isWatchlistNameUnique) {
            LOGGER.log(Level.WARNING, optionInformation + " is already one of the watchlist name.");
            throw new AniException("You already have a watchlist named \"" + optionInformation + "\".");
        }

        activeWatchlistList.add(newWatchlist);
        storage.saveWatchlist(activeWatchlistList);
        return "Watchlist created successfully!";
    }

    private String listAllWatchlist(ArrayList<Watchlist> activeWatchlistList) {
        if (activeWatchlistList.size() == 0) {
            return "You have no watchlist to list.";
        }

        StringBuilder sbWatchlistList = new StringBuilder();
        sbWatchlistList.append("Currently, you have ");
        sbWatchlistList.append(activeWatchlistList.size()).append(" watchlist(s):");
        for (int i = 0; i < activeWatchlistList.size(); i++) {
            Watchlist watchlist = activeWatchlistList.get(i);
            sbWatchlistList.append(System.lineSeparator());
            sbWatchlistList.append("\t").append(i + 1).append(". ");
            sbWatchlistList.append(watchlist.getName());
        }

        String watchlistList = sbWatchlistList.toString();
        assert !(watchlistList.isBlank()) : "The String that list watchlist(s) cannot be empty.";
        return watchlistList;
    }
}
