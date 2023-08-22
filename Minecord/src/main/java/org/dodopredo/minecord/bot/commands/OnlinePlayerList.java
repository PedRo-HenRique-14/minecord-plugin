package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;
import org.dodopredo.minecord.plugin.util.OnlinePlayers;

import java.util.ArrayList;
import java.util.List;

public class OnlinePlayerList implements ICommand {
    @Override
    public String getCommandName() {
        return "jogadores_online";
    }

    @Override
    public String getCommandDescription() {
        return "Retorna a lista de jogadores online no servidor do Minecraft.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        List<OptionData> commandOptions = new ArrayList<>();

        commandOptions.add(new OptionData(OptionType.INTEGER, "página", "Página da lista.", false));

        return commandOptions;
    }

    @Override
    public List<String[]> getAutoComplete() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        String playersName = "";

        Integer pageOption = 0;
        Double finalPage = Math.ceil(OnlinePlayers.getList().length / 5 + 0.5);

        if (event.getOption("página") != null) {

            pageOption = event.getOption("página").getAsInt();

            if (pageOption < 1 || pageOption > finalPage) {

                pageOption = 1;

            }


        } else {

            pageOption = 1;

        }

        Integer playerSectionFinal = pageOption * 5;
        Integer playerSectionInitial = playerSectionFinal - 5;

        if (playerSectionFinal > OnlinePlayers.getList().length) {
            playerSectionFinal = OnlinePlayers.getList().length;
        }

        for (Integer x = 0; x <= 4; x++){

            if (x < playerSectionFinal - playerSectionInitial) {

                playersName += String.format("\n%s", OnlinePlayers.getList()[playerSectionInitial + x].getName());

            } else {

                break;

            }

        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(String.format("%s de %.0f página(s).", pageOption, finalPage));
        embed.setDescription(String.format("**Lista de Jogadores Online**:%s", playersName));
        embed.setThumbnail("https://cdn.discordapp.com/attachments/1115799046234321008/1126969666384969869/OIG_-_2023-04-26T014120.png");
        embed.addField("Total:", String.format("%s Online.", OnlinePlayers.getInt()), false);

        event.getHook().sendMessageEmbeds(embed.build()).queue();
    }
}
