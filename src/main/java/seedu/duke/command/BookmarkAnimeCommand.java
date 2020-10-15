package seedu.duke.command;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkAnimeCommand extends Command {

    private int bookmarkIndex;
    private int animeIndex;
    private int bookmarkEpisode;
    // e for edit, a for add, d for delete
    private String bookmarkAction;
    private static final Logger LOGGER = Logger.getLogger(Command.class.getName());

    public BookmarkAnimeCommand(String description) throws AniException {
        // Set log levels
        LOGGER.setLevel(Level.WARNING);

        this.description = description;
        String[] paramGiven = description.split("-");
        if (paramGiven.length > 2) {
            String invalidDescription =  description + " has too many parameter!";
            LOGGER.warning("Could not load bookmark command:" + invalidDescription);
            throw new AniException(invalidDescription);
        }
        parameterParser(paramGiven[1]);
        // If bookmark action is edit, save the first field which is BookmarkIndex
        if (bookmarkAction.equals("e")) {
            boolean isValidBookmarkIndex = setBookmarkIndex(paramGiven[0].trim());
            if (!isValidBookmarkIndex) {
                String invalidBookmarkIndex = "" + paramGiven[0] + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark index for edit episode requires integer.";
                throw new AniException(invalidBookmarkIndex);
            }
        }
        LOGGER.info("Successfully loaded fields for Bookmark command.");
    }

    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) {
        String result = "";
        Bookmark bookmark = userManagement.getActiveUser().bookmark;

        switch (bookmarkAction) {
        case "e":
            LOGGER.info("Executing Edit Episode.");
            if (bookmarkIndex > bookmark.getBookmarkSize() || bookmarkIndex <= 0) {
                String invalidBookmarkIndex = "Bookmark index " + bookmarkIndex + "provided is invalid."
                        + System.lineSeparator() + " Bookmark index is outside Bookmark range (too big or too small).";
                LOGGER.warning("Bookmark command execute failed:" + invalidBookmarkIndex);
            }

            assert bookmarkEpisode >= 0 : "bookmarkEpisode should be positive";
            bookmark.editAnimeBookmarkEpisode(bookmarkIndex - 1, bookmarkEpisode);
            Anime animeToEdit = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
            result = "Editing " + animeToEdit.getAnimeName() + " to have " + bookmarkEpisode + " episode";
            //System.out.println("Editing " + animeName + " to have " + episode + " episode");
            break;
        case "a":
            LOGGER.info("Executing Add Anime to Bookmark.");
            if (animeIndex > animeData.getSize() || animeIndex <= 0) {
                String invalidAnimeIndex = "Anime index " + animeIndex + "provided is invalid."
                        + System.lineSeparator() + " Anime index is outside AnimeData range (too big or too small).";
                LOGGER.warning("Bookmark command execute failed:" + invalidAnimeIndex);
            }

            Anime animeToAdd = animeData.getAnimeByID(animeIndex - 1);
            result = "Saving " + animeToAdd.getAnimeID() + ". " + animeToAdd.getAnimeName() + " " + " to bookmark.";
            bookmark.addAnimeBookmark(animeToAdd.getAnimeID());
            break;
        case "d":
            LOGGER.info("Executing Delete Anime from Bookmark.");
            if (bookmarkIndex > bookmark.getBookmarkSize() || bookmarkIndex <= 0) {
                String invalidBookmarkIndex = "Bookmark index " + bookmarkIndex + "provided is invalid."
                        + System.lineSeparator() + " Bookmark index is outside Bookmark range (too big or too small).";
                LOGGER.warning("Bookmark command execute failed:" + invalidBookmarkIndex);
            }
            Anime animeToDelete = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
            result = "Removing " + animeToDelete.getAnimeName() + "! :(";
            //System.out.println("Removing " + animeName + "! :(");
            bookmark.removeAnimeBookmark(bookmarkIndex - 1);
            break;
        case "l":
            LOGGER.info("Executing List all anime in Bookmark.");
            result = "Listing all anime in bookmark: ";
            String bookmarks = bookmark.animeListInString(animeData);
            result += bookmarks;
            break;
        default:
            break;
        }
        LOGGER.info("Execute Bookmark command successful.");
        return result;
    }

    private void parameterParser(String paramGiven) throws AniException {
        String[] paramParts = paramGiven.split(" ");
        switch (paramParts[0].trim()) {
        case "e":
            paramLengthCheck(paramParts);
            setBookmarkAction(paramParts[0]);
            Boolean isValidBookmarkEpisode = setBookmarkEpisode(paramParts[1].trim());
            if (!isValidBookmarkEpisode) {
                String invalidParameter = "-" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark edit episode param requires integer.";
                LOGGER.warning("Could not load bookmark command:" + invalidParameter);
                throw new AniException(invalidParameter);
            }
            break;
        case "a":
            paramLengthCheck(paramParts);
            setBookmarkAction(paramParts[0]);
            Boolean isValidAnimeIndex = setAnimeIndex(paramParts[1].trim());
            if (!isValidAnimeIndex) {
                String invalidParameter = "-" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark Add param requires integer.";
                LOGGER.warning("Could not load bookmark command:" + invalidParameter);
                throw new AniException(invalidParameter);
            }
            break;
        case "d":
            paramLengthCheck(paramParts);
            setBookmarkAction(paramParts[0]);
            Boolean isValidBookmarkIndex = setBookmarkIndex(paramParts[1].trim());
            if (!isValidBookmarkIndex) {
                String invalidParameter = "-" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark delete param requires integer.";
                LOGGER.warning("Could not load bookmark command:" + invalidParameter);
                throw new AniException(invalidParameter);
            }
            break;
        case "l":
            setBookmarkAction(paramParts[0]);
            break;
        default:
            String invalidParameter = "-" + paramGiven + " is an invalid parameter!";
            throw new AniException(invalidParameter);
        }
    }

    private void paramLengthCheck(String[] paramParts) throws AniException {
        // Parameter Additional Field Check
        if (paramParts.length < 2) {
            String invalidParameter = "Parameter : " + paramParts[0] + " requires an additional field";
            LOGGER.warning("Could not load bookmark command:" + invalidParameter);
            throw new AniException(invalidParameter);
        } else if (paramParts.length > 2) {
            String invalidParameter = "Parameter : " + paramParts[0] + " has too much fields";
            LOGGER.warning("Could not load bookmark command:" + invalidParameter);
            throw new AniException(invalidParameter);
        }
    }

    private void setBookmarkAction(String actionString) {
        this.bookmarkAction = actionString;
    }

    private boolean setBookmarkIndex(String bookmarkIndexString) {
        if (isInt(bookmarkIndexString)) {
            bookmarkIndex = Integer.parseInt(bookmarkIndexString);
            return true;
        } else {
            return false;
        }
    }

    private boolean setAnimeIndex(String animeIndexString) {
        if (isInt(animeIndexString)) {
            animeIndex = Integer.parseInt(animeIndexString);
            return true;
        } else {
            return false;
        }
    }

    private boolean setBookmarkEpisode(String bookmarkEpisodeString) {
        if (isInt(bookmarkEpisodeString)) {
            bookmarkEpisode = Integer.parseInt(bookmarkEpisodeString);
            return true;
        } else {
            return false;
        }
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
