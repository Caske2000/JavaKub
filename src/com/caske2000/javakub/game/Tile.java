package com.caske2000.javakub.game;

import java.awt.*;

/**
 * Created by Caske2000 on 04/08/2015.
 */
public class Tile
{
    private Color color;
    private int number;
    private boolean isJoker;

    public Tile(Color color, int number, boolean isJoker)
    {
        if (!isJoker)
        {
            if (number <= 0 || number > 13)
                throw new IndexOutOfBoundsException("Your number is out of bounds, number found: " + number);
            if (color != Color.BLACK && color != Color.BLUE && color != Color.RED && color != Color.ORANGE || isJoker)
                throw new IndexOutOfBoundsException("This color does not exist in the game, color found: " + color);
        }
        this.color = color;
        this.number = number;
        this.isJoker = isJoker;
    }

    public Color getColor()
    {
        return color;
    }

    public String getColorName()
    {
        if (color.equals(Color.BLACK))
            return "BLACK";
        else if (color.equals(Color.BLUE))
            return "BLUE";
        else if (color.equals(Color.RED))
            return "RED";
        else
            return "ORANGE";
    }

    public int getNumber()
    {
        return number;
    }

    public boolean isJoker()
    {
        return isJoker;
    }
}
