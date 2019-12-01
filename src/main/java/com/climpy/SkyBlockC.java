package com.climpy;

import climpy.tabscoreboard.ScoreboardManager;
import com.climpy.chat.ChatListener;
import com.climpy.command.*;
import com.climpy.command.economy.MoneyCommand;
import com.climpy.command.economy.SetMoneyCommand;
import com.climpy.config.FileConfig;
import com.climpy.inv.InventoryManager;
import com.climpy.listeners.AsyncJoinListener;
import com.climpy.listeners.DBQuitListener;
import com.climpy.listeners.JoinListener;
import com.climpy.mongo.MongoConnection;
import com.climpy.playtime.PlaytimeTask;
import com.climpy.scoreboard.SkyBlockBoard;
import com.climpy.tab.TabListener;
import com.climpy.tab.Tablist;
import com.climpy.user.User;
import com.climpy.user.UserManager;
import com.climpy.util.C;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class SkyBlockC extends JavaPlugin {
    private static SkyBlockC instance;
    public FileConfig mainConfig;

    public UserManager userManager;
    private MongoConnection mongoConnection;

    private ChatListener chatListener;

    private static InventoryManager invManager;
    private SkyBlockBoard skyblockBoard;
    private TabListener tabListener;
    private Tablist tablist;
    private PlaytimeTask playtimeTask;

    @Override
    public void onEnable() {
        instance = this;

        mongoConnection = new MongoConnection();
        mongoConnection.setupDatabaseConnection();

        userManager = new UserManager();

        getInstance().setupConfig();
        getInstance().setupServer();
        getInstance().registerCommands();
        getInstance().registerManagers();
        getInstance().registerListener();

        invManager = new InventoryManager(this);
        invManager.init();

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            User user = SkyBlockC.getInstance().getUserManager().getUser(onlinePlayer.getUniqueId());
            if (user != null) {
                this.userManager.getUsers().put(user.getUniqueUUID(), user);
            } else {
                onlinePlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cÜzgünüm sunucuya giremezsin!\n Oyuncu verilerin oluşturmamış."));
            }
        });
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void fetchConfig() {
        File file = new File(getDataFolder().getPath(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    }


    public void setupConfig() {
        try {
            File file;
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }
            if (!(file = new File(this.getDataFolder().getAbsolutePath(), "config.yml")).exists()) {
                this.mainConfig = new FileConfig(this, "config.yml");
                this.saveConfig();
                Bukkit.getConsoleSender().sendMessage(C.color("&6[cSkyBlock] Config dosyasi tespit edilemedi, kurulum tamamlaniyor.."));
            } else {
                Bukkit.getConsoleSender().sendMessage(C.color("&a[cSkyBlock] Tamamen config dosyalari yuklendi!"));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        fetchConfig();
    }

    private void registerCommands() {
        getCommand("menu").setExecutor(new TestMenuOpen());
        getCommand("scoreboard").setExecutor(new ScoreboardCommand());
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        getCommand("mutechat").setExecutor(new MuteChatCommand());
        getCommand("resettime").setExecutor(new ResetTimeCommand());
        getCommand("money").setExecutor(new MoneyCommand());
        getCommand("setmoney").setExecutor(new SetMoneyCommand());
        getCommand("settime").setExecutor(new SetTimeCommand());
    }

    private void setupServer() {
        this.skyblockBoard = new SkyBlockBoard();
        Bukkit.getServer().getPluginManager().registerEvents(this.skyblockBoard, this);
        new ScoreboardManager(this, new SkyBlockBoard());

        this.tabListener = new TabListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.tabListener, this);
        this.tabListener.TabUpdate();

        this.tablist = new Tablist();
        Bukkit.getServer().getPluginManager().registerEvents(this.tablist, this);

        this.playtimeTask = new PlaytimeTask(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.playtimeTask, this);
        this.playtimeTask.PlaytimeUpdate();

    }

    private void registerListener() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new AsyncJoinListener(), this);
        pm.registerEvents(new DBQuitListener(), this);
        pm.registerEvents(new JoinListener(), this);
    }

    private void registerManagers() {
        this.chatListener = new ChatListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.chatListener, this);
    }



    public static InventoryManager manager() { return invManager; }
    public static SkyBlockC getInstance() { return instance; }
}
