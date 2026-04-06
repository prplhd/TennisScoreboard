package ru.prplhd.tennisscoreboard.service;

import lombok.RequiredArgsConstructor;
import ru.prplhd.tennisscoreboard.dto.match.FinishedMatchDto;
import ru.prplhd.tennisscoreboard.dto.FinishedMatchesPageDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchScoreboardDto;
import ru.prplhd.tennisscoreboard.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;
import ru.prplhd.tennisscoreboard.util.PaginationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class FinishedMatchesPersistenceServiceImpl implements FinishedMatchesPersistenceService {
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    @Override
    public FinishedMatchesPageDto getMatchesPage(String pageParameterValue) {
        int matchesCount = Math.toIntExact(matchRepository.countAll());
        int totalPages = PaginationHelper.getTotalPages(matchesCount);

        int page = PaginationHelper.getPage(pageParameterValue, totalPages);

        int offset = PaginationHelper.getOffset(page);

        List<MatchEntity> matches = matchRepository.findMatches(offset, PaginationHelper.DEFAULT_PAGE_LIMIT_SIZE);
        List<FinishedMatchDto> matchesDtos = toDtos(matches);

        Map<String, Integer> pagesWindow = PaginationHelper.getPagesWindow(page, totalPages);

        return new FinishedMatchesPageDto(
                matchesDtos,
                page,
                totalPages,
                pagesWindow.get("start"),
                pagesWindow.get("end")
        );
    }

    @Override
    public FinishedMatchesPageDto getMatchesPage(String pageParameterValue, String name) {
        int matchesCount = Math.toIntExact(matchRepository.countAllByPlayerName(name));
        int totalPages = PaginationHelper.getTotalPages(matchesCount);

        int page = PaginationHelper.getPage(pageParameterValue, totalPages);

        int offset = PaginationHelper.getOffset(page);

        List<MatchEntity> matches = matchRepository.findMatchesByName(offset, PaginationHelper.DEFAULT_PAGE_LIMIT_SIZE, name);
        List<FinishedMatchDto> matchesDtos = toDtos(matches);

        Map<String, Integer> pagesWindow = PaginationHelper.getPagesWindow(page, totalPages);

        return new FinishedMatchesPageDto(
                matchesDtos,
                page,
                totalPages,
                pagesWindow.get("start"),
                pagesWindow.get("end")
        );
    }

    @Override
    public void saveMatch(MatchScoreboardDto matchScoreboardDto) {
        PlayerEntity firstPlayer = playerRepository.findPlayerByName(matchScoreboardDto.firstPlayer())
                .orElseThrow(RuntimeException::new);

        PlayerEntity secondPlayer = playerRepository.findPlayerByName(matchScoreboardDto.secondPlayer())
                .orElseThrow(RuntimeException::new);

        String winnerName = matchScoreboardDto.winner();
        PlayerEntity winner = Objects.equals(winnerName, firstPlayer.getName()) ? firstPlayer : secondPlayer;

        MatchEntity matchEntity = new MatchEntity(firstPlayer, secondPlayer, winner);

        matchRepository.save(matchEntity);
    }

    private List<FinishedMatchDto> toDtos(List<MatchEntity> matches) {
        List<FinishedMatchDto> finishedMatchesDtos = new ArrayList<>();

        for (MatchEntity match : matches) {
            FinishedMatchDto finishedMatchesDto = new FinishedMatchDto(
                    match.getFirstPlayer().getName(),
                    match.getSecondPlayer().getName(),
                    match.getWinner().getName()
            );

            finishedMatchesDtos.add(finishedMatchesDto);
        }

        return finishedMatchesDtos;
    }
}
