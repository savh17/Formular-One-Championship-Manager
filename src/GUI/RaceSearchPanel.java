package GUI;

import ChampionshipParticipants.FormulaOneDriver;
import ChampionshipParticipants.FormulaOneRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RaceSearchPanel extends JPanel implements ActionListener {
    private Map<String, FormulaOneDriver> driversMap;
    private Map<String, FormulaOneRace> racesMap;
    private JButton searchBtn;
    private JTextField searchField;

    public RaceSearchPanel(Map<String, FormulaOneDriver> driversMap, Map<String, FormulaOneRace> racesMap) {
        // setting JPanel properties
        this.driversMap = driversMap;
        this.racesMap = racesMap;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600, 500));
        this.setBackground(new Color(0x344955));

        // setting searchBtn
        searchBtn = new JButton();
        searchBtn.setText("Search Races");
        searchBtn.setBounds(200, 240, 200, 60);
        searchBtn.setBackground(new Color(0x232F34));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setBorderPainted(false);
        searchBtn.setOpaque(true);

        // adding hover effect to searchBtn
        searchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchBtn.setBackground(new Color(0xF9AA33));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchBtn.setBackground(new Color(0x232F34));
            }
        });
        searchBtn.addActionListener(this);

        // setting up text field
        searchField = new JTextField();
        searchField.setBounds(200, 165, 200, 60);
        searchField.setText("Enter driverID ...");

        // adding components to JPanel
        this.add(searchField);
        this.add(searchBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String driverID = searchField.getText();

            if (racesMap.size() == 0)
                JOptionPane.showMessageDialog(null, "NoRaces Found on the database!", "Error", JOptionPane.WARNING_MESSAGE);

            else if (!(driversMap.containsKey(driverID)))
                JOptionPane.showMessageDialog(null, "DriverID not found!", "Error", JOptionPane.WARNING_MESSAGE);

            else {
                ArrayList<String> raceIDs = (ArrayList<String>) driversMap.get(driverID).getRaceIDs();
                new RaceDetailWindow(new String[] {"Race ID", "Race Date", "Positions"}, createDataArray(raceIDs), false);
            }

        }
    }

    private String[][] createDataArray(ArrayList<String> raceIDs) {
        String[][] data = new String[raceIDs.size()][3];
        FormulaOneRace race;
        int index = 0;
        for (String id : raceIDs) {
            race = racesMap.get(id);
            data[index][0] = id;
            data[index][1] = race.getCompletedDate().toString();
            data[index][2] = createPositionString(race.getPositions());
            index ++;
        }

        return data;
    }

    private String createPositionString(List<FormulaOneDriver> positions) {
        StringBuilder s = new StringBuilder();

        int position = 1;
        for (FormulaOneDriver driver : positions)
            s.append(position++)
                    .append(": ")
                    .append(driver.getName())
                    .append(", ");

        return s.toString();
    }
}
