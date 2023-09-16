package org.dodopredo.minecord.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

public class OfflinePlayerUtils {

    public static Integer getInt(){
        return Bukkit.getServer().getOfflinePlayers().length;
    }

    public static OfflinePlayer[] getListRaw(){
        return Bukkit.getServer().getOfflinePlayers();
    }

    public static Double getTimePlayed(OfflinePlayer player){

        Double timePlayed;
        timePlayed = Double.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 72000);
        return timePlayed;

    }

}
