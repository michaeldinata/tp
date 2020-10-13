package seedu.duke.human;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserManagementTest {

    @Test
    void getTotalUsers_noUsers_sizeZero() {
        UserManagement userManagement = new UserManagement(null);
        assertEquals(0, userManagement.getTotalUsers());
    }
}