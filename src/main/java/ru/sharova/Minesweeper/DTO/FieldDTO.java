package ru.sharova.Minesweeper.DTO;

import lombok.Data;

@Data
public class FieldDTO {
    private String game_id = "01234567-89AB-CDEF-0123-456789ABCDEF";
    private Integer width;
    private Integer height;
    private Integer mines_count;
    private boolean isCompleted;
    private String [][] field;
}
