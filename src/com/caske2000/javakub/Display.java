package com.caske2000.javakub;

import com.caske2000.javakub.game.Rules;
import com.caske2000.javakub.game.Tile;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class Display extends Canvas
{
    private final List<List<Tile>> tileGroupList = new ArrayList<>();
    private final List<Tile> myTiles = new ArrayList<>();

    private final List<Tile> allTiles = new ArrayList<>();

    public void render(DefaultListModel model, JTextPane textTiles, JTextPane textMyTiles)
    {
        model.clear();
        textTiles.setText("");
        allTiles.clear();
        for (List<Tile> listTile : tileGroupList)
        {
            if (Rules.checkValidGroup(listTile))
            {
                for (Tile tile : listTile)
                {
                    append(textTiles, Integer.toString(tile.getNumber()) + " ", tile.getColor());
                    allTiles.add(tile);
                }
                append(textTiles, "\n", Color.BLACK);
            } else
            {
                JavaKub.getInstance().log("Tilegroup isn't valid!\n");
            }
        }

        textMyTiles.setText("");
        for (Tile tile : myTiles)
        {
            if (tile.isJoker())
                append(textMyTiles, "? ", Color.MAGENTA);
            else
                append(textMyTiles, Integer.toString(tile.getNumber()) + " ", tile.getColor());
            allTiles.add(tile);
        }
    }

    public void addNormalGroup(Color color, int number, int size)
    {
        tileGroupList.add(new ArrayList<Tile>()
        {{
                for (int i = 0; i < size; i++)
                    add(new Tile(color, number + i, false));
            }});
    }

    public void addSameGroup(List<Color> colorList, int number)
    {
        //TODO replace call
        tileGroupList.add(new ArrayList<Tile>()
        {{
                addAll(colorList.stream().map(color -> new Tile(color, number, false)).collect(Collectors.toList()));
            }});
    }

    public void clear(String place)
    {
        switch (place)
        {
            case "my":
                myTiles.clear();
                break;
            case "b":
                tileGroupList.clear();
                break;
            default:
                JavaKub.getInstance().log("The argument you provided is not valid, argument found: " + place);
                throw new IllegalArgumentException("The argument you provided is not valid, argument found: " + place);
        }
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

    public void addMyDeck(Color color, int number, boolean isJoker)
    {
        myTiles.add(new Tile(color, number, isJoker));
    }
}
