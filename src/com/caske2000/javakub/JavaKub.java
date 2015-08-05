package com.caske2000.javakub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class JavaKub extends JFrame
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss]    ");
    private static final String TITLE = "JavaKub - PreAlpha";
    private static JavaKub instance;
    private Display display;
    private MyListener myListener;

    // Game Tab
    private final DefaultListModel model = new DefaultListModel();
    private JTextField groupInput;
    private JButton updateViewBtn;
    private List<JLabel> gameTilePanels = new ArrayList<>();
    private List<JLabel> myTilePanels = new ArrayList<>();
    private JButton moveBtn;
    private boolean isMoving = false;
    JLabel start = null;

    // Group Creation Tab
    private JLabel groupLabel;

    // Console Tab
    private JTextArea console;

    private JavaKub()
    {
        createView();

        setTitle(TITLE);
        setSize(1280, 720);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static JavaKub getInstance()
    {
        return instance;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            instance = new JavaKub();
            instance.setVisible(true);
            instance.log("Program Started!");
        });
    }

    private void createView()
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        tabbedPane.add("Game Area", createGameArea());
        tabbedPane.add("Help", createHelpArea());
        tabbedPane.add("Console", createConsoleArea());
    }

    private JPanel createGameArea()
    {
        this.display = new Display();
        this.myListener = new MyListener();

        JPanel panel = new JPanel(new BorderLayout());

        JPanel gameTileGrid = new JPanel(new GridLayout(10, 13));
        gameTileGrid.setBorder(BorderFactory.createTitledBorder("The Game"));
        gameTileGrid.setBackground(Color.WHITE);
        JScrollPane gameGridSP = new JScrollPane(gameTileGrid);
        panel.add(gameGridSP, BorderLayout.CENTER);

        for (int i = 1; i <= 130; i++)
        {
            JLabel lbl = new JLabel("-", SwingConstants.CENTER);

            lbl.setEnabled(true);
            lbl.setPreferredSize(new Dimension(40, 40));
            lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lbl.addMouseListener(new BoxListener());
            lbl.setName("gTile" + i);
            gameTilePanels.add(lbl);
            gameTileGrid.add(lbl);
        }

        JPanel myTileGrid = new JPanel(new GridLayout(3, 20));
        myTileGrid.setBorder(BorderFactory.createTitledBorder("Your Tiles"));

        for (int i = 1; i <= 60; i++)
        {
            JLabel lbl = new JLabel("-", SwingConstants.CENTER);

            lbl.setEnabled(true);
            lbl.setPreferredSize(new Dimension(40, 40));
            lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lbl.addMouseListener(new BoxListener());
            lbl.setName("mTile" + i);
            myTilePanels.add(lbl);
            myTileGrid.add(lbl);
        }

        JPanel panelUtils = new JPanel(new BorderLayout());
        panelUtils.setBorder(BorderFactory.createTitledBorder("Utils"));
        moveBtn = new JButton("Move Tile");
        moveBtn.addActionListener(myListener);
        panelUtils.add(moveBtn, BorderLayout.EAST);

        JPanel panelInput = new JPanel(new BorderLayout());
        groupInput = new JTextField("normal:red:2:4");
        groupInput.setBorder(BorderFactory.createTitledBorder("Group creator"));
        groupInput.setPreferredSize(new Dimension(1150, 40));
        groupInput.addActionListener(myListener);
        panelInput.add(groupInput, BorderLayout.WEST);

        updateViewBtn = new JButton("Update View");
        updateViewBtn.addActionListener(myListener);
        panelInput.add(updateViewBtn, BorderLayout.EAST);

        panelInput.add(myTileGrid, BorderLayout.NORTH);
        panel.add(panelInput, BorderLayout.SOUTH);
        panel.add(panelUtils, BorderLayout.EAST);

        return panel;
    }

    private JPanel createHelpArea()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Help"));
        groupLabel = new JLabel("<html>Here you can add a new group of tiles.<br>If you want to add a tile to your own 'deck', just type 'my:' followed by a color and a number (if it is a joker, just add ':j' after a random color and number)<br>To clear all tiles use the clear command followed by a semicolon and a identifier.<br>Identifiers:<br>-'my' clears your own 'deck'<br>-'b' clears the games 'deck'<br><br>Example: normal:red:2:4.<br>In a 'normal' group all of the tiles have the same color and their number is different, in a 'same' group their numbers are the same, but their color is different.<br>'red' is the color of the tiles, '2' is the value of the first tile and '4' is the amount of tiles in the group.<br><br>Another example: same:red:blue:orange:null:8<br>The 2nd, 3rd, 4th and 5th argument are the colors inside of your group and the last argument represents the number of the tiles.</html>", SwingConstants.CENTER);
        panel.add(groupLabel);

        return panel;
    }

    private JPanel createConsoleArea()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BorderLayout());

        console = new JTextArea();
        console.setEditable(false);
        JScrollPane consoleSP = new JScrollPane(console);
        consoleSP.setBorder(BorderFactory.createTitledBorder("Console Output"));
        panel.add(consoleSP, BorderLayout.CENTER);

        return panel;
    }

    private void updateView()
    {
        this.display.render(model, gameTilePanels, myTilePanels);
    }

    public void log(String message)
    {
        console.append(DATE_FORMAT.format(new Date()) + message + "\n");
    }

    private class BoxListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent me)
        {
            JLabel clicked = (JLabel) me.getSource();

            if (isMoving)
            {
                if (start != null)
                {
                    if (start != clicked)
                        display.moveTile(start, clicked);

                    isMoving = false;
                    start = null;
                }

                if (start == null)
                    start = clicked;
            }
        }
    }

    private class MyListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == moveBtn)
            {
                isMoving = true;
            } else if (e.getSource() == groupInput)
            {
                e.setSource(updateViewBtn);
            } else if (e.getSource() == updateViewBtn)
            {
                String[] groupData = groupInput.getText().split(":");
                if (groupData[0].equals("clear"))
                {
                    display.clear(groupData[1]);
                    updateView();
                } else if (!groupInput.getText().isEmpty())
                {
                    if (groupData[0].equals("my"))
                    {
                        Field field;
                        Color color = null;
                        try
                        {
                            field = Color.class.getField(groupData[1]);
                            color = (Color) field.get(null);
                        } catch (NoSuchFieldException | IllegalAccessException e1)
                        {
                            log(e1.getMessage());
                        }
                        int number = Integer.parseInt(groupData[2]);
                        if (groupData.length > 3)
                        {
                            if (groupData[3].equals("j"))
                                display.addMyDeck(color, number, true);
                        } else
                            display.addMyDeck(color, number, false);
                    } else if (groupData[0].equals("normal") || groupData[0].equals("same"))
                    {
                        if (groupData[0].equals("normal"))
                        {
                            Field field;
                            Color color = null;
                            try
                            {
                                field = Color.class.getField(groupData[1]);
                                color = (Color) field.get(null);
                            } catch (NoSuchFieldException | IllegalAccessException e1)
                            {
                                log(e1.getMessage());
                            }
                            int number = Integer.parseInt(groupData[2]);
                            int size = Integer.parseInt(groupData[3]);
                            display.addNormalGroup(color, number, size);
                        } else if (groupData[0].equals("same"))
                        {
                            Field field;
                            List<Color> colorList = new ArrayList<>();
                            try
                            {
                                for (int i = 1; i < 5; i++)
                                {
                                    if (!groupData[i].equals("null"))
                                    {
                                        field = Color.class.getField(groupData[i]);
                                        colorList.add((Color) field.get(null));
                                    }
                                }
                            } catch (NoSuchFieldException | IllegalAccessException e1)
                            {
                                log(e1.getMessage());
                            }
                            int number = Integer.parseInt(groupData[5]);
                            display.addSameGroup(colorList, number);
                        }
                        updateView();
                    } else
                    {
                        throw new IllegalArgumentException("Your input needs to be properly formatted!");
                    }
                }
                updateView();
            }
        }
    }
}
