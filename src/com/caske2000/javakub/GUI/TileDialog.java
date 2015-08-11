package com.caske2000.javakub.GUI;


import com.caske2000.javakub.JavaKub;
import com.caske2000.javakub.game.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 6/08/2015.
 */
public class TileDialog extends JFrame implements ActionListener
{
    private static final String TITLE = "Tile Creator";
    private final JavaKub javaKub;
    private final int x, y;
    private boolean isNormal = true;

    private JPanel gamePanel;
    private JPanel gameSubPanel;
    private JToggleButton groupBtn;
    private final String[] colors = {"BLACK", "BLUE", "RED", "ORANGE", "null"};
    private JComboBox colorBox;
    private JSeparator separator;
    private JLabel numberLbl, sizeLbl;
    private JSpinner number;
    private JSpinner size;
    private JButton addBtn;
    private JPanel invPanel;
    private JPanel invSubPanel;
    private JCheckBox isJoker;
    private JComboBox invColorBox;
    private JLabel invNumberLbl;
    private JSpinner invNumber;
    private JButton addInvBtn;

    public TileDialog(JavaKub javaKub, int x, int y)
    {
        setTitle(TITLE);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.javaKub = javaKub;
        this.x = x;
        this.y = y;
        createView();
    }

    private void createView()
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);
        getContentPane().setPreferredSize(new Dimension(250, 250));

        tabbedPane.add("Game Area", fillGameArea());
        tabbedPane.add("Player Inventory", fillPlayerInv());


    }

    private JPanel fillGameArea()
    {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gameSubPanel = new JPanel();

        groupBtn = new JToggleButton("Normal  group");
        groupBtn.addActionListener(this);
        groupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        groupBtn.setToolTipText("<html>'Normal group' adds a group in which all tiles have the same color, but a increasing number<br>'Same group' adds a group in which all tiles have a different color, but the same number</html>");
        gameSubPanel.add(groupBtn);

        colorBox = new JComboBox(colors);
        colorBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorBox.setToolTipText("<html>When creating a 'normal' group, this color represents the color of your tiles (so don't have the 'null' selected)<br>If you are creating a 'same' group, this color represents the color that is excluded from the group (so if you want to have all colors inside your group, select 'null'</html>");
        gameSubPanel.add(colorBox);

        separator = new JSeparator();
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameSubPanel.add(separator);

        numberLbl = new JLabel("Number: ");
        gameSubPanel.add(numberLbl);
        SpinnerNumberModel model = new SpinnerNumberModel(1D, 1D, 11D, 1D);
        number = new JSpinner(model);
        number.setAlignmentX(Component.CENTER_ALIGNMENT);
        number.setToolTipText("<html>For a 'normal' group, this is the number of the first tile of your group<br>When creating a 'same' group this number represents the number of the tiles inside of the group</html>");
        gameSubPanel.add(number);

        sizeLbl = new JLabel("Size: ");
        gameSubPanel.add(sizeLbl);
        SpinnerNumberModel model2 = new SpinnerNumberModel(3D, 3D, 13D, 1D);
        size = new JSpinner(model2);
        size.setAlignmentX(Component.CENTER_ALIGNMENT);
        size.setToolTipText("<html>This number represents the size of your group<br>Example: the group 10 11 12 has a size of 3</html>");
        gameSubPanel.add(size);

        gamePanel.add(gameSubPanel, BorderLayout.NORTH);

        addBtn = new JButton("Add Group");
        addBtn.addActionListener(this);
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.setToolTipText("Click this button to create your group");
        gamePanel.add(addBtn, BorderLayout.SOUTH);

        pack();
        return gamePanel;
    }

    private JPanel fillPlayerInv()
    {

        invPanel = new JPanel();
        invPanel.setLayout(new BoxLayout(invPanel, BoxLayout.Y_AXIS));
        invSubPanel = new JPanel();

        isJoker = new JCheckBox("Is Joker?");
        isJoker.setAlignmentX(Component.CENTER_ALIGNMENT);
        isJoker.setToolTipText("<html>If you wish to create a joker tile, just check this and press the 'Add Tile' button</html>");
        invSubPanel.add(isJoker);

        invColorBox = new JComboBox(colors);
        invColorBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        invColorBox.setToolTipText("<html>This color represents the tile's color</html>");
        invSubPanel.add(invColorBox);

        invNumberLbl = new JLabel("Number: ");
        invSubPanel.add(invNumberLbl);
        SpinnerNumberModel model = new SpinnerNumberModel(1D, 1D, 13D, 1D);
        invNumber = new JSpinner(model);
        invNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
        invNumber.setToolTipText("<html>This is the number of the tile you wish to create</html>");
        invSubPanel.add(invNumber);

        invPanel.add(invSubPanel, BorderLayout.NORTH);

        addInvBtn = new JButton("Add Tile");
        addInvBtn.addActionListener(this);
        addInvBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInvBtn.setToolTipText("Click this button to create your tile");
        invPanel.add(addInvBtn, BorderLayout.SOUTH);

        pack();
        return invPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addBtn)
        {
            if (isNormal)
            {
                addNormalGroup();
            } else
            {
                addSameGroup();
            }
        } else if (e.getSource() == addInvBtn)
        {
            addInvTile();
        } else if (e.getSource() == groupBtn)
        {
            if (groupBtn.isSelected())
            {
                groupBtn.setText("Same Group");
                gameSubPanel.remove(size);
                gameSubPanel.remove(sizeLbl);
                isNormal = false;
            } else
            {
                groupBtn.setText("Normal  group");
                gameSubPanel.add(sizeLbl);
                gameSubPanel.add(size);
                isNormal = true;
            }
            update(this.getGraphics());
            pack();
        }
    }

    private void addInvTile()
    {
        if (getColor() == null)
            return;
        javaKub.myTilePanels[x][y].setTile(new Tile(getColor(), ((Number) invNumber.getValue()).intValue(), x, y, isJoker.isSelected()));
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        javaKub.updateView();
    }

    private void addNormalGroup()
    {
        if (getColor() == null)
            return;
        for (int x = 0; x < ((Number) size.getValue()).intValue(); x++)
        {
            int newX = ((Number) number.getValue()).intValue() + x - 1;
            javaKub.gameTilePanels[newX][y].setTile(new Tile(getColor(), newX + 1, newX, y, false));
        }
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        javaKub.updateView();
    }

    private void addSameGroup()
    {
        java.util.List<Color> colorList = new ArrayList<>();
        fillColorList(colorList);
        for (int x = 0; x < colorList.size(); x++)
        {
            javaKub.gameTilePanels[x][y].setTile(new Tile(colorList.get(x), ((Number) number.getValue()).intValue(), x, y, false));
        }
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        javaKub.updateView();
    }

    private Color getColor()
    {
        switch (colors[colorBox.getSelectedIndex()])
        {
            case "BLACK":
                return Color.BLACK;
            case "BLUE":
                return Color.BLUE;
            case "RED":
                return Color.RED;
            case "ORANGE":
                return Color.ORANGE;
            default:
                return null;
        }
    }

    private void fillColorList(List<Color> colorList)
    {
        colorList.add(Color.BLACK);
        colorList.add(Color.BLUE);
        colorList.add(Color.RED);
        colorList.add(Color.ORANGE);
        colorList.remove(getColor());
    }
}
