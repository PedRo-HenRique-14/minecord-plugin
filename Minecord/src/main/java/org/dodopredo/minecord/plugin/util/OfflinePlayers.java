package org.dodopredo.minecord.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayers {

    public static Integer getInt(){
        return Bukkit.getServer().getOfflinePlayers().length;
    }

    public static OfflinePlayer[] getListRaw(){
        return Bukkit.getServer().getOfflinePlayers();
    }

}
