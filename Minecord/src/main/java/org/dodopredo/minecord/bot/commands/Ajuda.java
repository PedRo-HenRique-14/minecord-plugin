package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;

import java.util.List;

public class Ajuda implements ICommand {
    @Override
    public String getCommandName() {
        return "ajuda";
    }

    @Override
    public String getCommandDescription() {
        return "Um menu contendo todos os comandos disponíveis.";
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
        event.deferReply().queue();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Vai uma mãozinha aí?");
        embed.setDescription("Lista dos comandos mais úteis:\n" +
                "`ajuda` - Este menu serve para te ajudar com os comandos do bot\n" +
                "`jogadores_online [página]` - Mostra uma lista com os jogadores online no servidor;\n" +
                "`info_jogador [UUID/nome]`- Mostra algumas informações sobre um jogador específico;\n" +
                "`info_servidor` - Algumas informações sobre o servidor;\n" +
                "`ping` - Latência do bot;\n" +
                "`versão` - Verifica a versão atual do plugin.\n\n" +
                "Caso você tenha dúvidas sobre a utilização dos comandos, " +
                "você pode acessar a wiki do bot clicando [aqui](https://github.com/PedRo-HenRique-14/Minecord-Wiki).");
        event.getHook().sendMessageEmbeds(embed.build()).queue();
    }
}
