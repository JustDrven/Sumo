package cz.drven.sumo.game.team;

import cz.drven.sumo.Main;
import cz.drven.sumo.player.SumoPlayer;
import cz.drven.sumo.utils.Colors;

import java.util.Set;
import java.util.stream.Collectors;

public class Team {

    private TeamColor teamColor;
    private Main pl;

    public Team(TeamColor color, Main pl) {
        this.teamColor = color;
        this.pl = pl;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public String addPlayer(SumoPlayer player) {
        if (this.equals(player.getTeam())) {
            return Colors.format("&fYou already selected this team.");
        }

        player.setTeam(this);
        return Colors.format("&fSelected team " + this.getTeamColor().getChatColor() + this.getTeamColor().getName());
    }

    public Set<SumoPlayer> getPlayers() {
        return pl.getGame().getPlayerManager().getSumoPlayers().stream()
                .filter(ps -> this.equals(ps.getTeam())).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Team: " + this.getTeamColor().name() + "Players: " + this.getPlayers().size();
    }
}
