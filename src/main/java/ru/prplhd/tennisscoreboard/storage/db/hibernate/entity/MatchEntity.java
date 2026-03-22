package ru.prplhd.tennisscoreboard.storage.db.hibernate.entity;

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
        this.player1 = firstPlayer;
        this.player2 = secondPlayer;
        this.winner = winner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "player1")
    private PlayerEntity player1;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "player2", updatable = false)
    private PlayerEntity player2;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "winner", updatable = false)
    private PlayerEntity winner;
}
