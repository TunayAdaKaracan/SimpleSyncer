package dev.kutuptilkisi.syncer.commands;

import dev.kutuptilkisi.syncer.CodeManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LinkCommandMinecraft implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            TextComponent text = new TextComponent(CodeManager.hasCode(p) ? "Your new link code is ready! " : "Your link code is ready! ");
            text.setColor(ChatColor.YELLOW.asBungee());

            TextComponent code = new TextComponent("[Copy Your Code!]");
            code.setColor(ChatColor.GREEN.asBungee());
            code.setBold(true);
            code.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, CodeManager.generateCode(10, p)));

            p.spigot().sendMessage(text, code);
        } else {
            sender.sendMessage(ChatColor.RED + "You cant use this command!");
        }
        return true;
    }
}
