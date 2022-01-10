package net.cybercake.rockschallenge;

import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.chat.UTabComp;
import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 1) {
            sender.sendMessage(UChat.component("&c/rockschallenge [reloadconfig | api | status]")); return true;
        }else if(args.length == 1 && args[0].equalsIgnoreCase("reloadconfig")) {
            long ms = System.currentTimeMillis();
            Main.getPlugin(Main.class).saveDefaultConfig();
            Main.getPlugin(Main.class).reloadConfig();
            sender.sendMessage(UChat.component("&6You reloaded the configuration files in &a" + (System.currentTimeMillis() - ms) + "&ams&6!"));
            Spigot.playSound(sender, Sound.ENTITY_PLAYER_LEVELUP, 2F, 1F);
        }else if(args.length == 1 && args[0].equalsIgnoreCase("api")) {
            sender.sendMessage(UChat.component("&bThis plugin is using &dCyberAPI &bversion " + CyberAPI.getAPI().getVersion() + "&b!"));
            TextComponent component = new TextComponent(UChat.chat("&6You can click here to visit the GitHub page!"));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/CyberedCake/CyberAPI"));
            component.setHoverEvent(UChat.hover("&3&l> &bClick here to visit &eCyberAPI&b's GitHub page!"));
            sender.sendMessage(component);
        }else if(args.length == 1 && (args[0].equalsIgnoreCase("status") || args[0].equalsIgnoreCase("version"))) {
            sender.sendMessage(UChat.component("&bThe RocksChallenge was made by &dCyberedCake&b!"));
            sender.sendMessage(UChat.component("&fGitHub: &7https://github.com/CyberedCake"));
            sender.sendMessage(UChat.component("&fDiscord: &3CyberCake#9221"));
            sender.sendMessage(UChat.component("&fTwitter: &bhttps://twitter.com/trueCyberCake"));
            sender.sendMessage(UChat.component(" "));
            sender.sendMessage(UChat.component("&fPlugin Version: &a" + Main.getPlugin(Main.class).getDescription().getVersion()));
            sender.sendMessage(UChat.component("&6You can download a different version at &e" + Main.getPlugin(Main.class).getDescription().getWebsite()));
            sender.sendMessage(UChat.component(" "));
            sender.sendMessage(UChat.component("&bThanks for using the plugin!"));
        }else{
            sender.sendMessage(UChat.component("&c/rockschallenge [reloadconfig | api | status]"));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length < 2) {
            ArrayList<String> completions = new ArrayList<>();
            if(sender.hasPermission("rockschallenge.reloadconfig")) {
                completions.add("reloadconfig");
            }
            completions.add("status");
            completions.add("api");
            return UTabComp.tabCompletionsSearch(args[0], completions);
        }
        return UTabComp.emptyList;
    }
}
