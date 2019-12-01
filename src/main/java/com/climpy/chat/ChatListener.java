package com.climpy.chat;

import com.climpy.ClimpyLibrary;
import com.climpy.SkyBlockC;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.rank.RankType;
import com.climpy.profile.user.User;
import com.climpy.util.C;
import com.climpy.util.NumberUtil;
import com.google.common.collect.MapMaker;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {
    private static final String DOUBLE_POST_BYPASS_PERMISSION = "hcf.doublepost.bypass";
    private static final Pattern PATTERN = Pattern.compile("\\W");
    private final Map<UUID, String> messageHistory;
    private final SkyBlockC plugin;

    private long muteChatMillis;

    public void setMuteChatMillis(long value) {
        this.muteChatMillis = System.currentTimeMillis() + value;
    }


    public boolean isChatMuted() {
        return (getMillisecondLeft() > 0L);
    }


    public long getMillisecondLeft() {
        return this.muteChatMillis - System.currentTimeMillis();
    }


    public ChatListener(SkyBlockC plugin) {
        this.plugin = plugin;
        this.messageHistory = new MapMaker().makeMap();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = ProfilePlugin.getInstance().getUserManager().getUser(player.getName());

        String message = event.getMessage();
        String message2 = message.toLowerCase();
        String message3 = message2.replace(".", "");
        String message4 = message3.replace(",", "");
        String message5 = message4.replace(" ", "");
        String message6 = message5.replace("(\\w)\\1+", "§1");
        String lastMessage = (String) this.messageHistory.get(player.getUniqueId());
        String cleanedMessage = PATTERN.matcher(message).replaceAll("");
        Pattern p = Pattern.compile("(\\s|^)[e]+[z]+($|\\s)", Pattern.CASE_INSENSITIVE);
        Pattern hacker = Pattern.compile("(\\s|^)(hackers?|hacks?|hax|hacking|cheaters?|cheating|cheats?|hile|hileci)($|\\s)", Pattern.CASE_INSENSITIVE);
        Pattern hackstype = Pattern.compile("(\\s|^)(kill aura|forcefield|aimbot|autoclick|autoclickers?|groundhog|killaura)($|\\s)", Pattern.CASE_INSENSITIVE);
        Pattern dot = Pattern.compile("(.*\\d+\\.\\d+\\.\\d+\\.\\d+.*)", Pattern.CASE_INSENSITIVE);
        Pattern dot2 = Pattern.compile("(.*\\d+,\\d+,\\d+,\\d+.*)", Pattern.CASE_INSENSITIVE);
        Pattern website = Pattern.compile("((?:(?:https?):\\/\\/)?(?:[-\\w_]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf(ChatColor.COLOR_CHAR) + "\\s\\n]|$))))");


        for (String word : message6.split(" ")) {
            if (hacker.matcher(message).find()) {
                player.sendMessage(ChatColor.RED + "Bunun yerine /report kullanmanız daha mantıklı olacaktır!");
                player.sendMessage(ChatColor.RED + "Bunu yazınca, hile kullanan kişinin kontrol edilmeden önce hilesini kapatmasına izin vermiş olursunuz.");
                event.setCancelled(true);
            } else if (hackstype.matcher(message).find()) {
                player.sendMessage(ChatColor.RED + "Sohbet kısmında farklı hile türlerinden bahsetme.");
                player.sendMessage(ChatColor.RED + "Farklı hilelerden bahsetmek, diğer oyuncuları onları kullanmaya teşvik edebilir veya onlar hakkında daha fazla bilgi edindirebilir.");
                event.setCancelled(true);
            } else if (dot.matcher(message).find() || message.toLowerCase().contains("pvp.") || message.toLowerCase().contains("play.") || message.toLowerCase().contains("oyna.") || message.toLowerCase().contains("no-ip")) {
                player.sendMessage(ChatColor.RED + "Sohbet kısmında lütfen reklam yapmayın.");
                player.sendMessage(ChatColor.RED + "Eğer devam ederseniz, ağdan IP yasaklanacaksınız.");
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "warn " + player.getName() + " Reklam yapma ve sunucu paylaşma girişimi");
                event.setCancelled(true);
            } else if (dot2.matcher(message).find()) {
                player.sendMessage(ChatColor.RED + "Sohbet kısmında lütfen reklam yapmayın.");
                player.sendMessage(ChatColor.RED + "Eğer devam ederseniz, ağdan IP yasaklanacaksınız.");
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "warn " + player.getName() + " Reklam yapma ve sunucu paylaşma girişimi");
                event.setCancelled(true);
            } else if (website.matcher(message).find()) {
                Matcher matcher = website.matcher(message);
                while (matcher.find()) {
                    String url = matcher.group(0).toLowerCase();
                    if (url.contains("imgur.com") || url.contains("youtube.com") || url.contains("gyazo.com") || url.contains("climpymc.com") || url.contains("puu.sh") || url.contains("prntscr.com") || url.contains("twitch.tv") || url.contains("twitter.com") || url.contains("plug.dj") || url.contains("mlg.tv")
                            || url.contains("majorleaguegaming.com") || url.contains("discord.gg")) {
                        if (BlockedWords.BLOCKED_WORDS.contains(word.toLowerCase())) {
                            event.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.CAT_MEOW, 1.0F, 1.0F);

                            if (!user.getRankType().isAboveOrEqual(RankType.HELPER)) {
                                player.sendMessage(ChatColor.RED + "Kara listede olan bir kelimeyi kullandınız.");
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "warn " + player.getName() + " Küfür, Hakaret içeren sözcükler");
                            } else {
                                player.sendMessage(ChatColor.RED + "Kara listede olan bir kelimeyi kullandınız.");
                            }

                            for (Player playerStaff : Bukkit.getOnlinePlayers()) {
                                playerStaff.sendMessage(ChatColor.RED + "(Sansürlendi) " + ChatColor.translateAlternateColorCodes('&', user.getRankType().getPrefix() + player.getName() + ChatColor.WHITE + ": " + ChatColor.RESET + message));
                            }
                        } else {
                            this.messageHistory.put(player.getUniqueId(), cleanedMessage);
                            event.setCancelled(true);

                            ConsoleCommandSender console = Bukkit.getConsoleSender();
                            console.sendMessage(getFormattedMessage(player, message, console));

                            for (Player recipient : event.getRecipients()) {
                                recipient.sendMessage(getFormattedMessage(player, message, recipient));
                            }

                            ClimpyLibrary.getInstance().getLogManager().formatChatMessage(message, player.getName());
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Bu site adresine sohbet içinde izin verilmiyor.");
                        event.setCancelled(true);
                    }
                }
            } else if (BlockedWords.BLOCKED_WORDS.contains(word.toLowerCase())) {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.DONKEY_HIT, 1.0F, 1.0F);

                if (!user.getRankType().isAboveOrEqual(RankType.HELPER)) {
                    player.sendMessage(ChatColor.RED + "Bu kelimeyi kullanmak engellenmiş gözüküyor, yetkililere bildirildi.");
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "warn " + player.getName() + " Küfür, Hakaret içeren sözcükler");
                } else {
                    player.sendMessage(ChatColor.RED + "Bu kelimeyi kullanmak engellenmiş gözüküyor, yetkililere bildirildi.");
                }

                for (Player playerStaff : Bukkit.getOnlinePlayers()) {
                    playerStaff.sendMessage(ChatColor.RED + "[Sansürlendi] " + ChatColor.translateAlternateColorCodes('&', user.getRankType().getPrefix() + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + message));
                }
            } else if (lastMessage != null && (message.equals(lastMessage) || StringUtils.getLevenshteinDistance(cleanedMessage, lastMessage) <= 1) && !user.getRankType().isAboveOrEqual(RankType.MOD)) {
                player.sendMessage(ChatColor.RED + "Lütfen, aynı mesajı 2 kere göndermeyin.");
                event.setCancelled(true);
            } else if (ClimpyLibrary.getInstance().getStaffModeListener().isStaffChatActive(player)) {
                if (!user.getRankType().isAboveOrEqual(RankType.MOD)) {

                    event.setCancelled(true);
                    for (Player staff : Bukkit.getServer().getOnlinePlayers()) {

                        if (!user.getRankType().isAboveOrEqual(RankType.MOD)) {
                            staff.sendMessage(C.color("&b(Personel) " + player.getName() + "§7: ") + ChatColor.RESET + event.getMessage());
                        }
                    }
                    Bukkit.getServer().getConsoleSender().sendMessage(C.color("&b(Personel) " + player.getName() + "§7: " + event.getMessage()));
                } else {

                    ClimpyLibrary.getInstance().getStaffModeListener().setStaffChat(player, false);
                    player.sendMessage(C.color("&cPersonel modunuz devre dışı bırakıldı, çünkü bunları kullanma izniniz yok."));
                }
            } else if (isChatMuted() && !user.getRankType().isAboveOrEqual(RankType.MOD)) {

                event.setCancelled(true);
                player.sendMessage(C.color("&cGenel sohbet kanalı şu anda kapatılmış durumda, bir süre sonra tekrar deneyin."));
            } else {
                this.messageHistory.put(player.getUniqueId(), cleanedMessage);
                event.setCancelled(true);

                ConsoleCommandSender console = Bukkit.getConsoleSender();
                console.sendMessage(getFormattedMessage(player, event.getMessage(), console));

                for (Player recipient : event.getRecipients()) {
                    recipient.sendMessage(getFormattedMessage(player, event.getMessage(), recipient));
                }

                ClimpyLibrary.getInstance().getLogManager().formatChatMessage(event.getMessage(), player.getName());
            }
        }
    }

    public double userSkyLevel(com.climpy.skyblock.User user) {
        return Double.parseDouble(NumberUtil.formatNumberByDecimal(user.getIsland().getValue()));
    }


    private String getFormattedMessage(Player player, String message, CommandSender viewer) {
        User user = ProfilePlugin.getInstance().getUserManager().getUser(player.getName());
        com.climpy.skyblock.User userSky = com.climpy.skyblock.User.getUser(player);

        if (user.getRankType().isAboveOrEqual(RankType.MOD)) {
            if (userSky.getIsland() != null) {
                return user.getSymbolType().getPrefix() + user.getRankType().getPrefix() + player.getName() + "§f: §7" + message;
            } else {
                return user.getSymbolType().getPrefix() + user.getRankType().getPrefix() + player.getName() + "§f: §f" + message;
            }
        }


        if (user.getRankType().isAboveOrEqual(RankType.VIP)) {
            if (userSky.getIsland() != null) {
                return "§7" + userSkyLevel(userSky) + " §f" + user.getSymbolType().getPrefix() + user.getRankType().getPrefix() + player.getName() + "§f: §7" + message;
            } else {
                return user.getSymbolType().getPrefix() + user.getRankType().getPrefix() + player.getName() + "§f: §f" + message;
            }
        }

        //TODO: Sorunlu(bazı insanlar gibi) yetkiye sahip olanlar için Chat Formatı;
        else {
            if (userSky.getIsland() != null) {
                return "§7" + userSkyLevel(userSky) + " §f" + user.getRankType().getPrefix() + player.getName() + "§f: §f" + message;
            } else {
                return user.getSymbolType().getPrefix() + user.getRankType().getPrefix() + player.getName() + "§f: §f" + message;
            }
        }
    }
}
