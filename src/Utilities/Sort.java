package Utilities;

import ChampionshipParticipants.FormulaOneDriver;
import ChampionshipParticipants.FormulaOneRace;

import java.util.List;

public class Sort {
    public static void sortTableByPointAscendingOrder(List<FormulaOneDriver> driversList) {
        driversList.sort((driver1, driver2) -> {
            if (driver1.getPoints() == driver2.getPoints())
                return 0;

            return (driver1.getPoints() < driver2.getPoints()) ? -1 : 1;
        });
    }

    public static void sortTableByPointDescendingOrder(List<FormulaOneDriver> driversList) {
        driversList.sort((driver1, driver2) -> {
            if (driver1.getPoints() == driver2.getPoints())
                return 0;

            return (driver1.getPoints() > driver2.getPoints()) ? -1 : 1;
        });
    }

    public static void sortTableByFirstPositionsAscendingOrder(List<FormulaOneDriver> driversList) {
        driversList.sort((driver1, driver2) -> {
            if (driver1.getNoFirstPositions() == driver2.getNoFirstPositions())
                return 0;

            return (driver1.getNoFirstPositions() < driver2.getNoFirstPositions()) ? -1 : 1;
        });
    }

    public static void sortTableByFirstPositionsDescendingOrder(List<FormulaOneDriver> driversList) {
        driversList.sort((driver1, driver2) -> {
            if (driver1.getNoFirstPositions() == driver2.getNoFirstPositions())
                return 0;

            return (driver1.getNoFirstPositions() > driver2.getNoFirstPositions()) ? -1 : 1;
        });
    }

    public static void sortRaceArray(List<FormulaOneRace> raceArray) {
        raceArray.sort((driver1, driver2) -> {
            if (driver1.getCompletedDate().equals(driver2.getCompletedDate()))
                return 0;

            return (driver1.getCompletedDate().compareTo(driver2.getCompletedDate()) > 0) ? 1 : -1;
        });
    }
}
