package org.dodopredo.minecord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.bukkit.OfflinePlayer;
import org.dodopredo.minecord.utils.OfflinePlayers;
import org.dodopredo.minecord.utils.StatusSorter;
import org.dodopredo.minecord.utils.interfaces.ICommand;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class StatusRank implements ICommand {

    @Override
    public String getCommandName() {
        return "top";
    }

    @Override
    public String getCommandDescription() {
        return "Mostra o rank dos status da categoria selecionada.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        List<OptionData> commandOptions = new ArrayList<>();

        commandOptions.add(new OptionData(OptionType.STRING, "categoria", "Qual a categoria desejada?", true).setAutoComplete(true));
        commandOptions.add(new OptionData(OptionType.INTEGER, "página", "Qual página da lista você quer?", false));

        return commandOptions;
    }

    @Override
    public List<String[]> getAutoComplete() {

        List<String[]> autoComplete = new ArrayList<>();
        autoComplete.add(new String[]{"categoria", "tempo de jogo"});

        return autoComplete;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        switch (event.getOption("categoria").getAsString().toLowerCase()){

            case "tempo de jogo":

                Integer pageSelected;
                if (event.getOption("página") != null) {
                    pageSelected = event.getOption("página").getAsInt();
                } else {
                    pageSelected = 1;
                }
                System.out.println("Paginas selecionadas: " + pageSelected);
                List<OfflinePlayer> playerList = StatusSorter.getByTimePlayed();
                System.out.println("Tamanho da playerList: " + playerList.size());
                Integer totalPages = Double.valueOf(Math.ceil(playerList.size() / 5 + 0.5)).intValue();
                System.out.println("Numero total de páginas: " + totalPages);
                OfflinePlayer[][] topRankPlayersList = new OfflinePlayer[totalPages][];
                OfflinePlayer[] playersInPage = new OfflinePlayer[5];
                Integer numberOfElementsInPage;

                System.out.println("Lista carregada:");
                for (OfflinePlayer player : playerList){
                    System.out.println(player.getName());
                }

                for (Integer x = 1; x <= totalPages; x++) {
                    numberOfElementsInPage = 0;

                    for (OfflinePlayer player : playerList) {
                        System.out.println("Posição na pagina: " + numberOfElementsInPage);
                        System.out.println("Jogador a ser adicionado nesta posicao: " + player.getName());
                        if (numberOfElementsInPage == 5) {
                            System.out.println("Fechando página com " + numberOfElementsInPage + "elementos");
                            System.out.println("Tamanho da array playersInPage: " + playersInPage.length);
                            break;
                        }

                        playersInPage[numberOfElementsInPage] = player;
                        numberOfElementsInPage++;
                    }
                    System.out.println("Jogadores na pagina " + x);
                    for (OfflinePlayer gamer : playersInPage) {
                        System.out.println(gamer.getName());
                    }
                    topRankPlayersList[x - 1] = playersInPage;
                }

                System.out.println("Jogadores Registrados:");
                Integer pagina = 1;
                for (OfflinePlayer[] jogadoresLista : topRankPlayersList){
                    System.out.println("Pagina: " + pagina);
                    for (OfflinePlayer jogador : jogadoresLista){
                        System.out.println(jogador.getName());
                    }
                    pagina++;
                }

                if (pageSelected > totalPages || pageSelected < 1){

                    pageSelected = 1;

                }

                String elements = "";
                Integer position = 1;
                Integer page = 1;
                for (OfflinePlayer[] offlinePlayers : topRankPlayersList){
                    System.out.println("Página atual: " + page);
                    System.out.println("Tamanho da array playersList: " + offlinePlayers.length);
                    if (page == pageSelected) {
                        System.out.println("Pagina selecionada: " + page + "/" + pageSelected);
                        for (OfflinePlayer player : offlinePlayers) {
                            elements += String.format("`%sº` - **%s** [%s hora(s) de jogo]\n", position, player.getName(), OfflinePlayers.getTimePlayed(player));
                        }
                    }
                    page++;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(String.format("Rank dos jogadores com mais tempo de jogo: (Página %s de %s)", pageSelected, totalPages));
                embed.setDescription(elements);
                embed.setThumbnail("https://cdn.discordapp.com/attachments/1115799046234321008/1126969666384969869/OIG_-_2023-04-26T014120.png");

                event.getHook().sendMessageEmbeds(embed.build()).queue();

                break;

            default:
                event.getHook().sendMessage("É necessário escolher uma categoria válida.").queue();
                break;

        }


    }


}
