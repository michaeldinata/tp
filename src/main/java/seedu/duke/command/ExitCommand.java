package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.human.UserManagement;

public class ExitCommand extends Command {
    protected static final String NULL_STRING = "";

    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) {
        setShouldExit();
        return NULL_STRING;
    }
}
