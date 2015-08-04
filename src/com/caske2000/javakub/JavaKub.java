package com.caske2000.javakub;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class JavaKub extends JFrame
{
    private static final String TITLE = "JavaKub - PreAlpha";
    private static JavaKub instance;

    //TODO Change and fix this
    private JTextPane console;
    private JList groups;

    private JTextField fieldInput;
    private JButton groupBtn;
    private JTextPane textTiles;

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
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new BorderLayout());

        console = new JTextPane();
        console.setEditable(false);
        JScrollPane consoleSP = new JScrollPane(console);
        consoleSP.setPreferredSize(new Dimension(this.getWidth() - 200, 100));
        consoleSP.setBorder(BorderFactory.createTitledBorder("Console Output"));
        panel.add(consoleSP, BorderLayout.SOUTH);

        JPanel groupPanel = new JPanel(new BorderLayout());
        groups = new JList();
        JScrollPane listGroupsSP = new JScrollPane(groups);
        listGroupsSP.setPreferredSize(new Dimension(200, 0));
        listGroupsSP.setBorder(BorderFactory.createTitledBorder("Groups"));
        groupPanel.add(listGroupsSP, BorderLayout.CENTER);

        fieldInput = new JTextField();
        fieldInput.setBorder(BorderFactory.createTitledBorder("Input"));
        fieldInput.setPreferredSize(new Dimension(0, 50));
        groupPanel.add(fieldInput, BorderLayout.SOUTH);

        panel.add(groupPanel, BorderLayout.EAST);

        textTiles = new JTextPane();
        textTiles.setEditable(false);
        JScrollPane textTilesSP = new JScrollPane(textTiles);
        textTilesSP.setBorder(BorderFactory.createTitledBorder("Tiles"));
        panel.add(textTilesSP, BorderLayout.CENTER);
    }

    public static JavaKub getInstance()
    {
        return instance;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                instance = new JavaKub();
                instance.setVisible(true);
            }
        });
    }
}
