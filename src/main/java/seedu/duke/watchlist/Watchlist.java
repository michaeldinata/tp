package seedu.duke.watchlist;

import java.util.ArrayList;

public class Watchlist {
    private final String name;
    private final ArrayList<String> animeList;

    public Watchlist(String name) {
        this.name = name;
        this.animeList = new ArrayList<>();
    }

    public Watchlist(String name, ArrayList<String> animeList) {
        this.name = name;
        this.animeList = animeList;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAnimeList() {
        return animeList;
    }

    public void addAnimeToList(String animeName) {
        this.animeList.add(animeName);
    }

    public String animeListToString() {
        StringBuilder sbAnimeList = new StringBuilder();
        if (animeList.size() == 0) {
            sbAnimeList.append("Uhh.. It's empty.. :(");
            sbAnimeList.append(System.lineSeparator());
        }

        for (int i = 0; i < animeList.size(); i++) {
            sbAnimeList.append(i + 1);
            sbAnimeList.append(". ");
            sbAnimeList.append(animeList.get(i));
            sbAnimeList.append(System.lineSeparator());
        }

        return sbAnimeList.toString();
    }

    @Override
    public String toString() {
        return name + System.lineSeparator() + animeListToString();
    }

    public String toFileString() {
        return name + " | " + animeList.toString();
    }
}
