package GUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class RaceDetailWindow extends JFrame {
    private String[] columnNames;
    private String[][] data;
    boolean autoColumnSize;

    public RaceDetailWindow(String[] columnNames, String[][] data, boolean autoColumnSize) {
        this.columnNames = columnNames;
        this.data = data;
        this.autoColumnSize = autoColumnSize;

        // setting JFrame properties
        this.setTitle("Race Details");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // creating and setting up JPanel
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(500, 500));
        tablePanel.setBackground(new Color(0x232F34));
        tablePanel.setBounds(0, 0, 400, 400);

        tablePanel.add(createTable());

        this.add(tablePanel);

    }

    private JScrollPane createTable() {
        // create JTable with alternate row colors
        JTable table = new JTable(data, columnNames) {
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
        if (table.getColumnCount() > 2 && !(autoColumnSize)) {
            table.getColumnModel().getColumn(0).setPreferredWidth(55);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.getColumnModel().getColumn(2).setPreferredWidth(365);
        }

        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setPreferredSize(new Dimension(620, 35));
        sp.setBounds(100, 100, 250, 250);
        return sp;
    }
}
