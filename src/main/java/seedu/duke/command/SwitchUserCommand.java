package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.UserManagement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SwitchUserCommand extends Command {
    protected static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    protected static final String REQUIRE_ADDITIONAL_FIELD = " requires an additional field";
    protected static final String NOT_RECOGNISED = " is not recognised!";

    private static Logger LOGGER = Logger.getLogger(SwitchUserCommand.class.getName());

    public SwitchUserCommand(String description) {
        LOGGER.setLevel(Level.WARNING);
        this.description = description;
    }

    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) throws AniException {
        String[] paramGiven = description.split("-");
        if (description.length() < 2) {
            LOGGER.log(Level.WARNING, "No Parameter provided");
            throw new AniException("No Parameter provided");
        }
        for (String param : paramGiven) {
            String[] paramParts = param.split(" ", 2);
            switch (paramParts[0].trim()) {
            case "": //skip the first empty param
                break;
            case "n": //Name of User
                paramLengthCheck(paramParts);
                System.out.println("Trying to switch to: " + paramParts[1]);
                User chgUser = userManagement.getUser(paramParts[1]);
                userManagement.setActiveUser(chgUser);
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
        return "";
    }

    private void paramLengthCheck(String[] paramParts) throws AniException {
        if (paramParts.length < 2) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + REQUIRE_ADDITIONAL_FIELD;
            throw new AniException(invalidParameter);
        }
    }
}
