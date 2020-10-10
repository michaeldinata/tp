package seedu.duke;

public abstract class Human {
    protected String name;

    public Human(String name) throws AniException {
        if (!name.isEmpty()) {
            setName(name);
        } else {
            throw new AniException("Is your name empty?");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
