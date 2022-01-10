package net.cybercake.rockschallenge;

import net.cybercake.cyberapi.Time;
import net.cybercake.cyberapi.chat.UChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Listeners implements Listener {

    public HashMap<String, Long> lastMsgSent = new HashMap<>();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!Main.whitelisted.contains(event.getBlock().getType())) return;

        event.setDropItems(false);

        event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), Main.rock);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if(!Main.getMainConfig().getBoolean("PreventPickingUpBlacklistedItems")) return;

        if(!(event.getEntity() instanceof Player player)) return;

        if(!Main.whitelisted.contains(event.getItem().getItemStack().getType())) return;

        sendDelayedMsg(player, "&cYou cannot pick up items that are normally rocks!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().getLore() == null || !event.getPlayer().getInventory().getItemInMainHand().getLore().get(0).contains("Put this rock in a")) return;

        sendDelayedMsg(event.getPlayer(), "&cYou cannot place a rock, try crafting with it instead!");
        event.setCancelled(true);
    }

    public void sendDelayedMsg(Player player, String msg) {
        if(lastMsgSent.get(player.getName()) != null && (Time.getUnix()-lastMsgSent.get(player.getName()) >= 3)) {
            player.sendMessage(UChat.component(msg));
            lastMsgSent.put(player.getName(), Time.getUnix());
        }
        if(lastMsgSent.get(player.getName()) == null) {
            lastMsgSent.put(player.getName(), Time.getUnix());
        }
    }

}
