package seedu.duke.human;

import seedu.duke.exception.AniException;

import java.util.ArrayList;

public class VoiceActor extends Human {
    protected ArrayList<Character> characters = new ArrayList<>();

    public VoiceActor(String name) throws AniException {
        super(name);
    }

    public void addCharacter(Character newCharacter) {
        characters.add(newCharacter);
    }

    public void printCharacters() {
        if (characters.size() > 0) {
            System.out.println("Characters voiced by " + name + " are:");
            for (int i = 0; i < characters.size(); i++) {
                System.out.println(i + 1 + ". " + characters.get(i));
            }
        } else {
            System.out.println(name + " has not voiced any characters yet.");
        }
    }

    public int getTotalCharacters() {
        return characters.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
