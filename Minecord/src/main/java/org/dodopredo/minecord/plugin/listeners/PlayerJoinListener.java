package org.dodopredo.minecord.plugin.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.dodopredo.minecord.Minecord;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (Minecord.GLOBAL_CHANNEL_ENABLE) {
            TextChannel channel = Minecord.getJda().getTextChannelById(Minecord.GLOBAL_CHANNEL_ID);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(player.getDisplayName(), null, String.format("https://mc-heads.net/avatar/%s", player.getUniqueId()));
            embed.setDescription(event.getJoinMessage());
            channel.sendMessageEmbeds(embed.build()).queue();
            System.out.println(ChatColor.translateAlternateColorCodes('§', event.getJoinMessage()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (Minecord.GLOBAL_CHANNEL_ENABLE) {
            TextChannel channel = Minecord.getJda().getTextChannelById(Minecord.GLOBAL_CHANNEL_ID);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(player.getDisplayName(), null, String.format("https://mc-heads.net/avatar/%s", player.getUniqueId()));
            embed.setDescription(event.getQuitMessage());
            channel.sendMessageEmbeds(embed.build()).queue();
        }
    }
}
