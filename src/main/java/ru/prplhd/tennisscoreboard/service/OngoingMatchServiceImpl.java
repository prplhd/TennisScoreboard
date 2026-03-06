package ru.prplhd.tennisscoreboard.service;

import lombok.RequiredArgsConstructor;
import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;
import ru.prplhd.tennisscoreboard.dto.match.ongoing.MatchDto;
import ru.prplhd.tennisscoreboard.dto.NewMatchRequestDto;
import ru.prplhd.tennisscoreboard.repository.OngoingMatchRepository;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;
import ru.prplhd.tennisscoreboard.util.Validator;

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

        Player firstPlayer = getOrCreateByName(firstPlayerName);
        Player secondPlayer = getOrCreateByName(secondPlayerName);

        UUID matchUUID = UUID.randomUUID();
        Match match = new Match(firstPlayer, secondPlayer);

        ongoingMatchRepository.save(matchUUID, match);

        return matchUUID;
    }

    @Override
    public MatchDto getMatchScoreboard(UUID matchUUID) {
        Match match = ongoingMatchRepository.findById(matchUUID);

        return match.getScoreboard();
    }

    @Override
    public MatchDto applyPoint(UUID matchUUID, Integer scorerId) {
        return ongoingMatchRepository.applyPoint(matchUUID, scorerId).getScoreboard();
    }

    @Override
    public void deleteMatch(UUID matchUUID) {
        ongoingMatchRepository.delete(matchUUID);
    }

    private Player getOrCreateByName(String name) {
        PlayerEntity playerEntity = playerRepository.findPlayerByName(name)
                .orElseGet(() -> playerRepository.save(
                        PlayerEntity.builder()
                                    .name(name)
                                    .build()
                        )
                );

        return new Player(playerEntity.getId(), playerEntity.getName());
    }
}
