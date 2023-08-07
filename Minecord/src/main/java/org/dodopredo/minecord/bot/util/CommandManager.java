package org.dodopredo.minecord.bot.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    public void add(ICommand command){
        // Adiciona um comando na lista de commandos
        commands.add(command);
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

}
