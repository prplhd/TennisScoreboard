package ru.prplhd.tennisscoreboard.service;

import lombok.RequiredArgsConstructor;
import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;
import ru.prplhd.tennisscoreboard.domain.snapshot.MatchSnapshot;
import ru.prplhd.tennisscoreboard.dto.NewMatchRequestDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchPlayerIdsDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchScoreboardDto;
import ru.prplhd.tennisscoreboard.exception.NotFoundException;
import ru.prplhd.tennisscoreboard.exception.ValidationException;
import ru.prplhd.tennisscoreboard.repository.OngoingMatchRepository;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;
import ru.prplhd.tennisscoreboard.util.Validator;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class OngoingMatchServiceImpl implements OngoingMatchService{

    private final PlayerRepository playerRepository;
    private final OngoingMatchRepository ongoingMatchRepository;

    @Override
    public UUID createNewMatch(NewMatchRequestDto newMatchRequestDto) {
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        Validator.validatePlayerName(firstPlayerName);

        String secondPlayerName = newMatchRequestDto.secondPlayerName();
        Validator.validatePlayerName(secondPlayerName);

        if (Objects.equals(firstPlayerName, secondPlayerName)) {
            throw new ValidationException("Player names must be different");
        }

        Player firstPlayer = getOrCreateByName(firstPlayerName);
        Player secondPlayer = getOrCreateByName(secondPlayerName);

        UUID matchUUID = UUID.randomUUID();
        Match match = new Match(firstPlayer, secondPlayer);

        ongoingMatchRepository.save(matchUUID, match);

        return matchUUID;
    }

    @Override
    public MatchSnapshot getMatchScoreboard(UUID matchUUID) {
        Match match = ongoingMatchRepository.findById(matchUUID);

        return match.getSnapshot();
    }

    @Override
    public MatchSnapshot applyPoint(UUID matchUUID, Integer scorerId) {
        PlayerEntity playerEntity = playerRepository.findById(scorerId)
                .orElseThrow(() -> new NotFoundException("Player with id = '" + scorerId + "' does not belong to this match"));

        Player scorer = new Player(playerEntity.getName());

        return ongoingMatchRepository.applyPoint(matchUUID, scorer).getSnapshot();
    }

    @Override
    public void deleteMatch(UUID matchUUID) {
        ongoingMatchRepository.delete(matchUUID);
    }

    @Override
    public MatchPlayerIdsDto getPlayerIds(MatchScoreboardDto matchScoreboardDto) {
        String firstPlayerName = matchScoreboardDto.firstPlayer();
        int firstPlayerId = playerRepository.findPlayerByName(matchScoreboardDto.firstPlayer())
                .orElseThrow(() -> new NotFoundException("Player with name '" + firstPlayerName + "' not found")).getId();

        String secondPlayerName = matchScoreboardDto.secondPlayer();
        int secondPlayerId = playerRepository.findPlayerByName(matchScoreboardDto.secondPlayer())
                .orElseThrow(() -> new NotFoundException("Player with name '" + secondPlayerName + "' not found")).getId();

        return new MatchPlayerIdsDto(firstPlayerId, secondPlayerId);
    }

    private Player getOrCreateByName(String name) {
        PlayerEntity playerEntity = playerRepository.findPlayerByName(name)
                .orElseGet(() -> playerRepository.save(new PlayerEntity(name)));

        return new Player(playerEntity.getName());
    }
}
