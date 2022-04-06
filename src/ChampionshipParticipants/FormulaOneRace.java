package ChampionshipParticipants;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FormulaOneRace implements Serializable {
    private String raceID;
    private LocalDate completedDate;
    private List<FormulaOneDriver> positions;

    public FormulaOneRace(String raceID, LocalDate completedDate) {
        this.raceID = raceID;
        this.completedDate = completedDate;
    }

    public FormulaOneRace(String raceID, LocalDate completedDate, List<FormulaOneDriver> positions) {
        this.raceID = raceID;
        this.completedDate = completedDate;
        this.positions = positions;
    }

    public void setPositions(ArrayList<FormulaOneDriver> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(completedDate.toString()).append(",\"");

        for (FormulaOneDriver driver : positions)
            s.append(driver.getDriverID()).append(",");

        s.replace(s.length()-1, s.length(), "\"\n");
        return s.toString();
    }

    public String getRaceID() {
        return raceID;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public List<FormulaOneDriver> getPositions() {
        return positions;
    }
}
