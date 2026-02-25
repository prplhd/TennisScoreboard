package ru.prplhd.tennisscoreboard.service;

import lombok.RequiredArgsConstructor;
import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;
import ru.prplhd.tennisscoreboard.dto.request.NewMatchRequestDto;
import ru.prplhd.tennisscoreboard.repository.OngoingMatchRepository;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

import java.util.UUID;

@RequiredArgsConstructor
public class OngoingMatchServiceImpl implements OngoingMatchService{

    private final PlayerRepository playerRepository;
    private final OngoingMatchRepository ongoingMatchRepository;

    public UUID createNewMatch(NewMatchRequestDto newMatchRequestDto) {
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        String secondPlayerName = newMatchRequestDto.secondPlayerName();

        Player firstPlayer = getOrCreateByName(firstPlayerName);
        Player secondPlayer = getOrCreateByName(secondPlayerName);

        UUID matchUUID = UUID.randomUUID();
        Match match = new Match(firstPlayer, secondPlayer);

        ongoingMatchRepository.save(matchUUID, match);

        return matchUUID;
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
