package com.denisacosta.lgame.Component;

/*
Clase POJO para manejar las coordenas dentro del juego
 */
public class Coord {
    int x;
    int y;

    public Coord() {
        this.x = 100;
        this.y = 100;
    }

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
