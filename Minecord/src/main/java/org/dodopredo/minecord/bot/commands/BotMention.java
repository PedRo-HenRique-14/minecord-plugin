package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.dodopredo.minecord.Minecord;

import java.awt.*;

public class BotMention {
    public static void mentionEvent(MessageReceivedEvent event){
        if (!event.getMember().getUser().isBot()){
            Member member = event.getMember();
            SelfUser selfUser = Minecord.getJda().getSelfUser();
            if (event.getMessage().getContentRaw().trim().equals(selfUser.getAsMention())){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(member.getUser().getName(), member.getEffectiveAvatarUrl(),member.getEffectiveAvatarUrl());
                embed.setTitle(String.format("Olá %s! Como vai?", member.getEffectiveName()));
                embed.setDescription(String.format("Eu sou o %s, e aqui vai algumas informações uteis pra você:", selfUser.getAsMention()));
                embed.addField("Precisa de ajuda?", "Utilize o comando `/help`.", false);
                embed.addField("Meu prefixo?", "Eu funciono inteiramente com comandos de `/`.", false);
                embed.addField("Se tiver alguma dúvida...", "[Acesse a minha wiki aqui!](https://github.com/PedRo-HenRique-14/Minecord-Wiki)", false);
                embed.setColor(new Color(68, 68, 68));
                embed.setThumbnail(selfUser.getAvatarUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
        }
    }
}
