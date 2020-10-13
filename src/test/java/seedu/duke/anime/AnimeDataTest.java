package seedu.duke.anime;

import org.junit.jupiter.api.Test;
import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimeDataTest {

    @Test
    void getAnime_negativeInteger_expectException() throws IndexOutOfBoundsException {
        ArrayList<Anime> animeStorageList = new ArrayList<>();
        String[] releaseDate = {"2020", "12", "30"};
        String[] genre = {"Science", "Action", "Dance"};
        String animeName = "Adventures of Adventurers";
        int rating = 65;
        int avgEpisodeLength = 30;
        int totalEpisodes = 24;
        Anime testAnime = new Anime(animeName, releaseDate, rating, genre, avgEpisodeLength, totalEpisodes);

        String[] releaseDate2 = {"1997", "4", "15"};
        String[] genre2 = {"Adventure", "Action", "Dance"};
        String animeName2 = "Actions in Action";
        int rating2 = 80;
        int avgEpisodeLength2 = 20;
        int totalEpisodes2 = 13;
        Anime testAnime2 = new Anime(animeName2, releaseDate2, rating2, genre2, avgEpisodeLength2, totalEpisodes2);

        animeStorageList.add(testAnime);
        animeStorageList.add(testAnime2);
        AnimeData animeData = new AnimeData(animeStorageList);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            animeData.getAnime(-1);
        });
    }

    @Test
    void getAnime_nullInput_expectException() throws NullPointerException {
        ArrayList<Anime> animeStorageList = new ArrayList<>();
        String[] releaseDate = {"2020", "12", "30"};
        String[] genre = {"Science", "Action", "Dance"};
        String animeName = "Adventures of Adventurers";
        int rating = 65;
        int avgEpisodeLength = 30;
        int totalEpisodes = 24;
        Anime testAnime = new Anime(animeName, releaseDate, rating, genre, avgEpisodeLength, totalEpisodes);

        String[] releaseDate2 = {"1997", "4", "15"};
        String[] genre2 = {"Adventure", "Action", "Dance"};
        String animeName2 = "Actions in Action";
        int rating2 = 80;
        int avgEpisodeLength2 = 20;
        int totalEpisodes2 = 13;
        Anime testAnime2 = new Anime(animeName2, releaseDate2, rating2, genre2, avgEpisodeLength2, totalEpisodes2);

        animeStorageList.add(testAnime);
        animeStorageList.add(testAnime2);
        AnimeData animeData = new AnimeData(animeStorageList);

        assertThrows(NullPointerException.class, () -> {
            animeData.getAnime(null);
        });
    }
}