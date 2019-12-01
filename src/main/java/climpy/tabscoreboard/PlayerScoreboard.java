package climpy.tabscoreboard;

import com.climpy.SkyBlockC;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.rank.RankType;
import com.climpy.profile.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class PlayerScoreboard {

	public static String[] TEAM_NAMES;


	private final SkyBlockC skyBlockC = SkyBlockC.getInstance();

	private Scoreboard scoreboard;
	private Objective objective;
	private Player player;
	private ScoreboardEntrySupplier supplier;

	public static HashMap<UUID, PlayerScoreboard> scoreboardHashMap = new HashMap<>();

	protected PlayerScoreboard(Player player, ScoreboardEntrySupplier supplier) {
		this.player = player;
		this.supplier = supplier;
		Scoreboard scoreboard = player.getScoreboard();
		if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		}
		player.setScoreboard(this.scoreboard = scoreboard);
		Objective objective = scoreboard.getObjective("sidebar");
		if (objective == null) {
			objective = scoreboard.registerNewObjective("sidebar", "dummy");
		}

		Team owner = this.scoreboard.registerNewTeam("001owner");
		Team coowner = this.scoreboard.registerNewTeam("002coowner");
		Team admin = this.scoreboard.registerNewTeam("003admin");
		Team developer = this.scoreboard.registerNewTeam("004developer");
		Team buildteamplus = this.scoreboard.registerNewTeam("005buildteamp");
		Team buildteam = this.scoreboard.registerNewTeam("006buildteam");
		Team modplus = this.scoreboard.registerNewTeam("007modplus");
		Team mod = this.scoreboard.registerNewTeam("008mod");
		Team helper = this.scoreboard.registerNewTeam("009helper");
		Team trailerhelper = this.scoreboard.registerNewTeam("010trailerhelper");
		Team youtuber = this.scoreboard.registerNewTeam("011youtuber");
		Team cvipplusplus = this.scoreboard.registerNewTeam("012cvipplusplus");
		Team cvipplus = this.scoreboard.registerNewTeam("013cvipplus");
		Team cvip = this.scoreboard.registerNewTeam("014cvip");
		Team mvipplus = this.scoreboard.registerNewTeam("015mvipplus");
		Team mvip = this.scoreboard.registerNewTeam("016mvip");
		Team vipplus = this.scoreboard.registerNewTeam("017vipplus");
		Team vip = this.scoreboard.registerNewTeam("018vip");
		Team rankdefault = this.scoreboard.registerNewTeam("020member");

		owner.setPrefix(ChatColor.translateAlternateColorCodes('&', "&c[OWNER] &c"));
		coowner.setPrefix(ChatColor.translateAlternateColorCodes('&', "&c[Co-Owner] &c"));
		admin.setPrefix(ChatColor.translateAlternateColorCodes('&', "&c[ADMIN] &c"));
		developer.setPrefix(ChatColor.translateAlternateColorCodes('&', "&b[DEVELOPER] &b"));
		buildteamplus.setPrefix(ChatColor.translateAlternateColorCodes('&', "&3[MİMAR&c+&3] "));
		buildteam.setPrefix(ChatColor.translateAlternateColorCodes('&', "&3[MİMAR] "));
		modplus.setPrefix(ChatColor.translateAlternateColorCodes('&', "&2[MOD&c+&2] &2"));
		mod.setPrefix(ChatColor.translateAlternateColorCodes('&', "&2[MOD] "));
		helper.setPrefix(ChatColor.translateAlternateColorCodes('&', "&9[YARDIMCI] "));
		trailerhelper.setPrefix(ChatColor.translateAlternateColorCodes('&', "&9[T-YARDIMCI] "));
		youtuber.setPrefix(ChatColor.translateAlternateColorCodes('&', "&6[YOUTUBE] "));
		cvipplusplus.setPrefix(ChatColor.translateAlternateColorCodes('&', "&e[CVIP&c++&e] "));
		cvipplus.setPrefix(ChatColor.translateAlternateColorCodes('&', "&e[CVIP&c+&e] "));
		cvip.setPrefix(ChatColor.translateAlternateColorCodes('&', "&e[CVIP] "));
		mvipplus.setPrefix(ChatColor.translateAlternateColorCodes('&', "&d[MVIP&e+&d] "));
		mvip.setPrefix(ChatColor.translateAlternateColorCodes('&', "&d[MVIP] "));
		vipplus.setPrefix(ChatColor.translateAlternateColorCodes('&', "&b[VIP&c+&b] "));
		vip.setPrefix(ChatColor.translateAlternateColorCodes('&', "&b[VIP] "));
		rankdefault.setPrefix(ChatColor.translateAlternateColorCodes('&', "&9"));

		objective.setDisplayName(supplier.getScoreboardTitle());
		(this.objective = objective).setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void checkTabPrefix(Player p, Scoreboard sb) {
		User user = ProfilePlugin.getInstance().getUserManager().getUser(p.getName());
		if (user.getRankType() == RankType.OWNER) {
			sb.getTeam("001owner").addEntry(p.getName());
		} else if(user.getRankType() == RankType.CO_OWNER) {
			sb.getTeam("002coowner").addEntry(p.getName());
		} else if(user.getRankType() == RankType.ADMIN) {
			sb.getTeam("003admin").addEntry(p.getName());
		} else if(user.getRankType() == RankType.DEVELOPER) {
			sb.getTeam("004developer").addEntry(p.getName());
		} else if(user.getRankType() == RankType.BUILDTEAMPLUS) {
			sb.getTeam("005buildteamp").addEntry(p.getName());
		} else if(user.getRankType() == RankType.BUILDTEAM) {
			sb.getTeam("006buildteam").addEntry(p.getName());
		} else if(user.getRankType() == RankType.MODPLUS) {
			sb.getTeam("007modplus").addEntry(p.getName());
		} else if(user.getRankType() == RankType.MOD) {
			sb.getTeam("008mod").addEntry(p.getName());
		} else if(user.getRankType() == RankType.HELPER) {
			sb.getTeam("009helper").addEntry(p.getName());
		} else if(user.getRankType() == RankType.TRAILERHELPER) {
			sb.getTeam("010trailerhelper").addEntry(p.getName());
		} else if(user.getRankType() == RankType.YOUTUBER) {
			sb.getTeam("011youtuber").addEntry(p.getName());
		} else if(user.getRankType() == RankType.CVIPPLUSPLUS) {
			sb.getTeam("012cvipplusplus").addEntry(p.getName());
		} else if(user.getRankType() == RankType.CVIPPLUS) {
			sb.getTeam("013cvipplus").addEntry(p.getName());
		} else if(user.getRankType() == RankType.CVIP) {
			sb.getTeam("014cvip").addEntry(p.getName());
		} else if(user.getRankType() == RankType.MVIPPLUS) {
			sb.getTeam("015mvipplus").addEntry(p.getName());
		} else if(user.getRankType() == RankType.MVIP) {
			sb.getTeam("016mvip").addEntry(p.getName());
		} else if(user.getRankType() == RankType.VIPPLUS) {
			sb.getTeam("017vipplus").addEntry(p.getName());
		} else if(user.getRankType() == RankType.VIP) {
			sb.getTeam("018vip").addEntry(p.getName());
		} else if(user.getRankType() == RankType.DEFAULT) {
			sb.getTeam("020member").addEntry(p.getName());
		} else {
			sb.getTeam("020member").addEntry(p.getName());
		}
	}



	public void send() {
		List<String> entries = (this.skyBlockC.getSkyblockBoard().isScoreboardMini(player) ? supplier.getMinimizedLines(player) : supplier.getMaximizedLines(player));

		for (Player a : Bukkit.getOnlinePlayers()) {
			checkTabPrefix(a, this.scoreboard);
		}

		int index = 15;
		if (entries.size() != scoreboard.getEntries().size()) {
			scoreboard.getEntries().forEach(x -> scoreboard.resetScores(x));
		}
		for (String entry : entries) {
			if (index < 1) {
				continue;
			}
			String left = "", right = "";
			if (entry.length() <= 16) {
				left = entry;
			}
			else {
				String first = entry.substring(0, 16);
				String second = entry.substring(16, entry.length());
				if (first.endsWith("\u00a7")) {
					first = first.substring(0, first.length() - 1);
					second = "\u00a7" + second;
				}
				second = ChatColor.getLastColors(first) + second;
				left = first;
				right = second.substring(0, Math.min(second.length(), 16));
				if (right.endsWith("\u00a7")) {
					right = right.substring(0, right.length() - 1);
				}
			}
			Team team = scoreboard.getTeam(TEAM_NAMES[15 - index]);
			if (team == null) {
				team = scoreboard.registerNewTeam(TEAM_NAMES[15 - index]);
			}
			if (!team.hasEntry(TEAM_NAMES[15 - index])) {
				team.addEntry(TEAM_NAMES[15 - index]);
			}
			team.setPrefix(left);
			team.setSuffix(right);
			objective.getScore(TEAM_NAMES[15 - index]).setScore(index--);
		}
	}

	public void clean() {
		if (player.isOnline()) {
			player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
		scoreboard.getTeams().forEach(x -> x.unregister());
		scoreboard.getObjectives().forEach(x -> x.unregister());
		player = null;
		objective = null;
		scoreboard = null;
	}

	static {
		TEAM_NAMES = new String[15];
		for (int i = 0; i < 15; i ++) {
			TEAM_NAMES[i] = ChatColor.RESET.toString() + ChatColor.values()[i] + ChatColor.RESET.toString();
		}
	}
}
