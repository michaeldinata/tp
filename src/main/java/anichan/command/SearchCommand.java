package anichan.command;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchCommand extends Command {
    private static final String ASSERT_SEARCH_TERM_EMPTY = "Empty Search String!";
    private static final String ID_HEADER = "[ID:";
    private static final String ID_CLOSER = "] ";
    private static final String NO_RESULTS_FOUND = "No results found!";
    private static final String SEARCHING_BY_GENRE = "Searching By Genre";
    private static final String SEARCHING_BY_ANIME_NAME = "Searching By Anime Name";
    private static final String SEARCH_TYPE_INVALID = "Something went wrong with search input";
    private static final String SEARCH_TYPE_INVALID_LOG = "Search Type has the wrong values.";
    private static final int SEARCH_BY_NAME = 0;
    private static final int SEARCH_BY_GENRE = 1;
    private static final int NO_SEARCH_SELECTED = -1;

    private static final Logger LOGGER = AniLogger.getAniLogger(SearchCommand.class.getName());

    private String searchTerm;
    private String result;
    private String searchGenre;
    private int searchType;

    public SearchCommand() {
        searchTerm = "";
        searchGenre = "";
        result = "";
        searchType = NO_SEARCH_SELECTED;
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        switch (searchType) {
        case SEARCH_BY_NAME:
            searchForAnime(animeData);
            break;
        case SEARCH_BY_GENRE:
            searchForGenre(animeData);
            break;
        default:
            LOGGER.log(Level.SEVERE, SEARCH_TYPE_INVALID_LOG);
            throw new AniException(SEARCH_TYPE_INVALID);
        }

        if (result.isEmpty()) {
            return NO_RESULTS_FOUND;
        }
        return result;
    }

    private void searchForGenre(AnimeData animeData) {
        LOGGER.log(Level.INFO, SEARCHING_BY_GENRE);
        for (Anime anime : animeData.getAnimeDataList()) {
            if (Arrays.asList(anime.getGenre()).contains(searchGenre)) {
                result += ID_HEADER + anime.getAnimeID() + ID_CLOSER + anime.getAnimeName() + System.lineSeparator();
            }
        }
    }

    private void searchForAnime(AnimeData animeData) {
        LOGGER.log(Level.INFO, SEARCHING_BY_ANIME_NAME);
        for (Anime anime : animeData.getAnimeDataList()) {
            if (anime.getAnimeName().toLowerCase().contains(searchTerm)) {
                result += ID_HEADER + anime.getAnimeID() + ID_CLOSER + anime.getAnimeName() + System.lineSeparator();
            }
        }
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm.toLowerCase();
        this.searchType = SEARCH_BY_NAME;
    }

    public void setSearchGenre(String searchGenre) {
        this.searchGenre = searchGenre;
        this.searchType = SEARCH_BY_GENRE;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }
}
