package seedu.duke;

import java.util.ArrayList;

public class Bookmark {
    private final ArrayList<String> animeBookmark;
    private final ArrayList<Integer> animeEpisode;

    public Bookmark() {
        this.animeBookmark = new ArrayList<>();
        this.animeEpisode = new ArrayList<>();
    }

    public Bookmark(ArrayList<String> animeBookmark, ArrayList<Integer> animeEpisode) {
        this.animeBookmark = animeBookmark;
        this.animeEpisode = animeEpisode;
    }

    public void addAnimeBookmark(String animeName) {
        this.animeBookmark.add(animeName);
        this.animeEpisode.add(-1);
    }

    public void removeAnimeBookmark(int bookmarkIndex) {
        this.animeBookmark.remove(bookmarkIndex);
        this.animeEpisode.remove(bookmarkIndex);
    }

    public void editAnimeBookmarkEpisode(int bookmarkIndex, int episode) {
        this.animeEpisode.set(bookmarkIndex, episode);
    }

    public String getAnimeBookmarkByIndex(Integer animeIndex) {
        return this.animeBookmark.get(animeIndex);
    }

    public String animeListInString() {
        StringBuilder sbAnimeList = new StringBuilder(System.lineSeparator());
        if (animeBookmark.size() == 0) {
            sbAnimeList.append("Uhh.. It's empty.. :(");
            sbAnimeList.append(System.lineSeparator());
        }
        for (int i = 0; i < animeBookmark.size(); i++) {
            sbAnimeList.append(i + 1);
            sbAnimeList.append(". ");
            sbAnimeList.append(animeBookmark.get(i));
            if (animeEpisode.get(i) != -1) {
                sbAnimeList.append(" Ep: ");
                sbAnimeList.append(animeEpisode.get(i));
            }
            sbAnimeList.append(System.lineSeparator());
        }
        return sbAnimeList.toString();
    }

    @Override
    public String toString() {
        return animeListInString();
    }
}