package Utilities;

public class ConsoleBasedTable {

    public static void printTable(String[][] driverDetails) {
        String leftAlignFormat = "| %-9s | %-11s | %-9s | %-16s | %-16s | %-16s | %-8s | %-6s |%n";

        System.out.format("+-----------+-------------+-----------+------------------+------------------+------------------+----------+---------+%n");
        System.out.format("| Driver ID | Driver Name | Team Name | No 1st Positions | No 2nd Positions | No 3rd Positions | No Races | Points |%n");
        System.out.format("+-----------+-------------+-----------+------------------+------------------+------------------+----------+---------+%n");

        for (String[] s : driverDetails)
            System.out.format(leftAlignFormat, s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7]);

        System.out.format("+-----------+-------------+-----------+------------------+------------------+------------------+----------+---------+%n");
    }
}
