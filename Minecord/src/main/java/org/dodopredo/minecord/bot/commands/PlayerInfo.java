package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.dodopredo.minecord.bot.util.interfaces.ICommand;
import org.dodopredo.minecord.plugin.util.OfflinePlayers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayerInfo implements ICommand {

    String playerStatus;
    String playerName;
    String playerUuid;
    String playerDisplayName;
    String isBanned;
    String playerPing;
    String lastPlayed;

    Double timePlayed;

    Boolean playerExists = false;

    @Override
    public String getCommandName() {
        return "info_jogador";
    }

    @Override
    public String getCommandDescription() {
        return "Mostra algumas informações sobre um jogador específico.";
    }

    @Override
    public List<OptionData> getCommandOptions() {

        List<OptionData> commandOptions = new ArrayList<>();

        commandOptions.add(new OptionData(OptionType.STRING, "nome", "Nome do jogador.", false).setAutoComplete(true));
        commandOptions.add(new OptionData(OptionType.STRING, "uuid", "Insira a UUID do jogador.", false));

        return commandOptions;
    }

    @Override
    public List<String[]> getAutoComplete() {
        List<String[]> autoComplete = new ArrayList<>();
        String[] playersNameList = new String[OfflinePlayers.getInt() + 1];

        playersNameList[0] = "nome";

        Integer x = 1;
        for (OfflinePlayer player : OfflinePlayers.getListRaw()){

            playersNameList[x] = player.getName();
            x++;

        }

        autoComplete.add(playersNameList);

        return autoComplete;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        String optionName = "";
        String optionUuid = "";

        if (event.getOption("nome") != null){

            optionName = event.getOption("nome").getAsString();

        } else if (event.getOption("uuid") != null) {

            optionUuid = event.getOption("uuid").getAsString();

        } else {

            event.getHook().sendMessage("É necessário informar o nome ou a UUID do jogador.").queue();
            return;

        }

        OfflinePlayer[] offlinePlayers = OfflinePlayers.getListRaw();

        playerExists = false;

        for (OfflinePlayer player : offlinePlayers){

            if (optionName != ""){

                if (player.getName().trim().toLowerCase().equals(optionName.trim().toLowerCase())){

                    playerExists = getPlayerInformation(player);
                    event.getHook().sendMessageEmbeds(embedBuilder(
                            playerName,
                            playerDisplayName,
                            playerUuid,
                            playerStatus,
                            timePlayed,
                            playerPing,
                            lastPlayed,
                            isBanned
                    )).queue();

                    break;
                }

            } else if (optionUuid != "") {

                if (optionUuid.trim().equals(player.getUniqueId().toString().trim())) {

                    playerExists = getPlayerInformation(player);
                    event.getHook().sendMessageEmbeds(embedBuilder(
                            playerName,
                            playerDisplayName,
                            playerUuid,
                            playerStatus,
                            timePlayed,
                            playerPing,
                            lastPlayed,
                            isBanned)).queue();
                    break;
                }


            }

        }

        if (playerExists != true){

            event.getHook().sendMessage("Jogador não encontrado, verifique as informações e tente novamente. Lembrando que é necessário informar um nome ou uma UUID").queue();

        }

    }

    private Boolean getPlayerInformation(OfflinePlayer player){
        playerName = getPlayerName(player);
        playerUuid = getPlayerUuid(player);
        playerDisplayName = getDisplayName(player);
        playerStatus = getStatus(player);
        timePlayed = getTimePlayed(player);
        playerPing = getPing(player);
        lastPlayed = getLastPlayed(player);
        isBanned = isBanned(player);
        return true;
    }

    private MessageEmbed embedBuilder(String name, String displayName, String uuid, String status, Double timePlayed, String playerPing, String lastPlayed, String isBanned){
        EmbedBuilder embed = new EmbedBuilder();
        DecimalFormat df = new DecimalFormat("0.0");
        embed.setTitle(String.format("%s", name));
        embed.setDescription("**INFORMAÇÕES DO JOGADOR**:");
        embed.setThumbnail(String.format("https://mc-heads.net/avatar/%s", name));
        embed.addField("Nome de exibição:", String.format("%s", displayName), false);
        embed.addField("Status:", String.format("%s", status), true)
                .addField("Ping:", String.format("%s", playerPing), true);
        embed.addField("Tempo jogado:", String.format("%s horas.", df.format(timePlayed)), true)
                .addField("Visto por ultimo há:", String.format("%s", lastPlayed), true);
        embed.addField("Está banido?", String.format("%s", isBanned), false);
        embed.addField("UUID do jogador:", String.format("%s", uuid), false);

        return embed.build();
    }

    private String getStatus(OfflinePlayer player){

        if (player.isOnline()){

            return "Online";

        } else {

            return "Offline";

        }

    }

    private Double getTimePlayed(OfflinePlayer player){

        Double timePlayed;
        timePlayed = Double.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 72000);
        return timePlayed;

    }

    private String getLastPlayed(OfflinePlayer player){

        if (getStatus(player).equals("Offline")) {
            Double lastPlayed;
            DecimalFormat df = new DecimalFormat("0.0");
            lastPlayed = (Double.valueOf(Calendar.getInstance().getTimeInMillis() / 3600000)) - (player.getLastPlayed() / 3600000);
            return "" + df.format(lastPlayed) + " horas atrás.";
        } else {
            return "-";
        }
    }

    private String getPlayerName(OfflinePlayer player){

        return player.getName();

    }

    private String getDisplayName(OfflinePlayer player){

        if (getStatus(player).equals("Online")){

            return player.getPlayer().getDisplayName();

        } else {

            return "-";
        }

    }

    private String getPing(OfflinePlayer player){

        if (getStatus(player).equals("Online")){

            return "" + player.getPlayer().getPing() + " ms";

        } else {

            return "-";
        }

    }

    private String isBanned(OfflinePlayer player){

        if (player.isBanned()){
            return "Sim";
        } else {
            return "Não";
        }

    }

    private String getPlayerUuid(OfflinePlayer player){
        return player.getUniqueId().toString().trim();
    }
}
