package GUI;

import ChampionshipParticipants.FormulaOneDriver;
import Utilities.Sort;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class DriversTablePanel extends JPanel implements ActionListener {
    private ArrayList<FormulaOneDriver> driversList;
    private JComboBox<String> sortItemBox;
    private JComboBox<String> orderItemBox;
    private JTable table;

    private final String[] COLUMN_NAMES = {
            "DriverID",
            "Driver Name",
            "Team Name",
            "1st Positions",
            "2nd Positions",
            "3rd Positions",
            "Points",
            "NoRaces"
    };

    public DriversTablePanel(ArrayList<FormulaOneDriver> driversList) {
        // setting JPanel properties
        this.driversList = driversList;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 500));
        this.setBackground(new Color(0x344955));

        // setting drop-down menu
        String[] sortItems = {"Points", "First Positions"};
        sortItemBox = new JComboBox<>(sortItems);
        sortItemBox.setBounds(310, 100, 150, 30);
        sortItemBox.addActionListener(this);

        // setting label
        JLabel label1 = new JLabel("Sort by:");
        label1.setForeground(Color.WHITE);
        label1.setBounds(250, 100, 50, 30);

        // setting drop-down menu
        String[] orderItems = {"Ascending Order", "Descending Order"};
        orderItemBox = new JComboBox<>(orderItems);
        orderItemBox.setBounds(500, 100, 165, 30);
        orderItemBox.addActionListener(this);

        // setting label
        JLabel label2 = new JLabel("in");
        label2.setForeground(Color.WHITE);
        label2.setBounds(475, 100, 30, 30);

        // sort drivers list before displaying
        Sort.sortTableByPointAscendingOrder(driversList);

        // create JTable with alternate row colors
        table = new JTable(generateTableData(), COLUMN_NAMES) {
            public Component prepareRenderer(TableCellRenderer renderer,
                                             int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                Color color1 = new Color(220,220,220);
                Color color2 = Color.WHITE;
                if(!c.getBackground().equals(getSelectionBackground())) {
                    Color color = (row % 2 == 0 ? color1 : color2);
                    c.setBackground(color);
                    color = null;
                }
                return c;
            }
        };

        table.setShowVerticalLines(false);
        table.getTableHeader().setBackground(new Color(0xF9AA33));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(25);

        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setPreferredSize(new Dimension(620, 35));
        sp.setBounds(40, 150, 620, 300);

        this.add(sp);
        this.add(sortItemBox);
        this.add(orderItemBox);
        this.add(label1);
        this.add(label2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sortItemBox || e.getSource() == orderItemBox) {
            String sortBasedOn = (String) sortItemBox.getSelectedItem();
            String orderBy = (String) orderItemBox.getSelectedItem();

            String sortCode = (sortBasedOn + orderBy).replaceAll("\\s","");

            switch (sortCode) {
                case "PointsAscendingOrder" -> Sort.sortTableByPointAscendingOrder(driversList);
                case "PointsDescendingOrder" -> Sort.sortTableByPointDescendingOrder(driversList);
                case "FirstPositionsAscendingOrder" -> Sort.sortTableByFirstPositionsAscendingOrder(driversList);
                case "FirstPositionsDescendingOrder" -> Sort.sortTableByFirstPositionsDescendingOrder(driversList);
            }
            updateTable();
        }
    }

    private String[][] generateTableData() {
        String[][] data = new String[driversList.size()][8];
        int index = 0;

        for (FormulaOneDriver driver : driversList) {
            data[index][0] = driver .getDriverID();
            data[index][1] = driver.getName();
            data[index][2] = driver.getTeamName();
            data[index][3] = String.valueOf(driver.getNoFirstPositions());
            data[index][4] = String.valueOf(driver.getNoSecondPositions());
            data[index][5] = String.valueOf(driver.getNoThirdPositions());
            data[index][6] = String.valueOf(driver.getPoints());
            data[index][7] = String.valueOf(driver.getNoRaces());
            index ++;
        }
        return data;
    }

    private void updateTable() {
        DefaultTableModel tm = new DefaultTableModel(generateTableData(), COLUMN_NAMES);
        table.setModel(tm);
    }


}
