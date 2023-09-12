package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.utils.interfaces.ICommand;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserPromote implements ICommand {
    @Override
    public String getCommandName() {
        return "promover";
    }

    @Override
    public String getCommandDescription() {
        return "Promove um usuário a um cargo.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        List<OptionData> commandOptions = new ArrayList<>();

        commandOptions.add(new OptionData(OptionType.USER, "usuário", "Nome do usuário", true));
        commandOptions.add(new OptionData(OptionType.ROLE, "cargo", "Cargo para promover o usuário", true));
        commandOptions.add(new OptionData(OptionType.STRING, "adicionar_cargo_chefe", "Adicionar o cargo de chefe nesse usuário?", true).setAutoComplete(true));

        return commandOptions;
    }

    @Override
    public List<String[]> getAutoComplete() {
        List<String[]> autoComplete = new ArrayList<>();
        String[] cargoChefeAutoComplete = new String[]{"adicionar_cargo_chefe", "Sim", "Não"};
        autoComplete.add(cargoChefeAutoComplete);
        return autoComplete;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = Minecord.getJda().getGuildById(Minecord.GUILD_ID);
        Member member = event.getOption("usuário").getAsMember();
        Role roleOption = event.getOption("cargo").getAsRole();
        Role bossRole = Minecord.getJda().getRoleById(Minecord.BOSS_ROLE_ID);
        String addBossRole = event.getOption("adicionar_cargo_chefe").getAsString().toLowerCase();
        Boolean validRoleOption = false;
        Boolean commandExecutorHasPermission = false;

        if (!addBossRole.equals("sim") && !addBossRole.equals("não")){
            event.deferReply().setEphemeral(true).queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("❎ Erro!");
            embedBuilder.setDescription("A resposta para `adicionar_cargo_chefe` deve ser **sim** ou **não**.");
            embedBuilder.setColor(Color.RED);
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        System.out.println("Membro: "+member.getNickname());
        System.out.println("ID (membro): "+member.getId());
        System.out.println("Cargo: "+roleOption.getName());
        System.out.println("ID (cargo): "+roleOption.getId());
        System.out.println("Guild: "+guild.getName());
        System.out.println("ID (guild): "+guild.getId());

        for (Long role_id : Minecord.PROFESSIONS_ROLES){ //Verifica se o cargo escolhido pelo usuário está na lista de profissões válidas
            System.out.println(role_id + "/" + roleOption.getId());
            if (role_id.toString().equals(roleOption.getId())){
                validRoleOption = true;
                break;
            }

        }
        System.out.println("Valid oprion: " + validRoleOption);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        for (Long roleId : Minecord.WHITELISTED_PROFESSIONS_ROLE){ //Verifica se o usuário que executou o comando tem permissão de executá-lo

            for (Role userRoleId : event.getMember().getRoles()){
                System.out.println(roleId + "/" + userRoleId.getId());
                if (roleId.toString().equals(userRoleId.getId()) || event.getMember().getRoles().contains(Minecord.getJda().getRoleById(Minecord.BOSS_ROLE_ID))){
                    commandExecutorHasPermission = true;
                    break;
                }

            }

        }
        System.out.println("Command executor has permission: " + commandExecutorHasPermission);

        if (commandExecutorHasPermission){

            if (validRoleOption){
                event.deferReply().queue();
                System.out.println("Adicionar cargo de chefe: " + addBossRole);
                String rolesDescription = "";
                if (addBossRole.equals("sim")) {
                    guild.addRoleToMember(member, bossRole).queue();
                    rolesDescription += String.format("%s, ", bossRole.getAsMention());
                }
                guild.addRoleToMember(member, roleOption).queue();
                rolesDescription += String.format("%s", roleOption.getAsMention());
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("✅ Cargo atribuído com sucesso!");
                embedBuilder.setDescription(String.format("Foram atribuídos os seguintes cargos: %s.", rolesDescription));
                embedBuilder.setColor(new Color(46, 171, 28));
                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                event.deferReply().setEphemeral(true).queue();
                event.getHook().sendMessage("O cargo selecionado é inválido!").queue();
            }

        } else {
            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Você não tem permissão de utilizar este comando!").queue();
        }


    }
}
