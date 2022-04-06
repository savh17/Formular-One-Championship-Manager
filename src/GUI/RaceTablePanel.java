package GUI;

import ChampionshipParticipants.FormulaOneDriver;
import ChampionshipParticipants.FormulaOneRace;
import Utilities.Sort;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class RaceTablePanel extends JPanel {
    private final String[] COLUMN_NAMES = {
            "Race Date",
            "Race ID",
            "Positions"
    };
    private List<FormulaOneRace> raceArray;

    public RaceTablePanel(List<FormulaOneRace> raceArray) {
        // setting up JPanel properties
        this.raceArray = raceArray;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600, 500));
        this.setBackground(new Color(0x344955));

        // creating a JTable with alternate row colors
        JTable table = new JTable(createDataArray(), COLUMN_NAMES) {
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

//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(55);
        table.getColumnModel().getColumn(2).setPreferredWidth(365);

        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setPreferredSize(new Dimension(620, 35));
        sp.setBounds(40, 100, 520, 330);

        this.add(sp);
    }

    private String[][] createDataArray() {
        Sort.sortRaceArray(raceArray);
        String[][] data = new String[raceArray.size()][3];
        int index = 0;
        for (FormulaOneRace race : raceArray) {
            data[index][0] = race.getCompletedDate().toString();
            data[index][1] = race.getRaceID();
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
