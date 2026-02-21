package ru.prplhd.tennisscoreboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Player {

    private final Integer id;

    private final String name;
}