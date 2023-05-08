package cz.drven.sumo.game;

import cz.drven.sumo.Main;
import cz.drven.sumo.game.map.MapManager;
import cz.drven.sumo.game.state.State;
import cz.drven.sumo.game.state.WaitingState;
import cz.drven.sumo.game.team.Team;
import cz.drven.sumo.game.team.TeamColor;
import cz.drven.sumo.game.team.TeamManager;
import cz.drven.sumo.items.ItemManager;
import cz.drven.sumo.player.PlayerManager;
import cz.drven.sumo.player.SumoPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Game {

    private State state;
    private final PlayerManager playerManager;
    private final TeamManager teamManager;
    private final ItemManager itemManager;
    private final MapManager mapManager;
    private final List<Team> teams;
    private final Main pl;

    public Game(Main pl) {
        this.pl = pl;

        this.playerManager = new PlayerManager(pl);
        this.teamManager = new TeamManager(pl);
        this.itemManager = new ItemManager(pl);
        this.mapManager = new MapManager(pl);

        teams = Arrays.stream(TeamColor.values())
                .map(color -> new Team(color, pl))
                .collect(Collectors.toList());
        setState(new WaitingState(pl));

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (this.state != null) this.state.onDisable();

        this.state = state;
        this.state.onEnable();

    }


    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void getNextState() {
        this.setState(this.getState().getNextState());
    }

    public String toTime(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

}
