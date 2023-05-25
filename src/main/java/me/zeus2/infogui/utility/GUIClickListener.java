package me.zeus2.infogui.utility;

import me.zeus2.infogui.Commands.ServerInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the clicked inventory is the GUI
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + (ChatColor.BOLD +"Server Info"))) {
            // Handle the click event here
            ItemStack clickedItem = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (clickedItem != null) {
                ServerInfo serverInfo = new ServerInfo();
                serverInfo.handleClick(clickedItem, player);
            }
            event.setCancelled(true);
        }
    }
}
