package com.climpy.scoreboard;

import climpy.tabscoreboard.ScoreboardEntrySupplier;
import com.climpy.ClimpyLibrary;
import com.climpy.SkyBlockC;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.user.User;
import com.climpy.skyblock.Role;
import com.climpy.skyblock.SkyBlockPlugin;
import com.climpy.util.C;
import com.climpy.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.text.SimpleDateFormat;
import java.util.*;

public class SkyBlockBoard implements ScoreboardEntrySupplier, Listener {

    private final Set<UUID> scoreboardMini = new HashSet();

    public boolean isScoreboardMini(Player player) {
        return this.scoreboardMini.contains(player.getUniqueId());
    }

    public void setScoreboardMode(Player player, boolean status) {
        if (status) {
            this.scoreboardMini.add(player.getUniqueId());
        } else {
            this.scoreboardMini.remove(player.getUniqueId());
        }
    }

    Date date = new Date();
    String saat = (new SimpleDateFormat("HH:mm:ss")).format(date);
    Date date1 = new Date();
    String tarih = (new SimpleDateFormat("dd/MM/yyyy")).format(date1);

    @Override
    public String getScoreboardTitle() {
        return C.color("&b&lELICE");
    }

    @Override
    public List<String> getMaximizedLines(Player player) {
        final List<String> lines = new ArrayList<>();
        User user = ProfilePlugin.getInstance().getUserManager().getUser(player.getName());
        com.climpy.user.User userSkyCore = SkyBlockC.getInstance().getUserManager().getUser(player.getName());
        com.climpy.skyblock.User skyBlockUser = SkyBlockPlugin.getInstance().getUserManager().getUser(player.getName());

    //   lines.add(ChatColor.GRAY + "      " + tarih);
        com.climpy.skyblock.User u = com.climpy.skyblock.User.getUser(player);

        if (u.getIsland() == null) {
            lines.add(" ");
            lines.add(C.color("&cAdanız yok."));
            lines.add(C.color("  &7- /ada yarat"));
            lines.add(C.color(""));
        } else if(u.getIsland().getMembers().size() > 1) {
            lines.add(" ");
            lines.add(C.color("&fAda Rolü: &a" + u.islandRole.getDisplayName()));
            lines.add(C.color("&fAda Level: &a" + u.getIsland().getValue()));
            lines.add(C.color("&fÜyeler: &a" + u.getIsland().getMembers().size()));
            lines.add(C.color(""));
        } else {
            lines.add(" ");
            lines.add(C.color("&fAda Level: &a" + u.getIsland().getValue()));
            lines.add(C.color(""));
        }

        lines.add(" ");
        lines.add(C.color("&9www.elice.net"));

        return lines;
    }


    @Override
    public List<String> getMinimizedLines(Player player) {
        final List<String> lines = new ArrayList<>();
        User user = ProfilePlugin.getInstance().getUserManager().getUser(player.getName());

        lines.add(C.color("&7" + tarih));
        lines.add(C.color(""));

        lines.add(C.color(user.getRankType().getColor() + player.getName()));


        if (ClimpyLibrary.getInstance().getStaffModeListener().isStaffModeActive(player)) {
            lines.add(C.color("&fStaff Modu: &aAçık"));
            lines.add(C.color(player.getGameMode() == GameMode.CREATIVE ? "&fOyun Modu: &aCreative" : "&fOyun Modu: &cNormal"));
            lines.add(C.color(ClimpyLibrary.getInstance().getStaffModeListener().isVanished(player) ? "&fGörünmezlik: &aAçık" : "&eGörünmezlik: &cKapalı"));
        }

        lines.add(C.color("&ewww.elice.net"));


        return lines;
    }


}
