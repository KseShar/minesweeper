package ru.sharova.Minesweeper.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class GameEntity {
    @NotEmpty(message = "Поле не должно быть пустым")
    @Min(value = 2, message = "Значение поля должно быть больше 2")
    @Max(value = 30,message = "Значение поля должно быть меньше 30")
    private int width;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Min(value = 2, message = "Значение поля должно быть больше 2")
    @Max(value = 30,message = "Значение поля должно быть меньше 30")
    private int height;
    @NotEmpty(message = "Поле не должно быть пустым")
    //@Min(2)
    //@Max(height)
    private int mines_count;
    /*public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMines_count() {
        return mines_count;
    }

    public void setMines_count(int mines_count) {
        this.mines_count = mines_count;
    }*/
}
