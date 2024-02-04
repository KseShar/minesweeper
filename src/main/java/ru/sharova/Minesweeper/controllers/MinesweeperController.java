package ru.sharova.Minesweeper.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sharova.Minesweeper.DTO.FieldDTO;
import ru.sharova.Minesweeper.Repositories.FieldRepository;
import ru.sharova.Minesweeper.models.GameEntity;
import ru.sharova.Minesweeper.models.TurnEntity;
import ru.sharova.Minesweeper.services.MinesweeperService;
import ru.sharova.Minesweeper.util.GameEntityValidator;
import ru.sharova.Minesweeper.util.InvalidDataException;

import static ru.sharova.Minesweeper.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequiredArgsConstructor
public class MinesweeperController {
    private final MinesweeperService service;
    private final ModelMapper modelMapper;
    private final GameEntityValidator gameEntityValidator;
    @GetMapping()
    public String sayHello(){
        return "Hello world!";
    }
    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<FieldDTO> createNewGame(@RequestBody @Valid GameEntity gameEntity, BindingResult bindingResult){

        gameEntityValidator.validate(gameEntity, bindingResult);
        if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);

        return ResponseEntity.ok(convertToFieldDTO(service.createResponse(gameEntity)));
    }
    @ExceptionHandler
    public ResponseEntity<String> handleException(InvalidDataException e){
        String error = e.getMessage();
        return new ResponseEntity<>("{\"error\": \"" + error + "\"}", HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @PostMapping("/turn")
    public ResponseEntity<FieldDTO> makeTurn(@RequestBody TurnEntity turnEntity, BindingResult bindingResult){
        if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);

        return ResponseEntity.ok(convertToFieldDTO(service.createResponseTurn(turnEntity)));
    }
    private FieldDTO convertToFieldDTO(FieldRepository fieldRepository){
        FieldDTO fieldDTO = modelMapper.map(fieldRepository, FieldDTO.class);
        return fieldDTO;
    }
}
