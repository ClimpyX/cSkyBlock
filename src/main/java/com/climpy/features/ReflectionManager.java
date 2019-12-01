package com.climpy.features;

import com.climpy.SkyBlockC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class ReflectionManager
{
  public static void sendPacket(Player p, Object packet) {
    try {
      Object playerHandle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
      Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
      playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
    } catch (Exception ex) {
      SkyBlockC.getInstance().getLogger().warning(ex.getMessage());
    } 
  }
  
  public static Class<?> getNMSClass(String name) {
    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try {
      return Class.forName("net.minecraft.server." + version + "." + name);
    } catch (Exception ex) {
      SkyBlockC.getInstance().getLogger().warning(ex.getMessage());
      
      return null;
    } 
  }
}
