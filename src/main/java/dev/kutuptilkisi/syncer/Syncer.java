package dev.kutuptilkisi.syncer;

import dev.kutuptilkisi.syncer.commands.LinkCommandDiscord;
import dev.kutuptilkisi.syncer.commands.LinkCommandMinecraft;
import dev.kutuptilkisi.syncer.commands.UnlinkCommand;
import dev.kutuptilkisi.syncer.foxic.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public final class Syncer extends JavaPlugin implements EventListener {

    private JDA jda;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        try {
            jda = JDABuilder.createDefault(getConfig().getString("token")).addEventListeners(this).build();
        } catch (LoginException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("link").setExecutor(new LinkCommandMinecraft());
    }

    
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof ReadyEvent){
            CommandHandler handler = new CommandHandler(jda);
            jda.addEventListener(handler);
            handler.registerCommand(new LinkCommandDiscord(this));
            handler.registerCommand(new UnlinkCommand(this));

            TextChannel channel = jda.getTextChannelById(getConfig().getString("channel-id"));
            channel.sendMessage("Server has started!").queue();
        }
    }
}
