package org.dodopredo.minecord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dodopredo.minecord.Minecord;

import java.awt.*;

public class ChatBroadcast {
    public static void sendToMinecraft(MessageReceivedEvent event){

        try {
            if (!event.getMember().getUser().isBot()) {
                if (Minecord.DISCORD_TO_MINECRAFT_ENABLE) {
                    if (event.getChannel().equals(Minecord.getJda().getTextChannelById(Minecord.DISCORD_TO_MINECRAFT_CHANNEL_ID))) {
                        // Sempre que uma mensagem for enviada no canal DISCORD_TO_MINECRAFT_CHANNEL, ela será transmitida para o chat do jogo

                        String userName = event.getAuthor().getEffectiveName();
                        String message = event.getMessage().getContentRaw();

                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.format("[&1DISCORD&f] %s: %s", userName, message)));
                    }
                }
            }
        } catch (NullPointerException e) {}

    }
    public static void sendToDiscordEmbed(long channelId, String title, String description, Color color, Player author, String thumbURL){
        try {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(title);
            embedBuilder.setDescription(description);
            if (color != null){
                embedBuilder.setColor(color);
            }
            if (author != null){
                embedBuilder.setAuthor(author.getDisplayName());
            }
            if (thumbURL != null){
                embedBuilder.setThumbnail(thumbURL);
            }
            Minecord.getJda().getTextChannelById(channelId).sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (NullPointerException e){
            // oof
        }
    }

    public static void sendToDiscord(long channelId, String message){
        try {
            Minecord.getJda().getTextChannelById(channelId).sendMessage(message);
        } catch (NullPointerException e){
            // oof
        }
    }
}
