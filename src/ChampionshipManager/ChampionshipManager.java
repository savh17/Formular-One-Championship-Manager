package ChampionshipManager;


import java.io.Serializable;

public interface ChampionshipManager extends Serializable {

    void addNewDriver();
    void removerDriver();
    void changeDriverTeam();
    void showDriverStatistics();
    void showDriversStatistics();
    void displayGUITable();
    void addRace();

}
