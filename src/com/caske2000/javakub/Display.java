package com.caske2000.javakub;

import com.caske2000.javakub.game.Rules;
import com.caske2000.javakub.game.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class Display extends Canvas
{
    private List<Tile> tileGroup1 = new ArrayList<>();

    public Display(Window window)
    {
        setBounds(0, 0, window.WIDTH, window.HEIGHT);

        System.out.println(Rules.checkValidGroup(tileGroup1));
    }

    public void render(Graphics g)
    {
        // RENDER STUFF
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        int y = 20;
        for (Tile tile : tileGroup1)
        {
            if (tile != null)
            {
                drawTile(g, tile.getColor(), 5, y, tile.getNumber());
                y += 25;
            }
        }
        // RENDER END
    }

    public void drawTile(Graphics g, Tile.Color color, int x, int y, int number)
    {
        switch (color)
        {
            case RED:
                g.setColor(Color.RED);
                break;
            case BLACK:
                g.setColor(Color.BLACK);
                break;
            case ORANGE:
                g.setColor(Color.ORANGE);
                break;
            case BLUE:
                g.setColor(Color.BLUE);
                break;
        }
        g.drawString(Integer.toString(number), x, y);
    }
}
