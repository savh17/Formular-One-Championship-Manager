package GUI;

import ChampionshipParticipants.FormulaOneDriver;
import ChampionshipParticipants.FormulaOneRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RacePanel extends JPanel implements ActionListener {
    private ArrayList<FormulaOneDriver> driversList;
    private Map<String, FormulaOneRace> racesMap;
    private JButton probabilityRaceBtn;
    private JButton randomRaceBtn;

    public RacePanel(ArrayList<FormulaOneDriver> driversList, Map<String, FormulaOneRace> racesMap) {
        this.racesMap = racesMap;
        this.driversList = driversList;

        // setting JPanel properties
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600, 500));
        this.setBackground(new Color(0x344955));

        // setting up probabilityRaceBtn
        probabilityRaceBtn = new JButton();
        probabilityRaceBtn.setText("Generate a Race (Probability)");
        probabilityRaceBtn.setBounds(200, 160, 250, 60);
        probabilityRaceBtn.setBackground(new Color(0x232F34));
        probabilityRaceBtn.setForeground(Color.WHITE);
        probabilityRaceBtn.setBorderPainted(false);
        probabilityRaceBtn.setOpaque(true);

        // adding hover effects to btn
        probabilityRaceBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                probabilityRaceBtn.setBackground(new Color(0xF9AA33));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                probabilityRaceBtn.setBackground(new Color(0x232F34));
            }
        });

        // setting up probabilityRaceBtn
        randomRaceBtn = new JButton();
        randomRaceBtn.setText("Generate a Race (Random)");
        randomRaceBtn.setBounds(200, 240, 250, 60);
        randomRaceBtn.setBackground(new Color(0x232F34));
        randomRaceBtn.setForeground(Color.WHITE);
        randomRaceBtn.setBorderPainted(false);
        randomRaceBtn.setOpaque(true);

        // adding hover effects to btn
        randomRaceBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                randomRaceBtn.setBackground(new Color(0xF9AA33));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                randomRaceBtn.setBackground(new Color(0x232F34));
            }
        });

        // adding action listeners
        probabilityRaceBtn.addActionListener(this::actionPerformed);
        randomRaceBtn.addActionListener(this::actionPerformed);

        // adding components to JPanel
        this.add(probabilityRaceBtn);
        this.add(randomRaceBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == randomRaceBtn || e.getSource() == probabilityRaceBtn) {
            if (driversList.size() > 0) {

                if (e.getSource() == randomRaceBtn) {
                    String raceID = generateUniqueRaceID();
                    LocalDate raceDate = generateRandomDate();
                    List<FormulaOneDriver> positions = generateRandomPositionsArray(raceID);
                    racesMap.put(raceID, new FormulaOneRace(raceID, raceDate, positions));
                    new RaceDetailWindow(new String[] {"Driver Name", "Position"}, createData(positions), true);
                }
                else {
                    String raceID = generateUniqueRaceID();
                    LocalDate raceDate = generateRandomDate();
                    ArrayList<FormulaOneDriver> driverStartingPositionList = assignRandomStartingPositions();
                    ArrayList<FormulaOneDriver> copyOfDriverStartingPositionList = new ArrayList<>();
                    copyOfDriverStartingPositionList.addAll(driverStartingPositionList);
                    List<FormulaOneDriver> positions = assignPositionsToDrivers(raceID, copyOfDriverStartingPositionList);
                    racesMap.put(raceID, new FormulaOneRace(raceID, raceDate, positions));
                    new RaceDetailWindow(new String[] {"Driver Name", "Starting Position", "Winning Position"}, createData(driverStartingPositionList, positions), true);
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "Your driver list is empty!\nPlease add drivers before generate races", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private List<FormulaOneDriver> assignPositionsToDrivers(String raceID, ArrayList<FormulaOneDriver> driverStartingPositionList) {
        List<FormulaOneDriver> potentialWinners;
        FormulaOneDriver winner;

        // create a list of potential winners
        potentialWinners = createPotentialWinnersList(driverStartingPositionList);

        // finding the winner
        winner = findTheWinner(potentialWinners);

        // remove the winner from the driverStartingPositionList
        driverStartingPositionList.remove(winner);

        // give random positions to rest of the drivers by shuffling the list
        Collections.shuffle(driverStartingPositionList);

        // create new list to accommodate drivers in the order of random positions
        List<FormulaOneDriver> driverPositions = new ArrayList<>();
        driverPositions.add(winner);
        driverPositions.addAll(driverStartingPositionList);

        updateDriversStatistics(raceID, driverPositions);

        return driverPositions;
    }

    private void updateDriversStatistics(String raceID, List<FormulaOneDriver> driverPositions) {
        int position = 1;
        for (FormulaOneDriver driver : driverPositions)
            driver.updateStatistics(position++, raceID);
    }

    private List<FormulaOneDriver> createPotentialWinnersList(List<FormulaOneDriver> driverStartingPositionList) {
        List<FormulaOneDriver> potentialWinners = new ArrayList<>();
        int counter = 1;

        // adding drivers in starting positions 1-9, to potentialWinners based on the given probability
        for (FormulaOneDriver driver : driverStartingPositionList) {
            switch (counter) {
                case 1 -> addDriverToList(potentialWinners, driver, 40);
                case 2 -> addDriverToList(potentialWinners, driver, 30);
                case 3, 4 -> addDriverToList(potentialWinners, driver, 10);
                case 5, 6, 7, 8, 9 -> addDriverToList(potentialWinners, driver, 2);
            }

            // exits from the loop as soon as drivers in starting positions 1-9 has been added to the list
            if (counter++ == 10)
                break;
        }

        return potentialWinners;
    }

    private FormulaOneDriver findTheWinner(List<FormulaOneDriver> potentialWinners) {
        while (true) {
            try {
                return potentialWinners.get((int) (Math.random() * 101));
            }
            catch (IndexOutOfBoundsException ignored) {}
        }

    }

    private void addDriverToList(List<FormulaOneDriver> potentialWinners, FormulaOneDriver potentialWinner, int probability) {
        for (int i=0; i<probability; i++)
            potentialWinners.add(potentialWinner);
    }


    private ArrayList<FormulaOneDriver> assignRandomStartingPositions() {
        ArrayList<FormulaOneDriver> copyOfDriversList = new ArrayList<>(List.copyOf(driversList));
        Collections.shuffle(copyOfDriversList);

        return copyOfDriversList;
    }

    private String[][] createData(List<FormulaOneDriver> positions) {
        String[][] data = new String[positions.size()][2];
        int index = 0;
        for (FormulaOneDriver driver : positions) {
            data[index][0] = driver.getName();
            data[index][1] = String.valueOf(index+1);
            index ++;
        }

        return data;
    }

    private String[][] createData(List<FormulaOneDriver> driversStartingPositions, List<FormulaOneDriver> positions) {
        String[][] data = new String[driversStartingPositions.size()][3];
        int index = 0;
        for (FormulaOneDriver driver : driversStartingPositions) {
            data[index][0] = driver.getName();
            data[index][1] = String.valueOf(index + 1);
            data[index][2] = String.valueOf(positions.indexOf(driver) + 1);
            index ++;
        }

        return data;
    }


        private ArrayList<FormulaOneDriver> generateRandomPositionsArray(String raceID) {
        ArrayList<FormulaOneDriver> copyOfDriversList = new ArrayList<>(List.copyOf(driversList));
        Collections.shuffle(copyOfDriversList);
        int position = 1;

        for (FormulaOneDriver driver : copyOfDriversList)
            driver.updateStatistics(position++, raceID);

        return copyOfDriversList;
    }

    private LocalDate generateRandomDate() {
        int year = (int) (Math.random() * (LocalDate.now().getYear() - 1900)) + 1900;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 31) + 1;

        return LocalDate.of(year, month, day);
    }

    private String generateUniqueRaceID() {
        int id;
        do {
            id = (int) (Math.random() * 1000) + 1;

        } while (racesMap.containsKey(String.valueOf(id)));

        return String.valueOf(id);
    }
}
