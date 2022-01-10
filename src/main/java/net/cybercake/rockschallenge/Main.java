package net.cybercake.rockschallenge;

import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.instances.Spigot;
import net.cybercake.cyberapi.items.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public final class Main extends Spigot {

    public static ItemStack rock;
    public static ArrayList<Material> whitelisted = new ArrayList<>();

    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        CyberAPI.initSpigot(this);

        saveDefaultConfig();
        reloadConfig();

        for(String item : Main.getMainConfig().getStringList("ItemsToTurnIntoRocks")) {
            String key = (item.startsWith("minecraft:") ? item : "minecraft:" + item);

            if(!Item.mcItems().contains(key)) {
                Log.error("An invalid item was entered into the configuration file: " + key + " (raw=" + item + ")"); continue;
            }

            for(Material material : Material.values()) {
                if(material.getKey().getKey().equalsIgnoreCase(item.replace("minecraft:", ""))) {
                    whitelisted.add(material);
                    break;
                }
            }
        }
        Log.info(ChatColor.YELLOW + "Current items in material list: " + whitelisted.toString());

        ItemStack item = new ItemStack(Material.STONE_BUTTON, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(UChat.component("&fRock"));
        meta.lore(UChat.paginateComponents("&7Put this rock in a crafting table with 9 other rocks in order to craft some cobblestone!\n\n&c&lYou cannot place this item!"));
        item.setItemMeta(meta);
        rock = item;

        registerListener(new Listeners());

        registerCommand("rockschallenge", new Command());
        registerTabCompleter("rockschallenge", new Command());

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "rockstocobblestone"), new ItemStack(Material.COBBLESTONE, 1));
        recipe.shape("AAA", "AAA", "AAA");
        recipe.setIngredient('A', rock);
        Bukkit.addRecipe(recipe);

        Log.info(ChatColor.GREEN + "Successfully enabled Rocks Challenge [v" + getDescription().getVersion() + "] [by " + getDescription().getAuthors().get(0) + "]" + " in " + (System.currentTimeMillis()-ms) + "ms");
    }

    @Override
    public void onDisable() {
        long ms = System.currentTimeMillis();

        Log.info(ChatColor.RED + "Successfully disabled Rocks Challenge [v" + getDescription().getVersion() + "] [by " + getDescription().getAuthors().get(0) + "]" + " in " + (System.currentTimeMillis()-ms) + "ms");
    }


}
