package org.dodopredo.minecord.bot.util.interfaces;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface ICommand {

    String getCommandName();
    String getCommandDescription();

    List<OptionData> getCommandOptions();

    /*
    Para o auto complete funcionar, é necessário ativar o auto complete na Option data no método getCommandOptions()
    new OptionData(OptionType.STRING, "name", "description", true).setAutoComplete(true));

    Durante a criação do auto complete, é obrigatório colocar o nome do OptionData no index 0, em seguida, é possível adicionar as palavras do auto complete
     */
    List<String[]> getAutoComplete();

    void execute(SlashCommandInteractionEvent event);

}
