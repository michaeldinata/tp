package anichan.parser;

import anichan.anime.AnimeData;
import anichan.commands.SearchCommand;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.storage.StorageManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchParserTest {
    private static final String VALID_ANIME = "-n fate";
    private static final String VALID_GENRE = "-g Music";
    private static AnimeData animeData;
    private static StorageManager storageManager;
    private User user;

    private static final String INVALID_PARAMETERS_TEST1 = "-n ";
    private static final String INVALID_PARAMETERS_TEST2 = "-a Cowboy Bebop";
    private static final String OUTPUT_ANIME_1 = "[ID:323] Fate/stay night";
    private static final String OUTPUT_ANIME_2 = "[ID:39] Beck: Mongolian Chop Squad";

    @BeforeAll
    static void setUp() throws AniException {
        animeData = new AnimeData();
        storageManager = new StorageManager("");
    }

    @Test
    void parse_invalidParameter_throwsAniException() {
        SearchParser testParse = new SearchParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST1);
        });

        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST2);
        });
    }

    @Test
    void parse_validNameSearch_usableSearchCommand() throws AniException {
        SearchParser testParse = new SearchParser();
        SearchCommand testSearch = testParse.parse(VALID_ANIME);
        String result = testSearch.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ANIME_1 + System.lineSeparator(), result);
    }

    @Test
    void parse_validGenreSearch_usableSearchCommand() throws AniException {
        SearchParser testParse = new SearchParser();
        SearchCommand testSearch = testParse.parse(VALID_GENRE);
        String result = testSearch.execute(animeData, storageManager, user);
        System.out.println(result);
        result = result.substring(0,34);
        assertEquals(OUTPUT_ANIME_2, result);
    }

}
