package com.caske2000.javakub.GUI;

import com.caske2000.javakub.JavaKub;
import com.caske2000.javakub.game.Rules;
import com.caske2000.javakub.game.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class Display extends Canvas
{
    public void render(DefaultListModel model, JTileHolder[][] gameTilePanel, JTileHolder[][] myTilePanel)
    {
        model.clear();
        for (int y = 0; y < JavaKub.getInstance().boardHeight; y++)
        {
            if (gameTilePanel[0][y].getTile() == null)
                return;
            if (!Rules.checkValidGroup(gameTilePanel, y))
            {
                JavaKub.getInstance().log("Tilegroup isn't valid!\n");
            }
        }
    }

    public void moveTile(JTileHolder start, JTileHolder end)
    {
        Tile startTile = new Tile(start.getTile().getColor(), start.getTile().getNumber(), start.getTile().getX(), start.getTile().getY(), start.getTile().isJoker());
        if (start.getName().charAt(0) == 'm' && end.getName().charAt(0) == 'g')
        {
            int startX = Integer.parseInt(start.getName().split(":")[0].substring(7)), startY = Integer.parseInt(start.getName().split(":")[1].replaceAll("[^0-9]", ""));
            int endX = Integer.parseInt(end.getName().split(":")[0].substring(6)), endY = Integer.parseInt(end.getName().split(":")[1].replaceAll("[^0-9]", ""));

            JavaKub.getInstance().myTilePanels[startX][startY].setTile(end.getTile());
            JavaKub.getInstance().gameTilePanels[endX][endY].setTile(startTile);

            JavaKub.getInstance().log(String.format("Tiles moved successfully, from %d:%d to %d:%d", startX, startY, endX, endY));
        }
    }
}
