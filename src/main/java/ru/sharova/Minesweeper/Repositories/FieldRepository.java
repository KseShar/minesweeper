package ru.sharova.Minesweeper.Repositories;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.sharova.Minesweeper.util.InvalidDataException;

import java.awt.*;
import java.util.Random;

import static java.util.UUID.randomUUID;

@Repository
@Data
public class FieldRepository {
    private String game_id;
    private int width;
    private int height;
    private int mines_count;
    private boolean isCompleted;
    private Integer[][] fieldInt;
    private String [][] field;
    boolean[][] arrayNull;
    //создание минного поля
    public Integer[][] createField(int width, int height, int mine) {

        isCompleted = false;
        fieldInt = new Integer[width][height];
        field = new String[width][height];
        arrayNull = new boolean[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                fieldInt[i][j] = 0;
            }
        game_id = randomUUID().toString();//генерируем идентификатор игры
        //Создаем mine случайных координат
        Random r = new Random();
        int num = mine;
        do {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            if (fieldInt[x][y] != -1) {
                fieldInt[x][y] = -1;
                num--;
            }
        } while (num > 0);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                int numMinePoint = 0;
                if (fieldInt[i][j] != -1) {
                    Point[] p = this.getArrPoint(i, j);
                    for (int k = 0; k < p.length; k++) {
                        Point point = p[k];
                        if (point.getX() >= 0 && point.getX() < width && point.getY() >= 0 && point.getY() < height) {
                            if (fieldInt[point.x][point.y] == -1) {
                                numMinePoint++;
                            }
                        }
                    }
                    if (numMinePoint >= 0) {
                        fieldInt[i][j] = numMinePoint;
                    }
                }
            }
        for (int i = 0; i < width; i++)
             for (int j = 0; j < height; j++) {
                 field[i][j] = " ";
            }
        return fieldInt;
    }
    //обработка хода
    public String[][] createFieldTurn(int widthTurn, int heightTurn) {
        if (isCompleted)
            throw new InvalidDataException("Игра окончена!");

        if (!field[widthTurn][heightTurn].equals(" "))
            throw new InvalidDataException("Ячейка уже открыта!");
        //создаем массив для response
        if (fieldInt[widthTurn][heightTurn] == -1) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (fieldInt[i][j] == -1) field[i][j] = "X";
                    else field[i][j] = String.valueOf(fieldInt[i][j]);
                }
            }
            isCompleted = true;
        }
        else{
            if (fieldInt[widthTurn][heightTurn] == 0){
                arrayNull[widthTurn][heightTurn] = true;
                field[widthTurn][heightTurn] = "0";
                openNull(widthTurn, heightTurn);
                fillField();
            }
            else{
                field[widthTurn][heightTurn] = String.valueOf(fieldInt[widthTurn][heightTurn]);
                fillField();
            }
        }
    return field;
    }
    //обход 8-ми соседних точек
    private void around(int x, int y) {
        Point[] arr = this.getArrPoint(x, y);
        for (int k = 0; k < arr.length; k++) {
            Point point = arr[k];
            if (point.x >= 0 && point.x < width && point.y >= 0 && point.y < height && field[point.x][point.y].equals(" ")) {
                field[point.x][point.y] = String.valueOf(fieldInt[point.x][point.y]);
            }
        }
    }
    //обход поля в случае победы
    private void fillField() {
        int num_ = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field[i][j].equals(" ")) num_++;
            }
        }
        if (num_ == mines_count) {
            for (int k = 0; k < width; k++) {
                for (int l = 0; l < height; l++) {
                    if (fieldInt[k][l] == -1) field[k][l] = "M";
                    else field[k][l] = String.valueOf(fieldInt[k][l]);
                }
            }
            isCompleted = true;
            //game_id = " ";
        }
    }
    //открытие всех смежных нулей
    private void openNull(int x, int y) {
        around(x, y);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                 if (field[i][j].equals("0") && arrayNull[i][j] == false) {
                     arrayNull[i][j] = true;
                     around(i, j);
                     i = 0;
                     j = 0;
                 }
            }
        }
    }
    //создание массива координат 8-ми соседних точек
    private Point[] getArrPoint(int x, int y) {
        Point point[] = new Point[8];
        // вверх
        point[0] = new Point(x, y - 1);
        // вниз
        point[1] = new Point(x, y + 1);
        // осталось
        point[2] = new Point(x - 1, y);
        // право
        point[3] = new Point(x + 1, y);
        // левая нижняя
        point[4] = new Point(x - 1, y + 1);
        // Нижний правый
        point[5] = new Point(x + 1, y + 1);
        // верхний левый
        point[6] = new Point(x - 1, y - 1);
        // в правом верхнем углу
        point[7] = new Point(x + 1, y - 1);
        return point;
    }
}




