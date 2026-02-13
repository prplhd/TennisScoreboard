package ru.prplhd.tennisscoreboard.persistence.repository;

import ru.prplhd.tennisscoreboard.persistence.entity.Match;

import java.util.List;

public interface MatchRepository extends Repository<Integer, Match> {
    List<Match> findMatchesByPlayerName(String name);
}