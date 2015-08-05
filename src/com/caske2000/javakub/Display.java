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

    private Font font = new Font("myFont", Font.PLAIN, 30);

    public void render(DefaultListModel model, List<JLabel> gameTilePanel, List<JLabel> myTilePanel)
    {
        int gameRow = 0;
        model.clear();
        allTiles.clear();
        for (List<Tile> listTile : tileGroupList)
        {
            int gamePanel = 0;
            if (Rules.checkValidGroup(listTile))
            {
                for (Tile tile : listTile)
                {
                    gameTilePanel.get(gamePanel + gameRow * 13).setFont(font);
                    gameTilePanel.get(gamePanel + gameRow * 13).setText("<html><font color=" + tile.getColorName() + ">" + tile.getNumber() + "</font></html>");
                    allTiles.add(tile);
                    gamePanel++;
                }
                gameRow++;
            } else
            {
                JavaKub.getInstance().log("Tilegroup isn't valid!\n");
            }
        }

        int panelCount = 0;
        for (Tile tile : myTiles)
        {
            myTilePanel.get(panelCount).setFont(font);
            if (tile.isJoker())
                myTilePanel.get(panelCount).setText("<html><font color=#7007E0>?</font></html>");
            else
                myTilePanel.get(panelCount).setText("<html><font color=" + tile.getColorName() + ">" + tile.getNumber() + "</font></html>");
            allTiles.add(tile);
            panelCount++;
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

    public void moveTile(JLabel start, JLabel end)
    {
        System.out.println(start.getName());
        System.out.println(end.getName());
        //TODO Fix this
        int test = Integer.parseInt(start.getName().substring(5));
        System.out.println(test);
        int test2 = Integer.parseInt(end.getName().substring(5));
        System.out.println(test2);
    }
}
