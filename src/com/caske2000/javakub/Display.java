package com.caske2000.javakub;

import com.caske2000.javakub.game.Rules;
import com.caske2000.javakub.game.Tile;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class Display extends Canvas
{
    private List<List<Tile>> tileGroupList = new ArrayList<>();

    public void render(DefaultListModel model, JTextPane textTiles)
    {
        model.clear();
        for (List<Tile> listTile : tileGroupList)
        {
            if (Rules.checkValidGroup(listTile))
            {
                for (Tile tile : listTile)
                {
                    append(textTiles, Integer.toString(tile.getNumber()) + " ", tile.getColor());
                }
                append(textTiles, "\n", Color.BLACK);
            } else
            {
                JavaKub.getInstance().log("Tilegroup isn't valid!\n");
            }
        }
    }

    public void addNormalGroup(Color color, int number, int size)
    {
        tileGroupList.add(new ArrayList<Tile>() {{
            for (int i = 0; i < size; i++)
                add(new Tile(color, number + i));
        }});
    }

    private void append(JTextPane tp, String msg, Color color)
    {
        StyledDocument doc = tp.getStyledDocument();
        StyleContext context = new StyleContext();
        Style style = context.addStyle("caskeStyle", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontSize(style, 25);
        try
        {
            doc.insertString(doc.getLength(), msg, style);
        } catch (Exception e)
        {
            JavaKub.getInstance().log("ERROR: " + e);
        }
    }
}
