package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrowseCommandTest {
    AnimeData animeData;
    UserManagement userManagement;

    protected static final String INVALID_PARAMETERS_TEST1 = "-n name";
    protected static final String INVALID_PARAMETERS_TEST2 = "-sort name";
    protected static final String INVALID_FIELD_TEST1 = "-s   ";
    protected static final String INVALID_FIELD_TEST2 = "-s beepboopbeep";
    protected static final String INVALID_FIELD_TEST3 = "-s -o -p";
    protected static final String LARGE_PAGE_NUM = "-p 9999";
    protected static final String NEGATIVE_PAGE_NUM = "-p -1";
    protected static final String ZERO_PAGE_NUM = "-p 0";
    protected static final String DIFF_ORDER_TEST = "-p 1 -s rating -o asc";
    protected static final String DIFF_ORDER_TEST2 = "-s rating -o asc -p 1";
    protected static final String NO_PARAM_TEST = "";

    @BeforeEach
    void setUp() {
        ArrayList<Anime> testList = new ArrayList<Anime>();
        Anime testAnime1 = new Anime();
        Anime testAnime2 = new Anime();
        testList.add(testAnime1);
        testList.add(testAnime2);
        animeData = new AnimeData(testList);

    }

    @Test
    void execute_invalidParameter_ThrowsAniException() {
        BrowseCommand testBrowse = new BrowseCommand(INVALID_PARAMETERS_TEST1);
        assertThrows(AniException.class, () -> {
            testBrowse.execute(animeData, userManagement);
        });

        BrowseCommand testBrowse2 = new BrowseCommand(INVALID_PARAMETERS_TEST2);
        assertThrows(AniException.class, () -> {
            testBrowse2.execute(animeData, userManagement);
        });
    }

    @Test
    void execute_invalidField_ThrowsAniException() {
        BrowseCommand testBrowse = new BrowseCommand(INVALID_FIELD_TEST1);
        assertThrows(AniException.class, () -> {
            testBrowse.execute(animeData, userManagement);
        });

        BrowseCommand testBrowse2 = new BrowseCommand(INVALID_FIELD_TEST2);
        assertThrows(AniException.class, () -> {
            testBrowse2.execute(animeData, userManagement);
        });

        BrowseCommand testBrowse3 = new BrowseCommand(INVALID_FIELD_TEST3);
        assertThrows(AniException.class, () -> {
            testBrowse3.execute(animeData, userManagement);
        });
    }

    @Test
    void execute_invalidPageNum_ThrowsAniException() {
        BrowseCommand testBrowse = new BrowseCommand(LARGE_PAGE_NUM);
        assertThrows(AniException.class, () -> {
            testBrowse.execute(animeData, userManagement);
        });

        BrowseCommand testBrowse2 = new BrowseCommand(NEGATIVE_PAGE_NUM);
        assertThrows(AniException.class, () -> {
            testBrowse2.execute(animeData, userManagement);
        });

        BrowseCommand testBrowse3 = new BrowseCommand(ZERO_PAGE_NUM);
        assertEquals(testBrowse3.getPage(), 1);
    }

    @Test
    void execute_differentParameterOrder_identicalBrowseSettings() {
        BrowseCommand testBrowse = new BrowseCommand(DIFF_ORDER_TEST);
        BrowseCommand testBrowse2 = new BrowseCommand(DIFF_ORDER_TEST2);

        assertEquals(testBrowse.getPage(), testBrowse2.getPage());
        assertEquals(testBrowse.getSortType(), testBrowse2.getSortType());
        assertEquals(testBrowse.getOrder(), testBrowse2.getOrder());

    }

    @Test
    void execute_noParam_identicalBrowseSettings() {
        BrowseCommand testBrowse = new BrowseCommand(NO_PARAM_TEST);

        //Performs test against default settings
        assertEquals(testBrowse.getPage(), 1);
        assertEquals(testBrowse.getSortType(), 0);
        assertEquals(testBrowse.getOrder(), 1);
    }
}