package com.caske2000.javakub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private JTextField fieldInput;
    private JButton updateViewBtn;
    private JTextPane textTiles;

    // Group Creation Tab
    private JLabel groupLabel;
    private JTextField groupInput;
    private JButton addGroupBtn;

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

    private void createView()
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        tabbedPane.add("Game Area", createGameArea());
        tabbedPane.add("Group Creator", createGroupArea());
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
        fieldInput = new JTextField();
        fieldInput.setBorder(BorderFactory.createTitledBorder("Input"));
        panelInput.add(fieldInput, BorderLayout.CENTER);

        updateViewBtn = new JButton("Update View");
        updateViewBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateView();
            }
        });
        panelInput.add(updateViewBtn, BorderLayout.EAST);
        panel.add(panelInput, BorderLayout.SOUTH);

        textTiles = new JTextPane();
        textTiles.setEditable(false);
        JScrollPane textTilesSP = new JScrollPane(textTiles);
        textTilesSP.setBorder(BorderFactory.createTitledBorder("Tiles"));
        panel.add(textTilesSP, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGroupArea()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Create new group"));

        // TODO add second type of group
        groupLabel = new JLabel();
        groupLabel.setText("Here you can add a new group of tiles. Example: addGroup:red:2:4. red is the color of the tiles, 2 is the value of the first tile and 4 is the amount of tiles in the group.");
        panel.add(groupLabel);

        groupInput = new JTextField();
        groupInput.setBorder(BorderFactory.createTitledBorder("Input"));
        groupInput.setPreferredSize(new Dimension(1000, 50));

        panel.add(groupInput);

        addGroupBtn = new JButton("Add group");
        addGroupBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String[] groupData = groupInput.getText().split(":");
                if (!groupData[0].equals("addGroup"))
                    throw new IllegalArgumentException("Your input needs to be properly formatted!");
                Field field;
                Color color = null;
                try
                {
                    field = Color.class.getField(groupData[1]);
                    color = (Color)field.get(null);
                } catch (NoSuchFieldException e1)
                {
                    e1.printStackTrace();
                }
                catch (IllegalAccessException e2)
                {
                    e2.printStackTrace();
                }
                int number = Integer.parseInt(groupData[2]);
                int size = Integer.parseInt(groupData[3]);
                display.addNormalGroup(color, number, size);
                display.render(model, textTiles);
            }
        });
        panel.add(addGroupBtn);

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
}
