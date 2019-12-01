package climpy.tabscoreboard;

import com.climpy.profile.rank.RankType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;


public class CustomPlayer {
  private static ArrayList<CustomPlayer> customPlayers = new ArrayList();
  private Player player;
  private Scoreboard scoreboard;
  private RankType rankType;

  public CustomPlayer(Player player) {
    setPlayer(player);
    setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    getCustomPlayers().add(this);
  }
  
  public static CustomPlayer getCustomPlayer(Player p) {
    if (p != null) {
      for (CustomPlayer customPlayer : getCustomPlayers()) {
        if (p.equals(customPlayer.getPlayer())) {
          return customPlayer;
        }
      } 
    }
    return null;
  }
  
  public void sendNametag() {
    for (RankType rankType : RankType.values()) {
      if (getScoreboard().getTeam(rankType.getRankName()) == null) {
        getScoreboard().registerNewTeam(rankType.getRankName());
        getScoreboard().getTeam(rankType.getRankName()).setDisplayName(String.valueOf(rankType.getColor()));
        getScoreboard().getTeam(rankType.getRankName()).setPrefix(rankType.getPrefix());
      }
    }

    getPlayer().setDisplayName(rankType.getPrefix() + getPlayer().getName());
    getPlayer().setScoreboard(getScoreboard());
    for (Player all : Bukkit.getOnlinePlayers()) {
      CustomPlayer cpAll = getCustomPlayer(all);
      getScoreboard().getTeam(cpAll.getRank().getRankName()).addPlayer(all);
      all.getScoreboard().getTeam(getRank().getRankName()).addPlayer(getPlayer());
    } 
  }

  
  public static ArrayList<CustomPlayer> getCustomPlayers() { return customPlayers; }


  
  public Player getPlayer() { return this.player; }


  
  public Scoreboard getScoreboard() { return this.scoreboard; }

  
  private void setPlayer(Player player) { this.player = player; }

  public RankType getRank() { return this.rankType; }



  
  private void setScoreboard(Scoreboard scoreboard) { this.scoreboard = scoreboard; }

}
