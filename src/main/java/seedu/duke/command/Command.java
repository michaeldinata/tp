package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;

public abstract class Command {
    protected String description;
    private boolean shouldExit = false;

    public boolean getShouldExit() {
        return shouldExit;
    }

    public void setShouldExit() {
        this.shouldExit = true;
    }

    public String execute(AnimeData animeData, UserManagement userManagement) throws AniException {
        throw new AniException("This method should be implemented in the child class");
    }
}
