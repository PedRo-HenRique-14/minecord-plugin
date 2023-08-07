package org.dodopredo.minecord.plugin.util;

import org.bukkit.Bukkit;
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

}
