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
        int count = 0;
        int xFirstTile = -1;
        for (int x = 0; x < JavaKub.getInstance().boardWidth; x++)
        {
            if (gameTilePanel[x][y].getTile() != null && !gameTilePanel[x][y].getTile().isJoker())
            {
                if (xFirstTile == -1)
                    xFirstTile = x;
                count++;
            }
        }
        if (count < 3)
            return false;
        Color firstColor = gameTilePanel[xFirstTile][y].getTile().isJoker() ? gameTilePanel[xFirstTile + 1][y].getTile().getColor() : gameTilePanel[xFirstTile][y].getTile().getColor();
        for (int x = xFirstTile + 1; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (!gameTilePanel[x][y].getTile().isJoker())
                {
                    if (gameTilePanel[x][y].getTile().getColor() != firstColor)
                        return checkSameNumber(gameTilePanel, xFirstTile, y);
                }
            }
            return checkNormalGroup(gameTilePanel, xFirstTile, y);
        }
        return false;
    }

    private static boolean checkSameNumber(JTileHolder[][] gameTilePanel, int xFirstTile, int y)
    {
        int firstNumber = gameTilePanel[xFirstTile][y].getTile().isJoker() ? gameTilePanel[xFirstTile + 1][y].getTile().getNumber() : gameTilePanel[xFirstTile][y].getTile().getNumber();
        for (int x = xFirstTile + 1; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (gameTilePanel[x][y].getTile().getNumber() != firstNumber && !gameTilePanel[x][y].getTile().isJoker())
                    return false;
            }
        }
        List<Color> colors = new ArrayList<>();
        for (int x = xFirstTile; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (colors.contains(gameTilePanel[x][y].getTile().getColor()) && !gameTilePanel[x][y].getTile().isJoker())
                    return false;
                if (!gameTilePanel[x][y].getTile().isJoker())
                    colors.add(gameTilePanel[x][y].getTile().getColor());
            }
        }
        JavaKub.getInstance().log("Tilegroup is valid!\n");
        return true;
    }

    private static boolean checkNormalGroup(JTileHolder[][] gameTilePanel, int xFirstTile, int y)
    {
        for (int x = xFirstTile; x < 13; x++)
        {
            if (gameTilePanel[x][y].getTile() != null)
            {
                if (!gameTilePanel[x][y].getTile().isJoker())
                {
                    if (gameTilePanel[x][y].getTile().getNumber() - x != 1)
                        return false;
                }
            }
        }
        JavaKub.getInstance().log("Tilegroup is valid!\n");
        return true;
    }
}
