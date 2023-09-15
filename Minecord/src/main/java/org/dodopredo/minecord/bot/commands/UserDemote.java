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

public class UserDemote implements ICommand {
    @Override
    public String getCommandName() {
        return "rebaixar";
    }

    @Override
    public String getCommandDescription() {
        return "Demite e/ou rebaixa um usuário de sua profissão.";
    }

    @Override
    public List<OptionData> getCommandOptions() {

        List<OptionData> commandOptions = new ArrayList<>();
        commandOptions.add(new OptionData(OptionType.USER, "usuário", "Nome do usuário que deseja demitir e/ou rebaixar.", true));
        commandOptions.add(new OptionData(OptionType.ROLE, "cargo", "Cargo que deseja demitir o usuário.", false));
        commandOptions.add(new OptionData(OptionType.STRING, "remover_cargo_chefe", "Remover o cargo de chefe desse usuário?", false).setAutoComplete(true));
        return commandOptions;

    }

    @Override
    public List<String[]> getAutoComplete() {

        List<String[]> autoComplete = new ArrayList<>();
        String[] removerCargoChefeAutoComplete = new String[]{"remover_cargo_chefe", "Sim", "Não"};
        autoComplete.add(removerCargoChefeAutoComplete);
        return autoComplete;

    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        Guild guild = Minecord.getJda().getGuildById(Minecord.GUILD_ID);
        Role bossRole = Minecord.getJda().getRoleById(Minecord.BOSS_ROLE_ID);
        Member memberSelected = event.getOption("usuário").getAsMember();
        Role roleOption;
        String removeBossRole;
        Boolean validRoleOption = false;
        Boolean commandExecutorHasPermission = false;
        Boolean userHasTheRole = false;
        Boolean userHasTheBossRole = false;

        if (event.getOption("cargo") != null) {
            roleOption = event.getOption("cargo").getAsRole();
            for (Long role_id : Minecord.PROFESSIONS_ROLES) { //Verifica se o cargo escolhido pelo usuário está na lista de profissões válidas
                if (role_id.toString().equals(roleOption.getId())) {
                    validRoleOption = true;
                    break;
                }
            }
            for (Role userRole : memberSelected.getRoles()){
                if (userRole.getId().equals(roleOption.getId())){
                    userHasTheRole = true;
                }
            }
        } else {
            roleOption = null;
        }

        if (event.getOption("remover_cargo_chefe") != null) {
            removeBossRole = event.getOption("remover_cargo_chefe").getAsString().toLowerCase();
        } else {
            removeBossRole = "não";
        }


        if (removeBossRole == "não" && roleOption == null){
            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Nenhuma ação selecionada.").queue();
            return;
        }

        if (!removeBossRole.equals("sim") && !removeBossRole.equals("não")){
            event.deferReply().setEphemeral(true).queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("❎ Erro!");
            embedBuilder.setDescription("A resposta para `adicionar_cargo_chefe` deve ser **sim** ou **não**.");
            embedBuilder.setColor(Color.RED);
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        for (Long roleId : Minecord.WHITELISTED_PROFESSIONS_ROLE){ //Verifica se o usuário que executou o comando tem permissão de executá-lo
            for (Role userRoleId : event.getMember().getRoles()){
                if (roleId.toString().equals(userRoleId.getId())){
                    commandExecutorHasPermission = true;
                    break;
                }
            }
        }

        try {
            for (Role userRole : memberSelected.getRoles()) {
                if (userRole.getId().equals(bossRole.getId())) {
                    userHasTheBossRole = true;
                }
            }
        } catch (NullPointerException e){
            Minecord.sendConsoleMessage("BOSS_ROLE_ID is null.");
            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Um erro aconteceu! Mas fique tranquilo(a), eu reportei aos ADMs.").queue();
            return;
        }

        String description = "";
        if (commandExecutorHasPermission){
            System.out.println("userHastTheBossRole: " + userHasTheBossRole);
            System.out.println("removeBossRole: " + removeBossRole);
            if (userHasTheBossRole && removeBossRole.equals("sim")){
                guild.removeRoleFromMember(memberSelected, bossRole).queue();
                description += String.format("%s ", bossRole.getAsMention());
            } else {
                if (removeBossRole.equals("sim")) {
                    if (userHasTheBossRole == false) {
                        event.deferReply().queue();
                        event.getHook().sendMessage("O usuário não tem cargo de chefe.").queue();
                        return;
                    }
                }
            }

            if (validRoleOption && userHasTheRole){
                guild.removeRoleFromMember(memberSelected, roleOption).queue();
                description += String.format("%s ", roleOption.getAsMention());
            } else {
                if (roleOption != null) {
                    if (validRoleOption == false) {
                        event.deferReply().setEphemeral(true).queue();
                        event.getHook().sendMessage("Cargo selecionado inválido.").queue();
                        return;
                    } else if (userHasTheBossRole == false) {
                        event.deferReply().setEphemeral(true).queue();
                        event.getHook().sendMessage("O usuário não tem o cargo selecionado, nenhuma ação necessária.").queue();
                        return;
                    }
                }
            }
            event.deferReply().queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("✅ Cargo(s) removido(s) com sucesso!");
            embedBuilder.setDescription(String.format("Os seguintes cargos foram retirados de %s: %s", memberSelected.getAsMention(), description));
            embedBuilder.setThumbnail(memberSelected.getAvatarUrl());
            embedBuilder.setColor(new Color(28, 121, 171));
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        EmbedBuilder embedError = new EmbedBuilder();
        embedError.setTitle("❎ Você não tem permissão para executar este comando!");
        embedError.setColor(new Color(157, 10, 10));
        event.deferReply().queue();
        event.getHook().sendMessageEmbeds(embedError.build()).queue();

    }
}
