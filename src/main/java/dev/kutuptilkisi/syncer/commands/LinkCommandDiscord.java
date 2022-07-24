package dev.kutuptilkisi.syncer.commands;

import dev.kutuptilkisi.syncer.CodeManager;
import dev.kutuptilkisi.syncer.Syncer;
import dev.kutuptilkisi.syncer.foxic.annotations.Executor;
import dev.kutuptilkisi.syncer.foxic.annotations.Option;
import dev.kutuptilkisi.syncer.foxic.annotations.SlashCommand;
import dev.kutuptilkisi.syncer.foxic.data.Response;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.CompletableFuture;

@SlashCommand(label = "link", description = "Links your discord and minecraft accounts")
public class LinkCommandDiscord {

    private final Syncer main;

    public LinkCommandDiscord(Syncer main){
        this.main = main;
    }

    @Executor
    public void callback(Response e, @Option(name = "code", description = "Your linking code", required = true) Response linkCode){
        String code = linkCode.getString();

        if(main.getConfig().contains("verified."+e.getEvent().getMember().getId())){
            e.getEvent().reply("Your account is already linked with a minecraft account").setEphemeral(true).queue();
            return;
        }

        if(CodeManager.isValid(code)){
            OfflinePlayer player = Bukkit.getOfflinePlayer(CodeManager.getPlayer(code));
            e.getEvent().reply("Your account linked with `"+player.getName()+"`").setEphemeral(true).queue();
            main.getConfig().set("verified."+e.getEvent().getMember().getId(), CodeManager.getPlayer(code).toString());
            CompletableFuture.runAsync(main::saveConfig);
            CodeManager.deleteCode(code);
        } else {
            e.getEvent().reply("This code is not a valid linking code").setEphemeral(true).queue();
        }
    }
}
