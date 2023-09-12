package org.dodopredo.minecord.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatusSorter {

    public static List<OfflinePlayer> getByTimePlayed(){

        List<OfflinePlayer> topRankList = new ArrayList<>();

        for (OfflinePlayer player : OfflinePlayers.getListRaw()){

            topRankList.add(player);

        }

        Integer x = 0;
        for (OfflinePlayer player : OfflinePlayers.getListRaw()){

            for (OfflinePlayer player1 : topRankList){

                if (OfflinePlayers.getTimePlayed(player1) > OfflinePlayers.getTimePlayed(player)){

                    topRankList.set(x, player1);

                }

            }

            x++;

        }

        System.out.println("Rank List:");
        for (OfflinePlayer player : topRankList){
            System.out.println(player.getName());
        }

        return topRankList;

    }

}
