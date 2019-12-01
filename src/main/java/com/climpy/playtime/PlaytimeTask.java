package com.climpy.playtime;

import com.climpy.SkyBlockC;
import com.climpy.title.TitleAPI;
import com.climpy.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaytimeTask implements Listener {

    private SkyBlockC plugin;
    public PlaytimeTask(SkyBlockC pl) { this.plugin = pl; }

    public void PlaytimeUpdate() {
        (new BukkitRunnable() {
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    User user = SkyBlockC.getInstance().getUserManager().getUser(all.getUniqueId());
                    user.setPlayTime(user.getPlayTime() + 1000);
                    user.save();
                }
            }
        }).runTaskTimer(SkyBlockC.getInstance(), 0, 20);
    }
}
