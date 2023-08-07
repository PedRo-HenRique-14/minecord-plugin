package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;
import org.dodopredo.minecord.plugin.util.OfflinePlayers;
import org.dodopredo.minecord.plugin.util.OnlinePlayers;
import org.dodopredo.minecord.plugin.util.ServerData;

import java.util.List;

public class MinecraftInfo implements ICommand {
    @Override
    public String getCommandName() {
        return "info_servidor";
    }

    @Override
    public String getCommandDescription() {
        return "Lista as informações do servidor do Minecraft.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        Integer numberOnlinePlayers = OnlinePlayers.getInt();
        Integer numberOfflinePlayers = OfflinePlayers.getInt();

        String serverIp = ServerData.getIp();
        String serverVersion = ServerData.getVersion();
        String minecraftOriginal;

        Boolean onlineMode = ServerData.getOnlineMode();

        if (onlineMode != true){
            minecraftOriginal = "Não";
        } else {
            minecraftOriginal = "Sim";
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("✨ Arísia Roleplay ✨");
        embed.setThumbnail("https://cdn.discordapp.com/attachments/1115799046234321008/1126970134796439612/OIG_64.png");
        embed.addField("IP:", String.format("", serverIp), false);
        embed.addField("Versão do servidor:", String.format("%s", serverVersion), false);
        embed.addField("\uD83D\uDFE2 Jogadores Online:", String.format("%s/%s Online.", numberOnlinePlayers, OnlinePlayers.getMaxLenght()), true);
        embed.addField("\uD83D\uDD34 Jogadores Offline:", String.format("%s Offline", numberOfflinePlayers - numberOnlinePlayers), true);
        embed.addField("Apenas para Minecraft Original?", String.format("%s", minecraftOriginal), false);

        event.getHook().sendMessageEmbeds(embed.build()).queue();
    }
}
