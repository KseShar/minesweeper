package ru.sharova.Minesweeper.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class GameEntity {

    @Min(value = 2, message = "Значение поля должно быть больше 2")
    @Max(value = 30,message = "Значение поля должно быть меньше 30")
    private int width;

    @Min(value = 2, message = "Значение поля должно быть больше 2")
    @Max(value = 30,message = "Значение поля должно быть меньше 30")
    private int height;
    private int mines_count;
}
