package com.caske2000.javakub.game;

import com.caske2000.javakub.JavaKub;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caske2000 on 04/08/2015.
 */
public class Rules
{
    public static boolean checkValidGroup(List<Tile> tileGroup)
    {
        JavaKub.getInstance().log("Checking tilegroup...");
        if (tileGroup.size() < 3)
            return false;

        Color firstColor = tileGroup.get(0).getColor();
        for (Tile tile : tileGroup)
        {
            if (tile.getColor() != firstColor)
                return checkSameNumber(tileGroup);
        }
        return checkNormalGroup(tileGroup);
    }

    private static boolean checkSameNumber(List<Tile> tileGroup)
    {
        int firstNumber = tileGroup.get(0).getNumber();
        for (Tile tile : tileGroup)
        {
            if (tile.getNumber() != firstNumber)
                return false;
        }

        List<Color> colors = new ArrayList<>();
        for (Tile tile : tileGroup)
        {
            if (colors.contains(tile.getColor()))
                return false;
            colors.add(tile.getColor());
        }
        JavaKub.getInstance().log("Tilegroup is valid!\n");
        return true;
    }

    private static boolean checkNormalGroup(List<Tile> tileGroup)
    {
        int firstNumber = tileGroup.get(0).getNumber();
        for (int i = 1; i < tileGroup.size(); i++)
        {
            if (tileGroup.get(i).getNumber() - i != firstNumber)
                return false;
        }
        JavaKub.getInstance().log("Tilegroup is valid!\n");
        return true;
    }
}
