package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;

import java.text.ParseException;

public class AddUserCommand extends Command {
    public static final String EXCEPTION_INVALID_PARAMETERS = "Invalid parameters detected!";
    public static final String REGEX_CHARACTER_HYPHEN = "-";
    public static final String REGEX_CHARACTER_SPACE = " ";
    public static final String PARAMETER_NAME = "n";
    public static final String PARAMETER_DATE_OF_BIRTH = "dob";
    public static final String PARAMETER_GENDER = "g";

    String name;
    String dob;
    String gender;

    public AddUserCommand(String userInput) throws AniException {
        try {
            String[] parametersSplit = userInput.split(REGEX_CHARACTER_HYPHEN);

            for (String s : parametersSplit) {
                String[] parameterTextSplit = s.split(REGEX_CHARACTER_SPACE, 2);

                if (parameterTextSplit.length == 2 && !parameterTextSplit[0].isEmpty()) {
                    switch (parameterTextSplit[0]) {
                    case PARAMETER_NAME:
                        name = parameterTextSplit[1];
                        break;
                    case PARAMETER_DATE_OF_BIRTH:
                        dob = parameterTextSplit[1];
                        break;
                    case PARAMETER_GENDER:
                        gender = parameterTextSplit[1];
                        break;
                    default:
                        // All is good, continue!
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AniException(EXCEPTION_INVALID_PARAMETERS);
        }
    }

    @Override
    public String execute(AnimeData animeData, UserManagement userManagement) throws AniException {
        try {
            return "Successfully added new user: " + userManagement.addUser(name.trim(), dob, gender);
        } catch (ParseException | NullPointerException e) {
            throw new AniException(EXCEPTION_INVALID_PARAMETERS);
        }
    }
}
