package ru.prplhd.tennisscoreboard.util;

import lombok.experimental.UtilityClass;
import ru.prplhd.tennisscoreboard.exception.ValidationException;

import java.util.regex.Pattern;

@UtilityClass
public class Validator {

    /**
     * Rules for player name:
     * - Total length: 1..40 characters.
     * - Allowed characters: English letters (A-Z, a-z), space, hyphen (-), apostrophe ('), dot (.).
     * - The name must start and end with an English letter.
     * - Limits: at most 3 spaces, 3 hyphens, 3 apostrophes, and 3 dots in total (each counted independently).
     */
    private static final Pattern PLAYER_NAME_PATTERN = Pattern.compile(
            "^(?=.{2,40}$)"
                + "(?!.*(?:[^ ]* ){4})"
                + "(?!.*(?:[^-]*-){4})"
                + "(?!.*(?:[^']*'){4})"
                + "(?!.*(?:[^.]*\\.){4})"
                + "[A-Za-z](?:[A-Za-z .'-]*[A-Za-z])?$"
    );
    public static void validatePlayerName(String name) {
        if (!PLAYER_NAME_PATTERN.matcher(name).matches()) {
            throw new ValidationException("Player name must be 1–40 chars, start and end with a letter. Allowed: A–Z/a–z, space, hyphen (-), apostrophe ('), dot (.). Max 3 of each separator.");
        }
    }
}
