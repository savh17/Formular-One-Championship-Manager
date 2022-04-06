package GUI;

import ChampionshipParticipants.FormulaOneDriver;
import ChampionshipParticipants.FormulaOneRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;


public class MainWindow extends JFrame implements ActionListener {
    private Map<String, FormulaOneDriver> driversMap;
    private Map<String, FormulaOneRace> racesMap;

    private JButton displayDriversTableBtn;
    private JButton newRaceBtn;
    private JButton searchRaceBtn;
    private JButton showRaceBtn;

    public MainWindow(
            Map<String, FormulaOneDriver> driversMap,
            Map<String, FormulaOneRace> racesMap
    ) {
        this.driversMap = driversMap;
        this.racesMap = racesMap;

        // setting JFrame properties
        this.setTitle("Formula 01 Championship Manager");
        this.setVisible(true);
        this.setSize(800, 500);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // adding components to the JFrame
        this.add(generateNavigationPanel(), BorderLayout.WEST);
        setHome();

        // adding action listeners to buttons
        displayDriversTableBtn.addActionListener(this);
        newRaceBtn.addActionListener(this);
        searchRaceBtn.addActionListener(this);
        showRaceBtn.addActionListener(this);
    }

    private void setHome() {
        this.add(new HomePagePanel(), BorderLayout.CENTER);
        pack();
    }

    private JPanel generateNavigationPanel() {
        JPanel navigationPanel = new JPanel();

        // setting JPanel properties
        navigationPanel.setPreferredSize(new Dimension(200, 500));
        navigationPanel.setBackground(new Color(0x232F34));
        navigationPanel.setLayout(null);

        // initialise buttons
        initialiseButtons();

        // adding buttons to JPanel
        navigationPanel.add(displayDriversTableBtn);
        navigationPanel.add(newRaceBtn);
        navigationPanel.add(searchRaceBtn);
        navigationPanel.add(showRaceBtn);

        return navigationPanel;
    }

    private void initialiseButtons() {
        displayDriversTableBtn = new JButton();
        displayDriversTableBtn.setText("Show Drivers table");
        displayDriversTableBtn.setBounds(0, 125, 200, 50);

        newRaceBtn = new JButton();
        newRaceBtn.setText("New Race");
        newRaceBtn.setBounds(0, 175, 200, 50);

        searchRaceBtn = new JButton();
        searchRaceBtn.setText("Search Races");
        searchRaceBtn.setBounds(0, 225, 200, 50);

        showRaceBtn = new JButton();
        showRaceBtn.setText("Show Races Table");
        showRaceBtn.setBounds(0, 275, 200, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayDriversTableBtn) {
            this.getContentPane().remove(1);
            this.add(new DriversTablePanel(new ArrayList<>(driversMap.values())), BorderLayout.CENTER);
            pack();
        }
        else if (e.getSource() == newRaceBtn) {
            this.getContentPane().remove(1);
            this.add(new RacePanel(new ArrayList<>(driversMap.values()), racesMap), BorderLayout.CENTER);
            pack();
        }
        else if (e.getSource() == searchRaceBtn) {
            this.getContentPane().remove(1);
            this.add(new RaceSearchPanel(driversMap, racesMap), BorderLayout.CENTER);
            pack();
        }
        else if (e.getSource() == showRaceBtn) {
            this.getContentPane().remove(1);
            this.add(new RaceTablePanel(new ArrayList<>(racesMap.values())), BorderLayout.CENTER);
            pack();
        }
    }
}
