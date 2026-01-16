package ru.prplhd.tennisscoreboard.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "matches")
public class FinishedMatchEntity {
    @Id
    private Integer id;
    private Integer player1;
    private Integer player2;
    private Integer winner;

}
