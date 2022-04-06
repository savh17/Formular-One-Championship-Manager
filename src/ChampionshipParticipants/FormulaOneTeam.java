package ChampionshipParticipants;

import java.io.Serializable;

public class FormulaOneTeam implements Serializable {

    private String teamName;
    private FormulaOneDriver driver;


    public FormulaOneTeam(String teamName) {
        this.teamName = teamName;
    }

    protected void setDriver(FormulaOneDriver driver) {
        this.driver = driver;
    }

    public String getTeamName() {
        return teamName;
    }

    public FormulaOneDriver getDriver() {
        return driver;
    }

}
