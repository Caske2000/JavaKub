package com.caske2000.javakub.GUI;

import com.caske2000.javakub.game.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lucas on 7/08/2015.
 */
public class JTileHolder extends JLabel
{
    private final Font font = new Font("myFont", Font.PLAIN, 30);
    private final Font defaultFont = new Font("myFont", Font.PLAIN, 12);
    private Tile tile = null;

    public JTileHolder(String text, int horizontalAlignment)
    {
        super(text, horizontalAlignment);
    }

    public Tile getTile()
    {
        if (tile != null)
            return tile;
        else
            return null;
    }

    public void setTile(Tile tile)
    {
        if (tile == null)
        {
            this.tile = null;
            setFont(defaultFont);
            setText("-");
        } else
        {
            this.tile = new Tile(tile.getColor(), tile.getNumber(), tile.getX(), tile.getY(), tile.isJoker());
            setFont(font);
            if (tile.isJoker())
                setText("<html><font color=#7007E0>?</font></html>");
            else
                setText("<html><font color=" + tile.getColorName() + ">" + tile.getNumber() + "</font></html>");
        }
    }

    public void clear()
    {
        this.tile = null;
        setFont(defaultFont);
        setText("-");
    }
}