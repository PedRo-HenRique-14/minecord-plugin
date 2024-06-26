package org.dodopredo.minecord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.dodopredo.minecord.bot.commands.*;
import org.dodopredo.minecord.plugin.commands.MedicalLog;
import org.dodopredo.minecord.utils.CommandManager;
import org.dodopredo.minecord.bot.listeners.MessageListener;
import org.dodopredo.minecord.plugin.listeners.ChatListener;
import org.dodopredo.minecord.plugin.listeners.PlayerJoinListener;

import java.awt.*;
import java.util.List;

public final class Minecord extends JavaPlugin {

    private static JDA jda;
    private static PluginManager pluginManager;
    private static Minecord plugin;
    public static CommandManager COMMAND_MANAGER;
    public static String PLUGIN_VERSION;

    public static long GUILD_ID;

    //Discord Roles
    public static long ADM_ROLE_ID;
    public static long BOSS_ROLE_ID;

    //Discord Text Channels
    public static long GLOBAL_CHANNEL_ID;
    public static long PLUGIN_LOG_CHANNEL_ID;
    public static long DISCORD_TO_MINECRAFT_CHANNEL_ID;
    public static long MEDICAL_LOG_CHANNEL_ID;

    //Discord Text Channels Enable
    public static Boolean GLOBAL_CHANNEL_ENABLE;
    public static Boolean PLUGIN_LOG_CHANNEL_ENABLE;
    public static Boolean DISCORD_TO_MINECRAFT_ENABLE;
    public static Boolean MEDICAL_LOG_ENABLE;

    public static List<Long> PROFESSIONS_ROLES;
    public static List<Long> WHITELISTED_PROFESSIONS_ROLE;

    private FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        config = getConfig();
        pluginManager = Bukkit.getPluginManager();
        loadDefaultConfig();
        String version = this.getDescription().getVersion();
        PLUGIN_VERSION = version;

        JDA jda = discordClientBuild();
        getGuildId();
        textChannelsSetup();
        getRolesId();

        //Discord Command Manager Setup
        COMMAND_MANAGER = new CommandManager();

        // Atualizar comandos
        slashCommandsSetup();
        COMMAND_MANAGER.updateSlashCommands(GUILD_ID);
        getCommand("registro-medico").setExecutor(new MedicalLog());

        // Registro de Eventos Discord
        try {
            jda.addEventListener(COMMAND_MANAGER);
            jda.addEventListener(new MessageListener());
        }catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage("Impossible to load the Event Listeners, because the bot token is invalid.");
        }

        // Registro de Eventos Spigot
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);

        sendReadyEmbed();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Minecord getPlugin() {
        return plugin;
    }

    private void slashCommandsSetup() {

        // Registro de comandos
        COMMAND_MANAGER.add(new ServerStatsInfo());
        COMMAND_MANAGER.add(new OnlinePlayerList());
        COMMAND_MANAGER.add(new PlayerInfo());
        COMMAND_MANAGER.add(new Ping());
        COMMAND_MANAGER.add(new Ajuda());
        COMMAND_MANAGER.add(new Version());
        COMMAND_MANAGER.add(new UserPromote());
        COMMAND_MANAGER.add(new UserDemote());
        //COMMAND_MANAGER.add(new StatusRank());
    }

    private JDA discordClientBuild(){

        try {
            jda = JDABuilder.createDefault(config.getString("BOT-TOKEN").trim()).setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing("Precisa de ajuda? Use /help."))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableCache(CacheFlag.ROLE_TAGS)
                    .build()
                    .awaitReady();
        } catch (InterruptedException  | InvalidTokenException e) {
            Bukkit.getConsoleSender().sendMessage("Bot token is invalid.");
        }
        return jda;
    }

    public static JDA getJda(){
        return jda;
    }

    private void loadDefaultConfig(){
        config.options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void getGuildId(){
        GUILD_ID = config.getLong("GUILD_ID");
        if (GUILD_ID == (long)0){
            Bukkit.getConsoleSender().sendMessage("Invalid server ID.");
        }
    }

    private void getRolesId(){

        PROFESSIONS_ROLES = config.getLongList("professions.professionsRolesIds");
        WHITELISTED_PROFESSIONS_ROLE = config.getLongList("professions.whitelistedRoles");
        ADM_ROLE_ID = config.getLong("ADM_ROLE_ID");
        BOSS_ROLE_ID = config.getLong("professions.bossRoleId");

    }

    private void textChannelsSetup(){
        // Busca os IDs dos canais de texto no config.yml, e armazena nas variáveis

        GLOBAL_CHANNEL_ID = config.getLong("textChannels.globalChannel.ID");
        PLUGIN_LOG_CHANNEL_ID = config.getLong("textChannels.pluginLogChannel.ID");
        DISCORD_TO_MINECRAFT_CHANNEL_ID = config.getLong("textChannels.discordToMinecraftChannel.ID");
        MEDICAL_LOG_CHANNEL_ID = config.getLong("textChannels.medicalLog.ID");
        GLOBAL_CHANNEL_ENABLE = config.getBoolean("textChannels.globalChannel.enable");
        PLUGIN_LOG_CHANNEL_ENABLE = config.getBoolean("textChannels.pluginLogChannel.enable");
        DISCORD_TO_MINECRAFT_ENABLE = config.getBoolean("textChannels.discordToMinecraftChannel.enable");
        MEDICAL_LOG_ENABLE = config.getBoolean("textChannels.medicalLog.enable");
    }

    private void sendReadyEmbed(){
        // Cria e envia uma embed de inicialização do plugin
        if (PLUGIN_LOG_CHANNEL_ENABLE) {
            TextChannel channel = jda.getTextChannelById(PLUGIN_LOG_CHANNEL_ID);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(255, 115, 0));
            embed.setTitle("Servidor Ligado!");
            channel.sendMessageEmbeds(embed.build()).queue();
        }
    }

    public static void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }

}
