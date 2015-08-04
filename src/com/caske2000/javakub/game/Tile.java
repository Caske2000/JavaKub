package com.caske2000.javakub.game;

import java.awt.*;

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
        if (color != Color.BLACK && color != Color.BLUE && color != Color.RED && color != Color.ORANGE)
            throw new IndexOutOfBoundsException("This color does not exist in the game, color found: " + color);
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
}
