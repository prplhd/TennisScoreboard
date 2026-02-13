package ru.prplhd.tennisscoreboard.web.bootstrap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextKeys {
    public static final String SESSION_FACTORY = "sessionFactory";
    public static final String MATCH_REPOSITORY = "matchRepository";
    public static final String PLAYER_REPOSITORY = "playerRepository";

}
