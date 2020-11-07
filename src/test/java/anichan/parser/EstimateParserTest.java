package anichan.parser;

import anichan.exception.AniException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author OngDeZhi
public class EstimateParserTest {
    private EstimateParser estimateParser;

    @BeforeEach
    void setUp() {
        estimateParser = new EstimateParser();
    }

    @Test
    void parse_validParametersWithoutWordsPerHour_success() {
        assertDoesNotThrow(() -> {
            estimateParser.parse("script.txt");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script.txt  ");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script-wph.txt");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script.txt.txt");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script a b c d.txt");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script a b c d .txt");
        });
    }

    @Test
    void parse_validParametersWithWordsPerHour_success() {
        assertDoesNotThrow(() -> {
            estimateParser.parse("script.txt -wph 777");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script-wph.txt -wph 777");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script.txt.txt -wph 777");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script a b c d.txt -wph 777");
        });

        assertDoesNotThrow(() -> {
            estimateParser.parse("script a b c d .txt -wph 777");
        });
    }

    @Test
    void parse_invalidScriptFile_throwsAniException() {
        assertThrows(AniException.class, () -> estimateParser.parse(""));
        assertThrows(AniException.class, () -> estimateParser.parse("script"));
        assertThrows(AniException.class, () -> estimateParser.parse("script."));
        assertThrows(AniException.class, () -> estimateParser.parse("script.tx"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt-wph 777"));
        assertThrows(AniException.class, () -> estimateParser.parse("/path/to/script.txt"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt helloworld"));
    }

    @Test
    void parse_scriptFileTooMuchFields_throwsAniException() {
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt .txt"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt .txt -wph 777"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt script_two.txt"));
    }

    @Test
    void parse_invalidParameter_throwsAniException() {
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -o"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -   wph 700"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -one -two"));
    }

    @Test
    void parse_invalidWordsPerHour_throwsAniException() {
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph one"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph 0"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph -8"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph 777 0"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph 123 -two"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph 999999999999"));
    }
}
