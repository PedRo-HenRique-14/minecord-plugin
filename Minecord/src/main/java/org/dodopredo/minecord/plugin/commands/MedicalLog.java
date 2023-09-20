package org.dodopredo.minecord.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dodopredo.minecord.Minecord;
import org.dodopredo.minecord.utils.ChatBroadcast;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MedicalLog implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player){
            if (strings.length != 2){
                player.sendMessage(ChatColor.RED + "Registro inválido. Exemplo: /registro-medico <paciente> <remédio>");
            } else {
                Player target = Bukkit.getServer().getPlayerExact(strings[0]);
                if (target != null){
                    String message = String.format("O médico `%s` tratou o paciente `%s` com o remédio `%s`.", player.getDisplayName(), target.getDisplayName(), strings[1]);
                    ChatBroadcast.sendToDiscordEmbed(Minecord.MEDICAL_LOG_CHANNEL_ID, "Registro médico.", message, new Color(168, 15, 15), player, String.format("https://mc-heads.net/avatar/%s", target.getName()));
                    player.sendMessage(ChatColor.GREEN + "Registro efetuado com sucesso!");
                } else {
                    player.sendMessage(ChatColor.RED + "O paciente selecionado é inválido ou está offline.");
                }
            }
        }
        return true;
    }
}
