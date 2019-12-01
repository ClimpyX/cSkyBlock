package com.climpy.tab;

import com.climpy.SkyBlockC;
import com.climpy.title.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TabListener implements Listener {

    private SkyBlockC pl;
    public TabListener(SkyBlockC pl) { this.pl = pl; }

    public void TabUpdate() {
        Date date = new Date();
        String saat = (new SimpleDateFormat("HH:mm:ss")).format(date);
        Date date1 = new Date();
        String tarih = (new SimpleDateFormat("dd/MM/yyyy")).format(date1);

        (new BukkitRunnable() {
            public void run() {
                Player[] onlinePlayers;
                int length = (onlinePlayers = (Player[]) Bukkit.getServer().getOnlinePlayers().toArray(new Player[0])).length;
                for (int i = 0; i < length; ++i) {
                    Player player = onlinePlayers[i];
                    TitleAPI.sendTabTitle(player, "&e&lELICE NETWORK\n&7  " + tarih + "  &8" + SkyBlockC.getInstance().getConfig().getString("server-name") + "\n", "\n&dRütbeler, Yükselticiler ve daha fazlası için, &a&nelice.net/magaza&f &dadresini ziyaret edin.");
                }

            }
        }).runTaskTimer(SkyBlockC.getInstance(), 5L, 5L);
    }
}
