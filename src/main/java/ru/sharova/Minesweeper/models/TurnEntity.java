package ru.sharova.Minesweeper.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurnEntity {
    private String game_id;
    private Integer row;
    private Integer col;
}
