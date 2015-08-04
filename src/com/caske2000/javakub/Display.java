package com.caske2000.javakub;

import com.caske2000.javakub.game.Rules;
import com.caske2000.javakub.game.Tile;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class Display extends Canvas
{
    private List<Tile> tileGroup1 = new ArrayList<>();

    public Display()
    {
        System.out.println(Rules.checkValidGroup(tileGroup1));
        tileGroup1.add(new Tile(Color.ORANGE, 2));
        tileGroup1.add(new Tile(Color.BLACK, 2));
        tileGroup1.add(new Tile(Color.BLUE, 2));
        tileGroup1.add(new Tile(Color.RED, 2));
    }

    public void render(DefaultListModel model, JTextPane textTiles)
    {
        for (Tile tile: tileGroup1)
        {
            model.addElement("Color: " + tile.getColor());
        }
    }

    public void append(JTextPane tp, String s, Color c) { // better implementation--uses
        // StyleContext
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);

        int len = tp.getDocument().getLength(); // same value as
        // getText().length();
        tp.setCaretPosition(len); // place caret at the end (with no selection)
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(s); // there is no selection, so inserts at caret
    }
}
