package ChampionshipManager;

import ChampionshipParticipants.FormulaOneDriver;
import ChampionshipParticipants.FormulaOneTeam;
import ChampionshipParticipants.FormulaOneRace;
import GUI.MainWindow;
import Utilities.ConsoleBasedTable;
import Utilities.Input;
import java.time.LocalDate;
import java.util.*;


public class FormulaOneChampionshipManager implements ChampionshipManager {

    private final static Scanner input = new Scanner(System.in);
    public final  Map<String, FormulaOneDriver> driversMap = new HashMap<>();
    private final Map<String, FormulaOneTeam> teamsMap = new HashMap<>();
    private final Map<String, FormulaOneRace> racesMap = new HashMap<>();


    public void addNewDriver() {
        // create team to accommodate the driver
        FormulaOneTeam team = createNewTeam();

        // prompt for driver's name
        String driverName = Input.takeUniqueStringInput(
                "Enter driver name: ",
                "",
                (dName) -> true
        );

        // prompt for driver's ID
        String driverID = Input.takeUniqueStringInput(
                "Enter driverID: ",
                "Oops, DriverID: %s already exist\nTry again with a different value",
                dID -> !(driversMap.containsKey(dID))
        );

        // create driver object
        FormulaOneDriver.DriverBuilder driverBuilder = new FormulaOneDriver
                .DriverBuilder(driverID, driverName, team);

        FormulaOneDriver driver = new FormulaOneDriver(driverBuilder);

        // putting driver object into hashmap
        driversMap.put(driverID, driver);

        // display success message
        System.out.println("\nDriver Added Successfully!");
    }

    private FormulaOneTeam createNewTeam() {
        // prompt for team name
        String teamName = Input.takeUniqueStringInput(
                "Enter team name: ",
                "Oops, Team Name: %s already exist\nTry again with a different value",
                tName -> !(teamsMap.containsKey(tName))
        );

        // creating team object
        FormulaOneTeam team = new FormulaOneTeam(teamName);

        // putting team object into hashmap
        teamsMap.put(teamName, team);

        return team;
    }

    public void removerDriver() {
        // if no drivers have been registered, display an error message
        if (driversMap.isEmpty()) {
            System.out.println("There are no drivers currently\nPlease register drivers or load saved data, before removing.");
            return;
        }

        String driverID = Input.takeUniqueStringInput(
                "Enter driverID: ",
                "DriverID %s does not exist!\nPlease enter a valid driverID",
                driversMap::containsKey
        );

        // fetching the respective driver object from the hashmap
        FormulaOneDriver driver = driversMap.get(driverID);

        // remove drivers team
        teamsMap.remove(driver.getTeamName());

        // remove driver
        driversMap.remove(driverID);

        // display success message
        System.out.println("\nDriver Removed Successfully!");
    }

    public void changeDriverTeam() {
        // if no drivers have been registered, display an error message
        if (driversMap.isEmpty()) {
            System.out.println("There are no drivers currently\nPlease register drivers or load saved data, before changing.");
            return;
        }

        String driverID = Input.takeUniqueStringInput(
                "Enter driverID, or enter -1 to go back to the main menu: ",
                "DriverID %s does not exist!\nPlease enter a valid driverID",
                (dID) -> driversMap.containsKey(dID) || dID.equals("-1")
        );

        // return to main menu if user enters -1
        if (driverID.equals("-1")) {
            System.out.println("\nReturning to Main Menu ...");
            return;
        }

        String teamName = Input.takeUniqueStringInput(
                "Enter an Existing Team Name: ",
                "Team Name %s does not exist!\nPlease enter an existing Team Name",
                teamsMap::containsKey
        );

        // return to main menu if user enters -1
        if (teamName.equals("-1")) {
            System.out.println("\nReturning to Main Menu ...");
            return;
        }

        // fetching the respective driver object from the hashmap
        FormulaOneDriver driver = driversMap.get(driverID);

        // fetching the target team object from the hashmap
        FormulaOneTeam targetTeam = teamsMap.get(teamName);

        // remove driver's current team
        teamsMap.remove(driver.getTeamName());

        // remove target team's driver
        FormulaOneDriver targetTeamsDriver = targetTeam.getDriver();
        driversMap.remove(targetTeamsDriver.getDriverID());

        // changing the team name of the given driver
        driver.setTeam(targetTeam);
        System.out.printf("\nDriverID: %s, moved to team %s successfully\n", driverID, teamName);

    }

