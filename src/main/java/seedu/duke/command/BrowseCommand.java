package seedu.duke.command;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowseCommand extends Command {
    protected static final int ANIME_PER_PAGE = 20;
    protected static final String SORT_PARAM = "s";
    protected static final String FILTER_PARAM = "f";
    protected static final String ORDER_PARAM = "o";
    protected static final String PAGE_PARAM = "p";
    protected static final String ASCENDING_FIELD = "asc";
    protected static final String DESCENDING_FIELD = "dsc";
    protected static final String NAME_FIELD = "name";
    protected static final String RATING_FIELD = "rating";
    protected static final int ID_SORT = 0;
    protected static final int ORDER_DESCENDING = 0;

    private int sortType;
    private int order;
    private int page;
    private String filter;

    protected static final String LAST_ANIME_WARNING = "Printing Last Anime Series from source";
    protected static final String BROWSE_PAGE_INDICATOR = "Browsing Page: ";
    protected static final String OUT_OF_BOUND_PAGE_WARNING = "Getting page: Tried to start at index: ";
    protected static final String OUT_OF_BOUND_PAGE_ERROR = "Invalid Page size!";
    protected static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    protected static final String REQUIRE_ADDITIONAL_FIELD = " requires an additional field";
    protected static final String TOO_MUCH_FIELDS = " has too much fields";
    protected static final String INVALID_OPTION = " is not a valid option";
    protected static final String NOT_RECOGNISED = " is not recognised!";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for page number!";
    protected static final String ASSERT_SORT_TYPE = "sortType should be < 3";
    protected static final String ASSERT_ORDER_TYPE = "order should be < 2";
    protected static final String BROWSE_SETTINGS_CHANGED_INFO = "Default values modified";
    protected static final String SORT_ID_DESCENDING = "Sorting by ID descending";
    protected static final String SORT_NAME_ASCENDING = "Sorting by Name Ascending (A to Z)";
    protected static final String SORT_NAME_DESCENDING = "Sorting by Name Descending (Z to A)";
    protected static final String SORT_RATING_ASCENDING = "Sorting by Rating Ascending (low to high)";
    protected static final String SORT_RATING_DESCENDING = "Sorting by Rating Descending (high to low)";
    
    private static final Logger LOGGER = Logger.getLogger(BrowseCommand.class.getName());


    public BrowseCommand(String description) {
        this.description = description;
        this.sortType = 0;
        this.order = 1;
        this.page = 1;
        this.filter = "";
        LOGGER.setLevel(Level.WARNING);
    }

    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) throws AniException {
        setBrowseOptions();
        ArrayList<Anime> usableList = animeData.getAnimeDataList();

        assert (sortType < 3) : ASSERT_SORT_TYPE;
        assert (order < 2) : ASSERT_ORDER_TYPE;

        sortBrowseList(usableList);
        String result = buildBrowseOutput(usableList);
        setSortType(3);
        sortBrowseList(usableList);
        return result;
    }

    private String buildBrowseOutput(ArrayList<Anime> usableList) throws AniException {
        int indexToPrint = (page - 1) * 20;
        if (indexToPrint >= usableList.size()) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_PAGE_WARNING + indexToPrint);
            throw new AniException(OUT_OF_BOUND_PAGE_ERROR);
        }

        StringBuilder result = new StringBuilder();
        for (int i = indexToPrint; i < indexToPrint + ANIME_PER_PAGE; i++) {
            Anime browseAnime = usableList.get(i);
            result.append(i + 1 + ". " + browseAnime.getAnimeName() + " [Id: " + browseAnime.getAnimeID() + "]");
            result.append(System.lineSeparator());
            if (i + 1 >= usableList.size()) {
                LOGGER.log(Level.WARNING, LAST_ANIME_WARNING);
                break;
            }
        }
        result.append(BROWSE_PAGE_INDICATOR).append(page);
        return result.toString();
    }

    private void setBrowseOptions() throws AniException {
        String[] paramGiven = description.split("-");
        if (paramGiven.length > 1) {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, BROWSE_SETTINGS_CHANGED_INFO);
        }
    }

    private void sortBrowseList(ArrayList<Anime> usableList) {
        if (sortType == ID_SORT && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_ID_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeID).reversed());
        } else if (sortType == 1 && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_NAME_ASCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeName));
        } else if (sortType == 1) {
            LOGGER.log(Level.INFO, SORT_NAME_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeName).reversed());
        } else if (sortType == 2 && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_RATING_ASCENDING);
            usableList.sort(Comparator.comparing(Anime::getRating));
        } else if (sortType == 2) {
            LOGGER.log(Level.INFO, SORT_RATING_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getRating).reversed());
        } else if (sortType == 3) {
            usableList.sort(Comparator.comparing(Anime::getAnimeID));
        }
    }

    private void parameterParser(String[] paramGiven) throws AniException {
        for (String param : paramGiven) {
            String[] paramParts = param.split(" ");
            switch (paramParts[0].trim()) {
            case "": //skip the first empty param
                break;
            case SORT_PARAM:
                paramLengthCheck(paramParts);
                checkSortType(paramParts);
                break;
            case FILTER_PARAM:
                paramLengthCheck(paramParts);
                setFilter(paramParts[1]);
                break;
            case ORDER_PARAM:
                paramLengthCheck(paramParts);
                checkOrderType(paramParts[1]);
                break;
            case PAGE_PARAM:
                paramLengthCheck(paramParts);
                if (!isInt(paramParts[1].trim())) {
                    throw new AniException(NON_INTEGER_PROVIDED);
                }
                setPage(Integer.parseInt(paramParts[1].trim()));
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
    }

    private void checkOrderType(String paramField) throws AniException {
        switch (paramField.trim()) {
        case ASCENDING_FIELD:
            setOrder(0);
            break;
        case DESCENDING_FIELD:
            setOrder(1);
            break;
        default:
            String paramFieldError = paramField + INVALID_OPTION;
            throw new AniException(paramFieldError);
        }
    }

    private void checkSortType(String[] paramParts) throws AniException {
        switch (paramParts[1].trim()) {
        case NAME_FIELD:
            setSortType(1);
            break;
        case RATING_FIELD:
            setSortType(2);
            break;
        default:
            String paramFieldError = paramParts[1] + INVALID_OPTION;
            throw new AniException(paramFieldError);
        }
    }

    private void paramLengthCheck(String[] paramParts) throws AniException {
        if (paramParts.length < 2) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + REQUIRE_ADDITIONAL_FIELD;
            throw new AniException(invalidParameter);
        } else if (paramParts.length > 2) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + TOO_MUCH_FIELDS;
            throw new AniException(invalidParameter);
        }
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 1);
    }

    public int getPage() {
        return page;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getSortType() {
        return sortType;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
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
