package ru.prplhd.tennisscoreboard.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletContextKeys {

    public static final String SESSION_FACTORY = "sessionFactory";

    public static final String MATCH_REPOSITORY = "matchRepository";
    public static final String PLAYER_REPOSITORY = "playerRepository";
    public static final String ONGOING_MATCH_REPOSITORY = "ongoingMatchRepository";

    public static final String ONGOING_MATCH_SERVICE = "ongoingMatchService";
    public static final String FINISHED_MATCHES_PERSISTENCE_SERVICE = "finishedMatchesPersistenceService";
}
