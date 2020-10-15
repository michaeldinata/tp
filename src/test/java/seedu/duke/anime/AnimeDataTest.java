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
        animeStorageList.add(new Anime());
        animeStorageList.add(new Anime());
        AnimeData animeData = new AnimeData(animeStorageList);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            animeData.getAnime(-1);
        });
    }

    @Test
    void getAnime_nullInput_expectException() throws NullPointerException {
        ArrayList<Anime> animeStorageList = new ArrayList<>();
        animeStorageList.add(new Anime());
        animeStorageList.add(new Anime());
        AnimeData animeData = new AnimeData(animeStorageList);

        assertThrows(NullPointerException.class, () -> {
            animeData.getAnime(null);
        });
    }
}