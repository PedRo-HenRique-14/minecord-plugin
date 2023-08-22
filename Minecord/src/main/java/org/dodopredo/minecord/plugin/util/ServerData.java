package org.dodopredo.minecord.plugin.util;

import org.bukkit.Bukkit;

public class ServerData {
    public static String getIp(){
        return Bukkit.getIp();
    }

    public static Boolean getOnlineMode(){
        return Bukkit.getServer().getOnlineMode();
    }

    public static String getVersion(){
        String[] version = Bukkit.getServer().getVersion().split(" ");
        return version[2].replace(")", "");
    }

    public static String getName(){
        String serverName = Bukkit.getServer().getName();
        return serverName;
    }

}
