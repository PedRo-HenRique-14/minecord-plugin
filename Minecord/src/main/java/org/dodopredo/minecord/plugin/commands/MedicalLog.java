package org.dodopredo.minecord.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.utils.ChatBroadcast;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalLog implements CommandExecutor, TabCompleter {

    String[] treatments = new String[]{"gesso", "antibiótico", "kit-médico"};
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player){
            if (strings.length != 2){
                player.sendMessage(ChatColor.RED + "Registro inválido. Exemplo: /registro-medico <paciente> <remédio>");
            } else {
                Player target = Bukkit.getServer().getPlayerExact(strings[0]);
                Boolean validTreatment = false;
                for (String element : treatments){
                    if (element.equals(strings[1].toLowerCase())){
                        validTreatment = true;
                    }
                }
                if (target != null){
                    if (validTreatment) {
                        String message = String.format("O médico `%s` tratou o paciente `%s` com o remédio `%s`.", player.getDisplayName(), target.getDisplayName(), strings[1].toLowerCase());
                        ChatBroadcast.sendToDiscordEmbed(Minecord.MEDICAL_LOG_CHANNEL_ID, "Registro médico.", message, new Color(168, 15, 15), player, String.format("https://mc-heads.net/avatar/%s", target.getName()));
                        player.sendMessage(ChatColor.GREEN + "Registro efetuado com sucesso!");
                    } else {
                        player.sendMessage(ChatColor.RED + "O tratamento selecionado é inválido.");
                        return true;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "O paciente selecionado é inválido ou está offline.");
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final List<String> completions = new ArrayList<>();
        if (strings.length == 1){
            for (Player player : Bukkit.getServer().getOnlinePlayers()){
                completions.add(player.getName());
            }
        }
        if (strings.length == 2){
            completions.addAll(Arrays.asList(treatments));
        }
        return completions;
    }
}
