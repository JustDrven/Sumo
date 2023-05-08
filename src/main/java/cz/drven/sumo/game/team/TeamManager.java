package cz.drven.sumo.game.team;

import cz.drven.sumo.Main;

public class TeamManager {

    private final Team red;
    private final Team blue;
    private final Main pl;

    public TeamManager(Main pl) {
        this.pl = pl;

        this.red = new Team(TeamColor.RED, pl);
        this.blue = new Team(TeamColor.BLUE, pl);
    }

    public Team getRed() {
        return red;
    }

    public Team getBlue() {
        return blue;
    }
}
