package seedu.duke.human;

import seedu.duke.exception.AniException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends Human {
    // Add Watchlist & Bookmarks here and/or UserManagement


    private static final SimpleDateFormat DATE_MONTH_YEAR = new SimpleDateFormat("dd/MM/yyyy");
    protected Date birthdate;
    protected Gender gender;

    public User(String name, String birthdate, String gender) throws ParseException, AniException {
        super(name);
        setBirthdate(birthdate);
        setGender(gender);
    }

    public void setGender(String genderString) throws AniException {
        genderString = genderString.toLowerCase();

        switch (genderString) {
        case "male":
            gender = Gender.Male;
            break;
        case "female":
            gender = Gender.Female;
            break;
        case "other":
            gender = Gender.Other;
            break;
        default:
            throw new AniException("Unexpected gender: " + genderString);
        }
    }

    public void setBirthdate(String birthdateString) throws ParseException {
        birthdate = DATE_MONTH_YEAR.parse(birthdateString);
        assert birthdate != null;
    }

    public String getDobString() {
        return DATE_MONTH_YEAR.format(birthdate);
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Provides the name of the user with Japanese honorifics depending on his gender.
     *
     * @return name of user with honorifics.
     */
    public String getName() {
        if (gender == Gender.Female) {
            return name + "-chan";
        } else {
            return name + "-san";
        }
    }

    @Override
    public String toString() {
        return "\nName: " + name + "\nBirthdate: " + getDobString() + "\nGender: " + getGender();
    }

    public String toFileString() {
        return name + " | " + DATE_MONTH_YEAR.format(birthdate) + " | " + gender;
    }
}
