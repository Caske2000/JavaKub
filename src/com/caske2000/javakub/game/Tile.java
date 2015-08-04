package com.caske2000.javakub.game;

/**
 * Created by Caske2000 on 04/08/2015.
 */
public class Tile
{
    private Color color;
    private int number;

    public Tile(Color color, int number)
    {
        if (number <= 0 || number > 13)
            throw new IndexOutOfBoundsException("Your number is out of bounds, number found: " + number);
        this.color = color;
        this.number = number;
    }

    public Color getColor()
    {
        return color;
    }

    public int getNumber()
    {
        return number;
    }

    public enum Color{
        RED, BLACK, ORANGE, BLUE
    }
}
