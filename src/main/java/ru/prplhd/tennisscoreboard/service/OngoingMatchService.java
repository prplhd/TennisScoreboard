package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.dto.request.NewMatchRequestDto;

import java.util.UUID;

public interface OngoingMatchService {
    UUID createNewMatch(NewMatchRequestDto newMatchRequestDto);
}
