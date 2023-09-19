package org.dodopredo.minecord.utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.dodopredo.minecord.Minecord;

public class ChatBroadcast {
    public static void sendToMinecraft(MessageReceivedEvent event){

        try {
            if (!event.getMember().getUser().isBot()) {
                if (Minecord.DISCORD_TO_MINECRAFT_ENABLE) {
                    if (event.getChannel().equals(Minecord.getJda().getTextChannelById(Minecord.DISCORD_TO_MINECRAFT_CHANNEL))) {
                        // Sempre que uma mensagem for enviada no canal DISCORD_TO_MINECRAFT_CHANNEL, ela será transmitida para o chat do jogo

                        String userName = event.getAuthor().getEffectiveName();
                        String message = event.getMessage().getContentRaw();

                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.format("[&1DISCORD&f] %s: %s", userName, message)));
                    }
                }
            }
        } catch (NullPointerException e) {}

    }
    public static void sendToDiscord(){

    }
}
