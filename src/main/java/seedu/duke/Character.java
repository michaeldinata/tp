package seedu.duke;

import java.util.ArrayList;

public class Character extends Human {
    protected ArrayList<VoiceActor> voiceActors = new ArrayList<>();

    public Character(String name) throws AniException {
        super(name);
    }

    public void addVoiceActor(VoiceActor newVoiceActor) {
        voiceActors.add(newVoiceActor);
    }

    public void printVoiceActors() {
        System.out.println("Voice actors for " + name + " are:");
        for (int i = 0; i < voiceActors.size(); i++) {
            System.out.println(i + 1 + ". " + voiceActors.get(i));
        }
    }

    public int getTotalVoiceActors() {
        return voiceActors.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
