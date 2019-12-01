package com.climpy.menus;

import com.climpy.SkyBlockC;
import com.climpy.inv.ClickableItem;
import com.climpy.inv.SmartInventory;
import com.climpy.inv.content.InventoryContents;
import com.climpy.inv.content.InventoryProvider;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.user.User;
import com.climpy.skyblock.SkyBlockPlugin;
import com.climpy.skyblock.Utils;
import com.climpy.util.C;
import com.climpy.util.ItemUtils;
import com.climpy.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SkyblockMenu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("skyblockMenu")
            .provider(new SkyblockMenu())
            .size(6, 9)
            .title(C.color("&8§l» &8SkyBlock Menüsü"))
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

    }

    @Override
    public void update(Player player, InventoryContents contents) {
        int state = contents.property("state", 0);
        contents.setProperty("state", state + 1);

        if (state % 5 != 0)
            return;

        final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        User user = ProfilePlugin.getInstance().getUserManager().getUser(player.getName());
        com.climpy.user.User userSky = SkyBlockC.getInstance().getUserManager().getUser(player.getName());
        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData())));

        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName(ChatColor.GRAY + user.getRankType().getPrefix() + player.getName());
        ArrayList<String> loreSkull = new ArrayList<String>();
        loreSkull.add(C.color("&7Ada Seviyesi: &60"));
        loreSkull.add(" ");
        loreSkull.add(C.color("&7Sıralama: &a0"));
        loreSkull.add(C.color("&7Spawnerlar: &a0"));
        loreSkull.add(C.color("&7Yardımcılar: &a0"));
        loreSkull.add(" ");
        loreSkull.add(C.color("&7Oynama Süresi: &b" + TimeUtils.getDurationWords(userSky.getPlayTime(), true, false)));
        loreSkull.add(C.color("&7Sunucu: &b" + SkyBlockC.getInstance().getConfig().getString("server-name")));
        skullMeta.setLore(loreSkull);
        skull.setItemMeta(skullMeta);

        contents.set(0, 4, ClickableItem.empty(new ItemStack(skull)));

        final ItemStack closeMenu = new ItemStack(Material.BARRIER, 1);
        final ItemMeta closeMenuMeta = (ItemMeta) closeMenu.getItemMeta();

        closeMenuMeta.setDisplayName(C.color("&cMenüyü kapat"));
        closeMenu.setItemMeta(closeMenuMeta);

        contents.set(5, 4, ClickableItem.of(new ItemStack(closeMenu),
                e -> player.closeInventory()));

        com.climpy.skyblock.User u = SkyBlockPlugin.getInstance().getUserManager().getUser(player.getName());


        final String[] lores = new String[50];
        lores[0] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[1] = ChatColor.translateAlternateColorCodes('&', "&7Sen: &6" + player.getName());
        lores[2] = ChatColor.translateAlternateColorCodes('&', "&7Ada Seviyesi: &6" + u.getIsland().getValue());
        lores[3] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[4] = ChatColor.translateAlternateColorCodes('&', "&eAdanıza ışınlanmak için tıklayın!");

        lores[5] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[6] = ChatColor.translateAlternateColorCodes('&', "&7Görevleri yaparak kredi kazanabilirsiniz.");
        lores[7] = ChatColor.translateAlternateColorCodes('&', "&7bu sayede harika yükseltici ve güncelleyecileri açabilirsin.");
        lores[8] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[9] = ChatColor.translateAlternateColorCodes('&', "&eGörevleri açmak için tıklayın!");

        lores[10] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[11] = ChatColor.translateAlternateColorCodes('&', "&7Bu menü sizin adanıza erişebilen veya gelebilen");
        lores[12] = ChatColor.translateAlternateColorCodes('&', "&7oyuncuların eylemlerini düzenler. Örneğin; ziyaretçi'ye blok kırma izni.");
        lores[13] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[14] = ChatColor.translateAlternateColorCodes('&', "&eYetkilendirmeyi açmak için tıklayın!");

        lores[15] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[16] = ChatColor.translateAlternateColorCodes('&', "&7Yükselticiler sayesinde, uçma izni alabilir,");
        lores[17] = ChatColor.translateAlternateColorCodes('&', "&7spawner güçlendirebilir, tarımı hızlandırabilir,");
        lores[18] = ChatColor.translateAlternateColorCodes('&', "&7ve son olarak exp almayı kolaylaştırabilirsiniz.");
        lores[19] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[20] = ChatColor.translateAlternateColorCodes('&', "&eYükselticiyi açmak için tıklayın!");

        lores[21] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[22] = ChatColor.translateAlternateColorCodes('&', "&7Güncelleyeciler ile adanızın sınırılarını genişletebilir,");
        lores[23] = ChatColor.translateAlternateColorCodes('&', "&7maksimum üye sayısını arttırabilir,");
        lores[24] = ChatColor.translateAlternateColorCodes('&', "&7maksimum ada warp'ı sınırını yükseltebilir ve üreticinizi güçlendirebilirsiniz.");
        lores[25] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[26] = ChatColor.translateAlternateColorCodes('&', "&eGüncelleyecileri açmak için tıklayın!");

        lores[27] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[28] = ChatColor.translateAlternateColorCodes('&', "&7Ada üyelerini bu menü sayesinde görebilir,");
        lores[29] = ChatColor.translateAlternateColorCodes('&', "&7ada rütbelerini yükseltebilir/düşerebilir veya");
        lores[30] = ChatColor.translateAlternateColorCodes('&', "&7ada üyelerini atmak içinde kullanabilirsiniz.");
        lores[31] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[32] = ChatColor.translateAlternateColorCodes('&', "&eAda üyelerini görmek için tıklayın!");

        lores[33] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[34] = ChatColor.translateAlternateColorCodes('&', "&7Ada sınırının renkleri olduğunu biliyor musunuz?");
        lores[35] = ChatColor.translateAlternateColorCodes('&', "&7Bu özellik ile gözüne en hitap eden rengi seçebilirsin,");
        lores[36] = ChatColor.translateAlternateColorCodes('&', "&7veya sadece sınırı kapatabilirsin bu sayede");
        lores[37] = ChatColor.translateAlternateColorCodes('&', "&7oyun zevkini iki kat arttırabilirsin.");
        lores[38] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[39] = ChatColor.translateAlternateColorCodes('&', "&eSınır renklendirmesini ayarlamak için tıkla!");

        lores[40] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[41] = ChatColor.translateAlternateColorCodes('&', "&7Bu menü ile ada warplarını açabilirsin,");
        lores[42] = ChatColor.translateAlternateColorCodes('&', "&7eğlenceli yerler görebilirsin, hazır ol!");
        lores[43] = ChatColor.translateAlternateColorCodes('&', " ");
        lores[44] = ChatColor.translateAlternateColorCodes('&', "&eAda warplarını açmak için tıkla!");


        contents.set(2, 2, ClickableItem.of(ItemUtils.createItem(Material.GRASS, "§a§lAdanız", Arrays.asList(lores[0], lores[1], lores[2], lores[3], lores[4])),
                e -> u.getIsland().teleportHome(player)));

        contents.set(2, 3, ClickableItem.of(ItemUtils.createItem(Material.COBBLESTONE, "§a§lGörevler", Arrays.asList(lores[5], lores[6], lores[7], lores[8], lores[9])),
                e -> player.openInventory(u.getIsland().getMissionsGUI().getInventory())));

        contents.set(2, 4, ClickableItem.of(ItemUtils.createItem(Material.REDSTONE_COMPARATOR, "§a§lYetkilendirme", Arrays.asList(lores[10], lores[11], lores[12], lores[13], lores[14])),
                e -> player.openInventory(u.getIsland().getPermissionsGUI().getInventory())));

        contents.set(2, 5, ClickableItem.of(ItemUtils.createItem(Material.BEACON, "§a§lYükselticiler", Arrays.asList(lores[15], lores[16], lores[17], lores[18], lores[19], lores[20])),
                e -> player.openInventory(u.getIsland().getBoosterGUI().getInventory())));

        contents.set(2, 6, ClickableItem.of(ItemUtils.createItem(Material.ENCHANTMENT_TABLE, "§a§lGüncelleyiciler", Arrays.asList(lores[21], lores[22], lores[23], lores[24], lores[25], lores[26])),
                e -> player.openInventory(u.getIsland().getUpgradeGUI().getInventory())));


        contents.set(3, 3, ClickableItem.of(ItemUtils.createItem(Material.BED, "§a§lAda Üyeleri", Arrays.asList(lores[27], lores[28], lores[29], lores[30], lores[31], lores[32])),
                e -> player.openInventory(u.getIsland().getMembersGUI().getInventory())));

        contents.set(3, 4, ClickableItem.of(ItemUtils.createItem(Material.PAINTING, "§a§lSınır Renklendirmesi", Arrays.asList(lores[33], lores[34], lores[35], lores[36], lores[37], lores[38], lores[39])),
                e -> player.openInventory(u.getIsland().getBorderColorGUI().getInventory())));

        contents.set(3, 5, ClickableItem.of(ItemUtils.createItem(Material.JUKEBOX, "§a§lAda Warpları", Arrays.asList(lores[40], lores[41], lores[42], lores[43], lores[44])),
                e -> player.openInventory(u.getIsland().getWarpGUI().getInventory())));
    }
}
