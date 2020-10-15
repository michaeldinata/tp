package seedu.duke.bookmark;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;

import java.util.ArrayList;

public class Bookmark {
    private ArrayList<Integer> animeBookmarkList;
    private ArrayList<Integer> animeEpisode;

    public Bookmark() {
        this.animeEpisode = new ArrayList<>();
        this.animeBookmarkList = new ArrayList<>();
    }

    public Bookmark(ArrayList<Integer> animeBookmarkList, ArrayList<Integer> animeEpisode) {
        this.animeBookmarkList = animeBookmarkList;
        this.animeEpisode = animeEpisode;
    }

    public void addAnimeBookmark(Integer animeIndex) {
        this.animeBookmarkList.add(animeIndex);
        this.animeEpisode.add(-1);
    }

    public void removeAnimeBookmark(int bookmarkIndex) {
        this.animeBookmarkList.remove(bookmarkIndex);
        this.animeEpisode.remove(bookmarkIndex);
    }

    public void editAnimeBookmarkEpisode(int bookmarkIndex, int episode) {
        this.animeEpisode.set(bookmarkIndex, episode);
    }

    public Anime getAnimeBookmarkByIndex(AnimeData animeData, Integer bookmarkIndex) {
        int animeIndex = this.animeBookmarkList.get(bookmarkIndex);
        return animeData.getAnimeByID(animeIndex);
    }

    public int getBookmarkSize() {
        return animeBookmarkList.size();
    }

    public String animeListInString(AnimeData animeData) {
        StringBuilder sbAnimeList = new StringBuilder(System.lineSeparator());
        if (animeBookmarkList.size() == 0) {
            sbAnimeList.append("\tUhh.. It's empty.. :(");
            sbAnimeList.append(System.lineSeparator());
        }
        for (int i = 0; i < animeBookmarkList.size(); i++) {
            sbAnimeList.append("\t");
            sbAnimeList.append(i + 1);
            sbAnimeList.append(". ");
            int animeIndex = this.animeBookmarkList.get(i);
            sbAnimeList.append(animeData.getAnimeByID(animeIndex));
            if (animeEpisode.get(i) != -1) {
                sbAnimeList.append(" Ep: ");
                sbAnimeList.append(animeEpisode.get(i));
            }
            sbAnimeList.append(System.lineSeparator());
        }
        return sbAnimeList.toString();
    }

}