package seedu.duke.command;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowseCommand extends Command {
    private int sortType;
    private int order;
    private int page;
    private String filter;
    private static Logger logger = Logger.getLogger("BrowseLogger");


    public BrowseCommand(String description) {
        super(description);
        //Default values for browse command
        this.sortType = 0;
        this.order = 1;
        this.page = 1;
        this.filter = "";
        //Browse Feature (Bare and un-refactored)
        //-s sort [name/rating]       Default :No sort
        //-o [asc/dsc]                Default :Descending
        //-f [filter]                 Default :No filter
        //-p [page 1 of 25 entries]   Default :1
        //-i [Full Info]              Default :Off
    }

    @Override
    public void execute(Ui ui, Storage storage, AnimeData animeData,
                        Watchlist currentWatchlist, ArrayList<Watchlist> watchlists,
                        Bookmark bookmark, UserManagement userManagement) throws AniException {
        //Parameter Parser for Browse Command
        String[] paramGiven = description.split("-");
        if (paramGiven.length > 1) {
            parameterParser(paramGiven);
            logger.log(Level.INFO, "Default values modified");
        }
        //else use default values
        ArrayList<Anime> usableList = animeData.animeDataList;

        int indexToPrint = (page - 1) * 20;
        if (indexToPrint >= usableList.size()) {
            logger.log(Level.SEVERE, "Getting page: Tried to start at index: " + indexToPrint);
            throw new AniException("Invalid Page size!");
        }

        //Assert to ensure that sortType and orderType are all usable int
        assert (sortType < 3) : "sortType should be < 3";

        assert (order < 2) : "order should be < 2";
        sortBrowseList(usableList);
        //else no sort ascending

        for (int i = indexToPrint; i < indexToPrint + 20; i++) {
            Anime browseAnime = usableList.get(i);
            System.out.println(i + 1 + ". " + browseAnime.getAnimeName());
            if (i + 1 >= usableList.size()) {
                logger.log(Level.WARNING, "Printing Last Anime Series from source");
                break;
            }
        }
        System.out.println("Browsing Page: " + page);
        usableList.sort(Comparator.comparing(Anime::getAnimeID));
    }

    private void sortBrowseList(ArrayList<Anime> usableList) {
        if (sortType == 0 && order == 0) {
            //logger.log(Level.INFO, "Sorting by ID descending");
            usableList.sort(Comparator.comparing(Anime::getAnimeID).reversed());
        } else if (sortType == 1 && order == 0) {
            //logger.log(Level.INFO, "Sorting by Name Ascending (Z to A)");
            usableList.sort(Comparator.comparing(Anime::getAnimeName).reversed());
        } else if (sortType == 1) {
            //logger.log(Level.INFO, "Sorting by Name Ascending (A to Z)");
            usableList.sort(Comparator.comparing(Anime::getAnimeName));
        } else if (sortType == 2 && order == 0) {
            //logger.log(Level.INFO, "Sorting by Rating Ascending (low to high)");
            usableList.sort(Comparator.comparing(Anime::getRating));
        } else if (sortType == 2) {
            //logger.log(Level.INFO, "Sorting by Rating Descending (high to low)");
            usableList.sort(Comparator.comparing(Anime::getRating).reversed());
        }
    }

    private void parameterParser(String[] paramGiven) throws AniException {
        for (String param : paramGiven) {
            String[] paramParts = param.split(" ");
            switch (paramParts[0].trim()) {
            case "": //skip the first empty param
                break;
            case "s":
                paramLengthCheck(paramParts);
                checkSortType(paramParts);
                break;
            case "f":
                paramLengthCheck(paramParts);
                setFilter(paramParts[1]);
                break;
            case "o":
                paramLengthCheck(paramParts);
                checkOrderType(paramParts[1]);
                break;
            case "p":
                paramLengthCheck(paramParts);
                if (!isInt(paramParts[1].trim())) {
                    throw new AniException("Please specify an Int value for page number!");
                }
                setPage(Integer.parseInt(paramParts[1].trim()));
                break;
            default:
                String invalidParameter = "-" + param + " is an invalid parameter!";
                throw new AniException(invalidParameter);
            }
        }
    }

    private void checkOrderType(String paramField) throws AniException {
        //Logging here to set order type
        switch (paramField.trim()) {
        case "asc":
            setOrder(0);
            break;
        case "dsc":
            setOrder(1);
            break;
        default:
            String paramFieldError = paramField + " is not a valid option";
            throw new AniException(paramFieldError);
        }
    }

    private void checkSortType(String[] paramParts) throws AniException {
        switch (paramParts[1].trim()) {
        case "name":
            //Logging here to set sort type
            setSortType(1);
            break;
        case "rating":
            setSortType(2);
            break;
        default:
            String paramFieldError = paramParts[1] + " is not a valid option";
            throw new AniException(paramFieldError);
        }
    }

    private void paramLengthCheck(String[] paramParts) throws AniException {
        // Parameter Additional Field Check
        if (paramParts.length < 2) {
            String invalidParameter = "Parameter : " + paramParts[0] + " requires an additional field";
            throw new AniException(invalidParameter);
        } else if (paramParts.length > 2) {
            String invalidParameter = "Parameter : " + paramParts[0] + " has too much fields";
            throw new AniException(invalidParameter);
        }
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Checks if String is a parsable int.
     *
     * @param checkStr string to check
     * @return true if parsable int
     */
    public boolean isInt(String checkStr) {
        return checkStr.matches("-?\\d+(\\.\\d+)?");
    }
}
