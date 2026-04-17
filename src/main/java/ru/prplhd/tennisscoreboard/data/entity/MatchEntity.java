package ru.prplhd.tennisscoreboard.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = "id")
@Table(name = "finished_matches")
@Entity
public class MatchEntity {

    public MatchEntity (PlayerEntity firstPlayer, PlayerEntity secondPlayer, PlayerEntity winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "firstPlayer", updatable = false)
    private PlayerEntity firstPlayer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "secondPlayer", updatable = false)
    private PlayerEntity secondPlayer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "winner", updatable = false)
    private PlayerEntity winner;
}
