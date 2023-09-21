package org.dodopredo.minecord.plugin.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.dodopredo.minecord.Minecord;

import java.awt.*;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        // Quando um player manda mensagem no chat, uma embed é criada e enviada para um canal do Discord.
        Player player = event.getPlayer();

        if (Minecord.GLOBAL_CHANNEL_ENABLE) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0, 0, 0));
            embed.setAuthor(player.getDisplayName(), null, String.format("https://mc-heads.net/avatarr/%s", player.getUniqueId()));
            embed.setDescription(event.getMessage());

            TextChannel channel = Minecord.getJda().getTextChannelById(Minecord.GLOBAL_CHANNEL_ID);
            channel.sendMessageEmbeds(embed.build()).queue();
        }
    }

}
