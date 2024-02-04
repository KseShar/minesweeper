package ru.sharova.Minesweeper.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sharova.Minesweeper.Repositories.FieldRepository;
import ru.sharova.Minesweeper.models.GameEntity;
import ru.sharova.Minesweeper.models.TurnEntity;
@Service
@RequiredArgsConstructor
public class MinesweeperService {
    @Autowired
    private final FieldRepository fieldRepository;

    public FieldRepository createResponse(GameEntity gameEntity) {
        //int hash = generateId();
        fieldRepository.setWidth(gameEntity.getWidth());
        fieldRepository.setHeight(gameEntity.getHeight());
        fieldRepository.setMines_count(gameEntity.getMines_count());
        fieldRepository.createField(gameEntity.getWidth(), gameEntity.getHeight(), gameEntity.getMines_count());
        return fieldRepository;
    }
    public FieldRepository createResponseTurn(TurnEntity turnEntity) {
        //int hash = generateId();
        //FieldEntity fieldEntity = new FieldEntity();
        //fieldRepository.setWidth(turnEntity.getRow());
        //fieldRepository.setHeight(turnEntity.getCol());
        //fieldRepository.setMines_count(gameEntity.getMines_count());
        //fieldEntity.setCompleted(gameEntity.isCompleted());
        fieldRepository.createFieldTurn(turnEntity.getRow(), turnEntity.getCol());

        return fieldRepository;
    }
}
