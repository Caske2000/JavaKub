package com.caske2000.javakub;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
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

    // Game Tab
    private DefaultListModel model = new DefaultListModel();
    private JList listGroups;
    private JTextField groupInput;
    private JButton updateViewBtn;
    private JTextPane textTiles;
    private JTextPane textMyTiles;

    // Group Creation Tab
    private JLabel groupLabel;

    // Console Tab
    private JTextArea console;

    public JavaKub()
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
        panel.setLayout(new BorderLayout());

        listGroups = new JList(model);
        JScrollPane listGroupsSP = new JScrollPane(listGroups);
        listGroupsSP.setPreferredSize(new Dimension(200, 0));
        listGroupsSP.setBorder(BorderFactory.createTitledBorder("Groups"));
        panel.add(listGroupsSP, BorderLayout.EAST);

        JPanel panelInput = new JPanel(new BorderLayout());
        groupInput = new JTextField("normal:red:2:4");
        groupInput.setBorder(BorderFactory.createTitledBorder("Group creator"));
        panelInput.add(groupInput, BorderLayout.CENTER);

        updateViewBtn = new JButton("Update View");
        updateViewBtn.addActionListener(e -> {
            if (!groupInput.getText().isEmpty())
            {
                String[] groupData = groupInput.getText().split(":");
                if (!(groupData[0].equals("normal") || groupData[0].equals("same")))
                    throw new IllegalArgumentException("Your input needs to be properly formatted!");
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
                        e1.printStackTrace();
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
                        e1.printStackTrace();
                    }
                    int number = Integer.parseInt(groupData[5]);
                    display.addSameGroup(colorList, number);
                }
                updateView();
            }
        });
        panelInput.add(updateViewBtn, BorderLayout.EAST);

        textMyTiles = new JTextPane();
        textMyTiles.setEditable(false);
        JScrollPane textMyTilesSP = new JScrollPane(textMyTiles);
        textMyTilesSP.setBorder(BorderFactory.createTitledBorder("My Tiles"));
        textMyTilesSP.setPreferredSize(new Dimension(0, 100));
        panelInput.add(textMyTilesSP, BorderLayout.NORTH);

        panel.add(panelInput, BorderLayout.SOUTH);

        textTiles = new JTextPane();
        textTiles.setEditable(false);
        JScrollPane textTilesSP = new JScrollPane(textTiles);
        textTilesSP.setBorder(BorderFactory.createTitledBorder("Tiles"));
        panel.add(textTilesSP, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHelpArea()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Help"));

        groupLabel = new JLabel("<html>Here you can add a new group of tiles.<br>Example: normal:red:2:4.<br>In a 'normal' group all of the tiles have the same color and their number is different, in a 'same' group their numbers are the same, but their color is different.<br>'red' is the color of the tiles, '2' is the value of the first tile and '4' is the amount of tiles in the group.<br><br>Another example: same:red:blue:orange:null:8<br>The 2nd, 3rd, 4th and 5th argument are the colors inside of your group and the last argument represents the number of the tiles.</html>", SwingConstants.CENTER);
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
        this.display.render(model, textTiles);
    }

    public void log(String message)
    {
        console.append(DATE_FORMAT.format(new Date()) + message + "\n");
    }
}
