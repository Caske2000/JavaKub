package com.caske2000.javakub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class JavaKub extends JFrame
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final String TITLE = "JavaKub - PreAlpha";
    private static JavaKub instance;
    private Display display;

    private JTextArea console;
    private DefaultListModel model = new DefaultListModel();
    private JList listGroups;

    private JTextField fieldInput;
    private JButton addGroupBtn;
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
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        tabbedPane.add("Game Area", createGameArea());
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

        addGroupBtn = new JButton("Update View");
        addGroupBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                log("View updated!");
                updateView();
            }
        });
        panelInput.add(addGroupBtn, BorderLayout.EAST);
        panel.add(panelInput, BorderLayout.SOUTH);

        textTiles = new JTextPane();
        textTiles.setEditable(false);
        JScrollPane textTilesSP = new JScrollPane(textTiles);
        textTilesSP.setBorder(BorderFactory.createTitledBorder("Tiles"));
        panel.add(textTilesSP, BorderLayout.CENTER);

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
        console.append(DATE_FORMAT.format(new Date()) + " " + message + "\n");
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
