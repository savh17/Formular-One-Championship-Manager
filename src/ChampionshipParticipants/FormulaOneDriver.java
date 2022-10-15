package ChampionshipParticipants;

import java.util.*;

public class FormulaOneDriver extends Driver implements Comparable<FormulaOneDriver> {
    private final static Map<Integer, Integer> positionsPoints = new HashMap<>();
    private final String driverID;
    private FormulaOneTeam team;
    private int[] location;
    private int noFirstPositions;
    private int noSecondPositions;
    private int noThirdPositions;
    private List<String> raceIDs;
    private int points;

    static {
        positionsPoints.put(1, 25);
        positionsPoints.put(2, 18);
        positionsPoints.put(3, 15);
        positionsPoints.put(4, 12);
        positionsPoints.put(5, 10);
        positionsPoints.put(6, 8);
        positionsPoints.put(7, 6);
        positionsPoints.put(8, 4);
        positionsPoints.put(9, 2);
    }

    public FormulaOneDriver(DriverBuilder builder) {
        super(builder.name);
        this.driverID = builder.driverID;
        this.team = builder.team;
        this.location = builder.location;
        this.noFirstPositions = builder.noFirstPositions;
        this.noSecondPositions = builder.noSecondPositions;
        this.noThirdPositions = builder.noThirdPositions;
        this.raceIDs = builder.raceIDs;
        this.points = builder.points;
        team.setDriver(this);
    }

    public static class DriverBuilder {
        private final static Map<Integer, Integer> positionsPoints = new HashMap<>();
        private final String driverID;
        private final String name;
        private final FormulaOneTeam team;
        private int[] location = new int[2];
        private int noFirstPositions;
        private int noSecondPositions;
        private int noThirdPositions;
        private List<String> raceIDs;
        private int points;
        public DriverBuilder(String driverID, String name, FormulaOneTeam team) {
            this.name = name;
            this.driverID = driverID;
            this.team = team;
        }

        public DriverBuilder setLocation(int[] location) {
            this.location = location;
            return this;
        }

        public DriverBuilder setNoFirstPositions(int noFirstPositions) {
            this.noFirstPositions = noFirstPositions;
            return this;
        }

        public DriverBuilder setNoSecondPositions(int noSecondPositions) {
            this.noSecondPositions = noSecondPositions;
            return this;
        }

        public DriverBuilder setNoThirdPositions(int noThirdPositions) {
            this.noThirdPositions = noThirdPositions;
            return this;
        }

        public DriverBuilder setRaceIDs(List<String> raceIDs) {
            this.raceIDs = raceIDs;
            return this;
        }

        public DriverBuilder setPoints(int points) {
            this.points = points;
            return this;
        }
    }

    public void updateStatistics(int position, String raceID) {
        this.raceIDs.add(raceID);

        if (position == 1)
            noFirstPositions ++;
        else if (position == 2)
            noSecondPositions ++;
        else if (position == 3)
            noThirdPositions ++;

        updatePoints(position);
    }

    private void updatePoints(int position) {
        if (positionsPoints.containsKey(position)) {
            this.points += positionsPoints.get(position);
            return;
        }

        this.points += 1;
    }


    @Override
    public int compareTo(FormulaOneDriver other) {
        if (this.points < other.points)
            return 1;

        if (this.points == other.points) {
            if (this.noFirstPositions < other.noFirstPositions)
                return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Driver))
            return false;

        FormulaOneDriver driver = (FormulaOneDriver) o;

        return this.driverID.equals(driver.driverID);
    }

    public String getName() {
        return name;
    }

    public String getDriverID() {
        return driverID;
    }

    public int[] getLocation() {
        return location;
    }

    public String getTeamName() {
        return team.getTeamName();
    }

    public int getNoFirstPositions() {
        return noFirstPositions;
    }

    public int getNoSecondPositions() {
        return noSecondPositions;
    }

    public int getNoThirdPositions() {
        return noThirdPositions;
    }

    public int getNoRaces() {
        return raceIDs.size();
    }

    public int getPoints() {
        return points;
    }

    public void setLocation(int latitude, int longitude) {
        this.location[0] = latitude;
        this.location[1] = longitude;
    }

    public void setTeam(FormulaOneTeam team) {
        this.team = team;
        team.setDriver(this);
    }

    public FormulaOneTeam getTeam() {
        return this.team;
    }

    public List<String> getRaceIDs() {
        return raceIDs;
    }

    @Override
    public String toString()  {
        return this.driverID + "," +
                this.name + "," +
                this.team.getTeamName() + "," +
                "\"" + Arrays.toString(this.location) + "\"" + "," +
                this.noFirstPositions + "," +
                this.noSecondPositions + "," +
                this.noThirdPositions + "," +
                this.points + "," +
                this.raceIDs.size() + "\n";
    }
}
