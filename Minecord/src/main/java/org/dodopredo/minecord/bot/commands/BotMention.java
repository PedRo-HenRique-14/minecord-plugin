package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.dodopredo.minecord.Minecord;

import java.awt.*;

public class BotMention {
    public static void mentionEvent(MessageReceivedEvent event){
        if (!event.getMember().getUser().isBot()){
            Member member = event.getMember();
            User arisiaBot = Minecord.getJda().getUserById(1077692558056112298L);
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

            if (event.getMessage().getContentRaw().trim().equals(arisiaBot.getAsMention())){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(member.getUser().getName(), member.getEffectiveAvatarUrl(),member.getEffectiveAvatarUrl());
                embed.setTitle("Opa %s, bão?", member.getEffectiveName());
                embed.setDescription("Está precisando de ajuda com o código? Se a resposta for não, pode ignorar esta mensagem. Mas se a resposta for sim, eu vou te explicar como funciona. Faz assim, vai no **privado** do %s e envia esse `código` que apareceu na sua tela quando você tentou entrar em Arísia. Depois de enviar o código, você estará livre para jogar! Se tiver qualquer dúvida, não exite em perguntar.");
                embed.setColor(new Color(68, 68, 68));
                embed.setThumbnail(arisiaBot.getAvatarUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
        }
    }
}
