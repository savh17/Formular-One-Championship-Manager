package ChampionshipManager;

import Utilities.FileHandler;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "src/Data/championship.txt";
    private static Boolean isRunning = true;
    private final static Scanner input = new Scanner(System.in);
    private static ChampionshipManager championshipManager = new FormulaOneChampionshipManager();


    public static void main(String[] args) {
        while (isRunning) {
            displayConsoleMenu();
            executeOperation(input.next().toUpperCase());
        }
    }

    private static void displayConsoleMenu() {
        System.out.println("""
                                
                100 or CND: Create a new driver
                101 or DDT: Delete a driver & the team driver belongs to
                102 or CDC: Change driver's team
                103 or DDS: Display driver statistics
                104 or DDC: Display drivers table (Console)
                105 or DDG: Display drivers table (GUI)
                106 or AR : Add a race
                107 or SPD: Save programme data
                108 or LSD: Load last saved data
                999 or EXT: Exit the Program""");
    }

    public static void executeOperation(String operator) {
        switch (operator) {
            case "100", "CND" -> championshipManager.addNewDriver();
            case "101", "DDT" -> championshipManager.removerDriver();
            case "102", "CDC" -> championshipManager.changeDriverTeam();
            case "103", "DDS" -> championshipManager.showDriverStatistics();
            case "104", "DDC" -> championshipManager.showDriversStatistics();
            case "105", "DDG" -> championshipManager.displayGUITable();
            case "106", "AR" -> championshipManager.addRace();
            case "107", "SPD" -> save();
            case "108", "LSD" -> load();
            case "999", "EXT" -> {
                System.out.println("Exiting ...");
                isRunning = false;
            }
            default -> System.out.println("Oops, invalid Command!!!\nPlease enter a valid command.");
        }
    }

    private static void save() {
        try {
            FileHandler.save(championshipManager, FILE_PATH);
            System.out.println("Date saved Successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void load() {
        try {
            championshipManager = (FormulaOneChampionshipManager) FileHandler.load(FILE_PATH);
            System.out.println("Data loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
