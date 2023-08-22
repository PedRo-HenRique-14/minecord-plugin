package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;

import java.util.ArrayList;
import java.util.List;

public class StatusRank implements ICommand {

    @Override
    public String getCommandName() {
        return "top";
    }

    @Override
    public String getCommandDescription() {
        return "Mostra o rank dos status da categoria selecionada.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        List<OptionData> commandOptions = new ArrayList<>();

        commandOptions.add(new OptionData(OptionType.STRING, "categoria", "Qual a categoria desejada?", true).setAutoComplete(true));

        return commandOptions;
    }

    @Override
    public List<String[]> getAutoComplete() {

        List<String[]> autoComplete = new ArrayList<>();
        autoComplete.add(new String[]{"categoria", "tempo de jogo"});

        return autoComplete;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }
}
