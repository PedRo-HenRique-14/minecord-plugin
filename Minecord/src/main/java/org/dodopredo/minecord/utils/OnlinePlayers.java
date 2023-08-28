package org.dodopredo.minecord.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class OnlinePlayers {

    public static Player[] getList(){

        Player[] playerList;

        Integer numberOfPlayersOnline = Bukkit.getServer().getOnlinePlayers().size();

        playerList = new Player[numberOfPlayersOnline];

        Integer playerIndex = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            playerList[playerIndex] = player;
            playerIndex++;

        }

        return playerList;
    }

    public static Integer getInt(){
        return Bukkit.getServer().getOnlinePlayers().size();
    }

    public static Integer getMaxLenght(){
        return Bukkit.getServer().getMaxPlayers();
    }

    public static Double getTimePlayed(OfflinePlayer player){

        Double timePlayed;
        timePlayed = Double.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 72000);
        return timePlayed;

    }

}
