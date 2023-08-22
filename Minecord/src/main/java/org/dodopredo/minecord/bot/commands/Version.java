package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;

import java.util.List;

public class Version implements ICommand {
    @Override
    public String getCommandName() {
        return "versão";
    }

    @Override
    public String getCommandDescription() {
        return "Mostra a versão atual do plugin.";
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
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Versão atual do plugin:");
        embed.setDescription(String.format("%s", Minecord.PLUGIN_VERSION));
        event.getHook().sendMessageEmbeds(embed.build()).queue();
    }
}
