package ru.sharova.Minesweeper.DTO;

import lombok.Data;

@Data
public class FieldDTO {
    private String game_id;
    private Integer width;
    private Integer height;
    private Integer mines_count;
    private boolean isCompleted;
    private String [][] field;
}
