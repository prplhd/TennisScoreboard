package ru.prplhd.tennisscoreboard.service;

import lombok.RequiredArgsConstructor;
import ru.prplhd.tennisscoreboard.dto.match.FinishedMatchesDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchDto;
import ru.prplhd.tennisscoreboard.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class FinishedMatchesPersistenceServiceImpl implements FinishedMatchesPersistenceService {
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;


    @Override
    public List<FinishedMatchesDto> findAllMatches() {
        List<FinishedMatchesDto> finishedMatchesDtos = new ArrayList<>();
        List<MatchEntity> matches = matchRepository.findAll();

        for (MatchEntity match : matches) {
            FinishedMatchesDto finishedMatchesDto = new FinishedMatchesDto(
                    match.getPlayer1().getName(),
                    match.getPlayer2().getName(),
                    match.getWinner().getName()
            );

            finishedMatchesDtos.add(finishedMatchesDto);
        }

        return finishedMatchesDtos;
    }

    @Override
    public void saveMatch(MatchDto matchDto) {
        PlayerEntity firstPlayer = playerRepository.findPlayerByName(matchDto.firstPlayer().getName())
                .orElseThrow(RuntimeException::new);

        PlayerEntity secondPlayer = playerRepository.findPlayerByName(matchDto.secondPlayer().getName())
                .orElseThrow(RuntimeException::new);

        Integer winnerId = matchDto.winner().getId();
        PlayerEntity winner = Objects.equals(winnerId, firstPlayer.getId()) ? firstPlayer : secondPlayer;

        MatchEntity matchEntity = MatchEntity.builder()
                .player1(firstPlayer)
                .player2(secondPlayer)
                .winner(winner)
                .build();

        matchRepository.save(matchEntity);
    }
}
