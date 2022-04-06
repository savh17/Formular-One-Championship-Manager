package ChampionshipParticipants;

import java.io.Serializable;

public abstract class Driver implements Serializable {
    String name;

    public Driver(String name) {
        this.name = name;
    }

}
