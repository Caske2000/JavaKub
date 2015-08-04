package com.caske2000.javakub.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * Created by Caske2000 on 04/08/2015.
 */
public class Rules
{
    public static boolean checkValidGroup(List<Tile> tileGroup)
    {
        if (tileGroup.size() < 3)
            return false;

        Color firstColor = tileGroup.get(0).getColor();
        for (Tile tile: tileGroup)
        {
            if (tile.getColor() != firstColor)
                return checkSameNumber(tileGroup);
        }
        return checkNormalGroup(tileGroup);
    }

    public static boolean checkSameNumber(List<Tile> tileGroup)
    {
        int firstNumber = tileGroup.get(0).getNumber();
        for (Tile tile: tileGroup)
        {
            if (tile.getNumber() != firstNumber)
                return false;
        }

        List<Color> colors = new ArrayList<>();
        for (Tile tile: tileGroup)
        {
            if (colors.contains(tile.getColor()))
                return false;
            colors.add(tile.getColor());
        }
        return true;
    }

    public static boolean checkNormalGroup(List<Tile> tileGroup)
    {
        int firstNumber = tileGroup.get(0).getNumber();
        for (int i = 1; i < tileGroup.size(); i++)
        {
            if (tileGroup.get(i).getNumber() - i != firstNumber)
                return false;
        }
        return true;
    }
}
