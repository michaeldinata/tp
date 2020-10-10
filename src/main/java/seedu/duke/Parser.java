package seedu.duke;

public class Parser {

    public String[] parseUserInput(String input) throws AniException {
        if (input == null || input.isEmpty()) {
            throw new AniException("Input is empty");
        }

        String[] inputSplit = input.split(" ", 2);
        return inputSplit;
    }
}
