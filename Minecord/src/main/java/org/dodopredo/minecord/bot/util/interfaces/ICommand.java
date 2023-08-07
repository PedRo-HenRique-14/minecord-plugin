package org.dodopredo.minecord.bot.util.interfaces;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface ICommand {

    String getCommandName();
    String getCommandDescription();

    List<OptionData> getCommandOptions();

    void execute(SlashCommandInteractionEvent event);

}
