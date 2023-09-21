package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.utils.interfaces.ICommand;

import java.awt.*;
import java.util.List;

public class Ping implements ICommand {
    @Override
    public String getCommandName() {
        return "ping";
    }

    @Override
    public String getCommandDescription() {
        return "Mostra a latência do bot.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        return null;
    }

    @Override
    public List<String[]> getAutoComplete() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        long botPingValue= Minecord.getJda().getGatewayPing();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(String.format("%s ms", botPingValue));
        embed.setColor(new Color(45, 162, 10));
        event.getHook().sendMessageEmbeds(embed.build()).queue();
    }
}
