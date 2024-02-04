package ru.sharova.Minesweeper.Repositories;

import lombok.Data;
import org.springframework.stereotype.Repository;

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
        //создаем массив для response
        if (!field[widthTurn][heightTurn].equals(" ")) return field;
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
    /*private void OpenButtons(int x, int y) {
        Queue<Point> toOpen = new LinkedBlockingQueue<>();
        toOpen.add(new Point(x, y));
        field[x][y] = String.valueOf(fieldInt[x][y]);
        while (!toOpen.isEmpty()) {
            Point p = toOpen.poll();
            x = p.x;
            y = p.y;
            for (int k = -1; k < 2; k++) {
                for (int k1 = 1; k1 >= -1; k1--) {
                    if (x + k >= 0 && x + k < width && y - k1 >= 0 && y - k1 < height)
                        if (fieldInt[x + k][y - k1] == 0) {
                            field[x + k][y - k1] = "0";
                            toOpen.add(new Point(x + k, y - k1));
                        }
                }
            }
        }
    }*/
    /*public void recFun(int x, int y) {
        field[x][y] = String.valueOf(fieldInt[x][y]);

        Point[] arr = this.getArrPoint(x, y);
        for (int k = 0; k < arr.length; k++) {
            Point point = arr[k];
            if (point.x >= 0 && point.x < width && point.y >= 0 && point.y < height && field[point.x][point.y] != " ") {
                field[point.x][point.y] = String.valueOf(fieldInt[point.x][point.y]);
                //if (fieldInt[point.x][point.y] == 0) {
                    recFun(point.x, point.y);
               // }
            }
            //else return;
        }}
        /*if (x < 0 || x >= width || y < 0 || y >= height || fieldInt[x][y] != 0) {
            return;
        }

        //visited[x][y] = true;
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < 8; i++) {
            field[x + dx[i]][y + dy[i]] = "0";
            recFun(x + dx[i], y + dy[i]);
        }

    }*/
  /*  public void openZeroNeighbors(int x, int y, int[][] board, boolean[][] opened) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            if (r < 0 || r >= width || c < 0 || c >= height || field[r][c] != " "  || field[r][c] != "0") {
                continue;
            }

            field[r][c] = String.valueOf(fieldInt[r][c]);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        int newRow = r + i;
                        int newCol = c + j;
                        if (newRow >= 0 && newRow < width && newCol >= 0 && newCol < height && field[newRow][newCol] == " ") {
                            queue.add(new int[]{newRow, newCol});
                        }
                    }
                }
            }
        }
    }*/


}




