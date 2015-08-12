package com.caske2000.javakub;

import com.caske2000.javakub.GUI.Display;
import com.caske2000.javakub.GUI.JTileHolder;
import com.caske2000.javakub.GUI.TileDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class JavaKub extends JFrame implements ActionListener
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss]    ");
    private static final String TITLE = "JavaKub - PreAlpha";
    private static JavaKub instance;
    private static TileDialog tileInstance;
    // Game Tab
    private final DefaultListModel model = new DefaultListModel();
    public final int boardWidth = 13, boardHeight = 10;
    private final int invWidth = 20, invHeight = 3;
    public final JTileHolder[][] gameTilePanels = new JTileHolder[boardWidth][boardHeight];
    public final JTileHolder[][] myTilePanels = new JTileHolder[invWidth][invHeight];
    private JTileHolder start = null;
    private Display display;
    private JTextField groupInput;
    private JButton updateViewBtn;
    private JButton swapBtn;
    private boolean isSwapping = false;
    private JButton resetBtn;
    private JButton deleteBtn;
    private boolean isDeleting = false;

    // Group Creation Tab
    // TODO fix this
    private JLabel helpLbl;

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

        JPanel panel = new JPanel(new BorderLayout());

        JPanel gameTileGrid = new JPanel(new GridLayout(boardHeight, boardWidth));
        gameTileGrid.setBorder(BorderFactory.createTitledBorder("The Game"));
        gameTileGrid.setBackground(Color.WHITE);
        JScrollPane gameGridSP = new JScrollPane(gameTileGrid);
        panel.add(gameGridSP, BorderLayout.CENTER);

        for (int y = 0; y < boardHeight; y++)
        {
            for (int x = 0; x < boardWidth; x++)
            {
                JTileHolder tileH = new JTileHolder("-", SwingConstants.CENTER);

                tileH.setEnabled(true);
                tileH.setPreferredSize(new Dimension(40, 40));
                tileH.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                tileH.addMouseListener(new BoxListener());
                tileH.setName(String.format("gTile(%d:%d)", x, y));
                gameTilePanels[x][y] = tileH;
                gameTileGrid.add(tileH);
            }
        }

        JPanel myTileGrid = new JPanel(new GridLayout(invHeight, invWidth));
        myTileGrid.setBorder(BorderFactory.createTitledBorder("Your Tiles"));

        for (int x = 0; x < invWidth; x++)
        {
            for (int y = 0; y < invHeight; y++)
            {
                JTileHolder tileH = new JTileHolder("-", SwingConstants.CENTER);

                tileH.setEnabled(true);
                tileH.setPreferredSize(new Dimension(40, 40));
                tileH.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                tileH.addMouseListener(new BoxListener());
                tileH.setName(String.format("myTile(%d:%d)", x, y));
                myTilePanels[x][y] = tileH;
                myTileGrid.add(tileH);
            }
        }

        JPanel panelUtils = new JPanel(new BorderLayout());
        panelUtils.setPreferredSize(new Dimension(150, 0));
        panelUtils.setBorder(BorderFactory.createTitledBorder("Utils"));
        swapBtn = new JButton("Swap Tiles");
        swapBtn.addActionListener(this);
        swapBtn.setPreferredSize(new Dimension(0, 200));
        swapBtn.setToolTipText("Press this button to start the swapping of tiles, next press a tile of your own 'deck' followed by a tile of the game board. (You can't swap a empty tile)");
        panelUtils.add(swapBtn, BorderLayout.NORTH);

        resetBtn = new JButton("Reset Swapping");
        resetBtn.addActionListener(this);
        resetBtn.setPreferredSize(new Dimension(0, 200));
        resetBtn.setToolTipText("Press this button to stop swapping.");
        resetBtn.addActionListener(this);
        panelUtils.add(resetBtn, BorderLayout.CENTER);

        deleteBtn = new JButton("Delete Row");
        deleteBtn.addActionListener(this);
        deleteBtn.setPreferredSize(new Dimension(0, 200));
        deleteBtn.setToolTipText("Press this button followed by a tile to delete all the tiles in that row.");
        panelUtils.add(deleteBtn, BorderLayout.SOUTH);

        JPanel panelInput = new JPanel(new BorderLayout());
        groupInput = new JTextField();
        groupInput.setBorder(BorderFactory.createTitledBorder("Console"));
        groupInput.setPreferredSize(new Dimension(1150, 40));
        panelInput.add(groupInput, BorderLayout.WEST);

        updateViewBtn = new JButton("Update View");
        updateViewBtn.addActionListener(this);
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
        helpLbl = new JLabel("<html>Inside the 'Create group' dialog, you can hover over the inputs to learn what they do!</html>");
        panel.add(helpLbl);

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

    public void updateView()
    {
        this.display.render(model, gameTilePanels);
    }

    public void clear(int y, boolean gameBoard)
    {
        if (gameBoard)
        {
            if (!(y < 0 || y >= boardHeight))
            {
                for (int i = 0; i <= boardHeight; i++)
                {
                    gameTilePanels[i][y].clear();
                }
                return;
            }

        } else
        {
            if (!(y < 0 || y >= invHeight))
            {
                for (int i = 0; i <= invHeight; i++)
                {
                    myTilePanels[i][y].clear();
                }
                return;
            }
        }
        JavaKub.getInstance().log("The argument you provided is not valid, argument found: " + y);
        throw new IllegalArgumentException("The argument you provided is not valid, argument found: " + y);
    }

    public void log(String message)
    {
        console.append(DATE_FORMAT.format(new Date()) + message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == swapBtn)
        {
            isSwapping = true;
            swapBtn.setEnabled(false);
        } else if (e.getSource() == resetBtn)
        {
            isSwapping = false;
            swapBtn.setEnabled(true);
        } else if (e.getSource() == deleteBtn)
        {
            isDeleting = true;
            deleteBtn.setEnabled(false);
        } else if (e.getSource() == updateViewBtn)
            updateView();
    }

    private class BoxListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent me)
        {
            JTileHolder clicked = (JTileHolder) me.getSource();

            if (isSwapping)
            {
                if (start != null)
                {
                    if (start != clicked)
                        display.moveTile(start, clicked);

                    isSwapping = false;
                    swapBtn.setEnabled(true);
                    start = null;
                } else
                    start = clicked;
            } else if (isDeleting)
            {
                clear(Integer.parseInt(clicked.getName().split(":")[1].replaceAll("[^0-9]", "")), clicked.getName().charAt(0) == 'g');
                isDeleting = false;
                deleteBtn.setEnabled(true);
            } else
            {
                SwingUtilities.invokeLater(() -> {
                    tileInstance = new TileDialog(instance, Integer.parseInt(clicked.getName().split(":")[0].substring(clicked.getName().charAt(0) == 'g' ? 6 : 7)), Integer.parseInt(clicked.getName().split(":")[1].replaceAll("[^0-9]", "")));
                    tileInstance.setVisible(true);
                    instance.log("Opened new dialog.");
                });
            }
        }
    }
}
