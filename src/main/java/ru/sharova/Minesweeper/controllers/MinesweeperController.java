package ru.sharova.Minesweeper.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sharova.Minesweeper.DTO.FieldDTO;
import ru.sharova.Minesweeper.Repositories.FieldRepository;
import ru.sharova.Minesweeper.models.GameEntity;
import ru.sharova.Minesweeper.models.TurnEntity;
import ru.sharova.Minesweeper.services.MinesweeperService;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import ru.sharova.Minesweeper.services.MinesweeperService;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/")
public class MinesweeperController {
    private final MinesweeperService service;
    private final ModelMapper modelMapper;
    //private final GameEntity gameEntity;
    /*@GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash){
        return service.getByHash(hash);*/
    @GetMapping()
    public String sayHello(){
        return "Hello world!";
    }
    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<FieldDTO> createNewGame(@RequestBody GameEntity gameEntity, BindingResult bindingResult){
      //  if (bindingResult.hasErrors()) {
       //     StringBuilder errorMsg = new StringBuilder();

            /*List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());*/
            //if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);
        //}

        return ResponseEntity.ok(convertToFieldDTO(service.createResponse(gameEntity)));
    }
    @CrossOrigin
    @PostMapping("/turn")
    public ResponseEntity<FieldDTO> makeTurn(@RequestBody TurnEntity turnEntity, BindingResult bindingResult){
        //  if (bindingResult.hasErrors()) {
        //     StringBuilder errorMsg = new StringBuilder();

            /*List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());*/
        //if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);
        //}

        return ResponseEntity.ok(convertToFieldDTO(service.createResponseTurn(turnEntity)));
    }
    private FieldRepository convertToField(FieldDTO fieldDTO){
        FieldRepository fieldRepository = modelMapper.map(fieldDTO, FieldRepository.class);
        return fieldRepository;
    }

    private FieldDTO convertToFieldDTO(FieldRepository fieldRepository){
        FieldDTO fieldDTO = modelMapper.map(fieldRepository, FieldDTO.class);
        return fieldDTO;
    }


}
