package seedu.duke.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.human.User;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StorageTest {
    private static final String INVALID_USER_PROFILE_FILE = "a/b/c/invalid_userprofile.txt";
    private static final String INVALID_WATCHLIST_FILE = "a/b/c/invalid_watchlist.txt";

    private Ui ui;
    private Storage invalidStorage;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        invalidStorage = new Storage(INVALID_USER_PROFILE_FILE, INVALID_WATCHLIST_FILE);
    }

    @Test
    void loadUserProfile_invalidStorage_nullObject() {
        User user = invalidStorage.loadUser(ui);
        assertNull(user);
    }

    @Test
    void loadWatchlist_invalidStorage_notNullObject() {
        ArrayList<Watchlist> watchlists = invalidStorage.loadWatchlist(ui);
        assertNotNull(watchlists);
    }
}