    public void showDriverStatistics() {
        // if no drivers have been registered, display an error message
        if (driversMap.isEmpty()) {
            System.out.println("There are no drivers currently\nPlease register drivers or load saved data.");
            return;
        }

        String driverID = Input.takeUniqueStringInput(
                "Enter driverID: ",
                "DriverID %s does not exist!\nPlease enter a valid driverID",
                driversMap::containsKey
        );
        System.out.println(createDriverStatsString(driversMap.get(driverID)));
    }

    public void showDriversStatistics() {
        // if no drivers have been registered, display an error message
        if (driversMap.isEmpty()) {
            System.out.println("There are no drivers currently\nPlease register drivers or load saved data.");
            return;
        }

        List<FormulaOneDriver> driversList = new ArrayList<>(driversMap.values());
        Collections.sort(driversList);
        ConsoleBasedTable.printTable(createTableDataArray(driversList));
    }

    private String[][] createTableDataArray(List<FormulaOneDriver> driversList) {
        String[][] driverDetails = new String[driversList.size()][8];
        int index = 0;

        for (FormulaOneDriver driver : driversList) {
            driverDetails[index][0] = driver.getDriverID();
            driverDetails[index][1] = driver.getName();
            driverDetails[index][2] = driver.getTeamName();
            driverDetails[index][3] = String.valueOf(driver.getNoFirstPositions());
            driverDetails[index][4] = String.valueOf(driver.getNoSecondPositions());
            driverDetails[index][5] = String.valueOf(driver.getNoThirdPositions());
            driverDetails[index][6] = String.valueOf(driver.getNoRaces());
            driverDetails[index][7] = String.valueOf(driver.getPoints());

            index ++;
        }

        return driverDetails;
    }

    public void displayGUITable() {
        System.out.println("Opening GUI ...");
        new MainWindow(driversMap, racesMap);
    }

    public void addRace() {
        // if no drivers have been registered, display an error message
        if (driversMap.isEmpty()) {
            System.out.println("There are no drivers currently\nPlease register drivers or load saved data, before create a race");
            return;
        }

        String raceID = Input.takeUniqueStringInput(
                "Enter raceID: ",
                "Oops, raceID: %s already exist\nTry again with a different value",
                rID -> !(racesMap.containsKey(rID))
        );

        // creating and, putting new race inside the hashmap
        racesMap.put(raceID, new FormulaOneRace(raceID, promptUserForDate(), getPositions(raceID)));

        // display success message
        System.out.println("\nRace added successfully");
    }

    private String createDriverStatsString(FormulaOneDriver driver) {
        return "Driver ID : " + driver.getDriverID() +
                "\nDriver Name : " + driver.getName() +
                "\nTotal First Positions : " + driver.getNoFirstPositions() +
                "\nTotal Second Positions : " + driver.getNoSecondPositions() +
                "\nTotal Third Positions : " + driver.getNoThirdPositions() +
                "\nTotal Points : " + driver.getPoints();
    }

    private LocalDate promptUserForDate() {
        // prompt user for year
        int dateYear = Input.takeIntegerInput(
                "Enter year(yyyy): ",
                String.format("Oops, year can't be less than 1900 or greater than %s.\nPlease try again", LocalDate.now().getYear()),
                year -> year >= 1900 && year <= LocalDate.now().getYear()
        );

        // prompt user for mont
        int dateMonth = Input.takeIntegerInput(
                "Enter month(mm): ",
                "Oops, month can't be less than 1 or greater than 12\nPlease try again",
                month -> month >= 1 && month <= 12
        );

        // prompt user for day
        int dateDay = Input.takeIntegerInput(
                "Enter day(dd): ",
                "Oops, month can't be less than 1 or greater than 31\nPlease try again",
                day -> day >= 1 && day <= 31
        );

        return LocalDate.of(dateYear, dateMonth, dateDay);
    }

    private ArrayList<FormulaOneDriver> getPositions(String raceID) {
        ArrayList<FormulaOneDriver> positions = new ArrayList<>();
        String driverID;
        FormulaOneDriver driver;

        for (int i = 0; i < driversMap.size(); i++) {

            while (true) {
                System.out.printf("Enter driverID for position %s: ", i + 1);
                driverID = input.next();
                driver = driversMap.get(driverID);

                if (!(driversMap.containsKey(driverID)))
                    System.out.printf("DriverID %s has not been registered.\nPlease enter a valid DriverID", driverID);

                else if (positions.contains(driver))
                    System.out.printf("DriverID %s has already been entered.\nPlease enter a different DriverID", driverID);

                else
                    break;
            }
            positions.add(driver);
            driver.updateStatistics(i + 1, raceID);
        }

        return positions;
    }
}
