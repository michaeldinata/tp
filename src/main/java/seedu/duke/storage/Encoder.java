package seedu.duke.storage;

import seedu.duke.human.User;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class Encoder {
    private static final String FILE_LINE_DELIMITER = " | ";

    public String encodeUser(User user) throws NullPointerException {
        String name = user.getName();
        String birthDate = user.getDobString();
        String gender = user.getGender().toString();

        String encodedUserString = name + FILE_LINE_DELIMITER + birthDate + FILE_LINE_DELIMITER + gender;
        assert (name != null && birthDate != null && gender != null) : "User details should not have any null.";
        return encodedUserString;
    }

    public String encodeWatchlist(ArrayList<Watchlist> watchlists) throws NullPointerException {
        StringBuilder sbWatchlist = new StringBuilder();
        for (Watchlist watchlist : watchlists) {
            sbWatchlist.append(watchlist.getName());
            sbWatchlist.append(FILE_LINE_DELIMITER);
            sbWatchlist.append(watchlist.getAnimeList());
            sbWatchlist.append(System.lineSeparator());
        }

        String encodedWatchlistString = sbWatchlist.toString();
        assert (!encodedWatchlistString.isBlank()) : "Encoded watchlist string should not be blank.";
        return encodedWatchlistString;
    }
}
