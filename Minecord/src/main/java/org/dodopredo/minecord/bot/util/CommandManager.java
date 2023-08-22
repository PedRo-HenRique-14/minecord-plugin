package org.dodopredo.minecord.bot.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.bukkit.Bukkit;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandManager extends ListenerAdapter {
    private List<ICommand> commands = new ArrayList<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        /*
        Ao executar um comando, cada ICommand em List Commands será verificado, e se o nome do ICommand for igual ao
        comando do event, o comando será executado
         */
        for (ICommand command : commands){
            if (command.getCommandName().equals(event.getName())){
                command.execute(event);
                return;
            }
        }

    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        /*
        Carrega o auto completar dos comandos.
         */
        for (ICommand command : commands){
            if (command.getCommandName().equals(event.getName())){
                for (String[] autoComplete : command.getAutoComplete()){
                    if (event.getFocusedOption().getName().equals(autoComplete[0])){
                        String[] words = new String[autoComplete.length - 1];

                        for (Integer x = 1; x < autoComplete.length; x++){

                            words[x - 1] = autoComplete[x];
                        }

                        List<Command.Choice> options = Stream.of(words)
                                .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                                .map(word -> new Command.Choice(word, word))
                                .collect(Collectors.toList());
                        event.replyChoices(options).queue();

                    }
                }

            }
        }

    }

    public void updateSlashCommands(Long guildId) {
        try {

            Guild guild = Minecord.getJda().getGuildById(guildId);

            // Atualiza todos os slash commands
            for (ICommand command : commands){

                System.out.println(String.format("Carregando comando: %s.", command.getCommandName()));

                if (command.getCommandOptions() != null) {

                    System.out.println("Data Option: true.");

                    guild.upsertCommand(command.getCommandName(), command.getCommandDescription())
                            .addOptions(command.getCommandOptions())
                            .queue();

                } else {

                    System.out.println("Data Option: false.");

                    guild.upsertCommand(command.getCommandName(), command.getCommandDescription())
                            .queue();
                }
            }
            System.out.println("Todos os comandos foram carregados.");

        }catch (NullPointerException e){

            Bukkit.getConsoleSender().sendMessage("Command update error. Check the Guild ID.");

        }


    }

    public void add(ICommand command){
        // Adiciona um comando na lista de commandos
        commands.add(command);
    }

}
