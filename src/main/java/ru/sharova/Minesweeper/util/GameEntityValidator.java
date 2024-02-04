package ru.sharova.Minesweeper.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sharova.Minesweeper.models.GameEntity;
import ru.sharova.Minesweeper.services.MinesweeperService;

@Component
@RequiredArgsConstructor
public class GameEntityValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return GameEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GameEntity gameEntity = (GameEntity) o;
        int numOfCells = gameEntity.getWidth() * gameEntity.getHeight();
        if (gameEntity.getMines_count() > (gameEntity.getWidth() * gameEntity.getHeight() - 1) || gameEntity.getMines_count() < 1)
            errors.rejectValue("Mines_count", "1",
                    "Количество мин должно быть больше одного и строго меньше " + numOfCells);
    }

}
