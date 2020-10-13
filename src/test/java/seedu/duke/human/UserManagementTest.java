package seedu.duke.human;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserManagementTest {

    @Test
    void getTotalUsers_noUsers_sizeZero() {
        UserManagement userManagement = new UserManagement(null, null);
        assertEquals(0, userManagement.getTotalUsers());
    }
}