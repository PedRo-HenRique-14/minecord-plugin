package org.dodopredo.minecord.bot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.dodopredo.minecord.bot.commands.BotMention;
import org.dodopredo.minecord.utils.ChatBroadcast;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        ChatBroadcast.sendToMinecraft(event);
        BotMention.mentionEvent(event);
    }
}
