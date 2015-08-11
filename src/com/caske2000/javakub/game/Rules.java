package com.caske2000.javakub.game;

import com.caske2000.javakub.GUI.JTileHolder;
import com.caske2000.javakub.JavaKub;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caske2000 on 04/08/2015.
 */
public class Rules
{
    public static boolean checkValidGroup(JTileHolder[][] gameTilePanel, int y)
    {
        JavaKub.getInstance().log("Checking tilegroup...");
        if (gameTilePanel[3][y] == null)
            return false;

        Color firstColor = gameTilePanel[0][y].getTile().getColor();
        for (int x = 1; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (gameTilePanel[x][y].getTile().getColor() != firstColor)
                    return checkSameNumber(gameTilePanel, y);
            }
            return checkNormalGroup(gameTilePanel, y);
        }
        return false;
    }

    private static boolean checkSameNumber(JTileHolder[][] gameTilePanel, int y)
    {
        int firstNumber = gameTilePanel[0][y].getTile().getNumber();
        for (int x = 1; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (gameTilePanel[x][y].getTile().getNumber() != firstNumber)
                    return false;
            }
        }
        List<Color> colors = new ArrayList<>();
        for (int x = 0; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (colors.contains(gameTilePanel[x][y].getTile().getColor()))
                    return false;
                colors.add(gameTilePanel[x][y].getTile().getColor());
            }
        }
        JavaKub.getInstance().log("Tilegroup is valid!\n");
        return true;
    }

    private static boolean checkNormalGroup(JTileHolder[][] gameTilePanel, int y)
    {
        int firstNumber = gameTilePanel[0][y].getTile().getNumber();
        for (int x = 1; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (gameTilePanel[x][y].getTile().getNumber() - x != firstNumber)
                    return false;
            }
        }
        JavaKub.getInstance().log("Tilegroup is valid!\n");
        return true;
    }
}
