package dev.kutuptilkisi.syncer.commands;

import dev.kutuptilkisi.syncer.Syncer;
import dev.kutuptilkisi.syncer.foxic.annotations.Executor;
import dev.kutuptilkisi.syncer.foxic.annotations.SlashCommand;
import dev.kutuptilkisi.syncer.foxic.data.Response;

import java.util.concurrent.CompletableFuture;

@SlashCommand(label = "unlink", description = "Unlink your minecraft account")
public class UnlinkCommand {
    private final Syncer main;

    public UnlinkCommand(Syncer main){
        this.main = main;
    }

    @Executor
    public void callback(Response e){
        if(!main.getConfig().contains("verified."+e.getEvent().getMember().getId())){
            e.getEvent().reply("Your account is not linked with any minecraft account").setEphemeral(true).queue();
            return;
        }
        e.getEvent().reply("Your account is unlinked with your minecraft account").setEphemeral(true).queue();
        main.getConfig().set("verified."+e.getEvent().getMember().getId(), null);
        CompletableFuture.runAsync(main::saveConfig);
    }

}
