package me.zeus2.infogui.Commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has permission to view other players' info
        boolean hasOtherPermission = player.hasPermission("InfoGui.ServerInfo.other");

        if (args.length > 0 && hasOtherPermission) {
            // Player specified another user, open GUI for that user
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null) {
                openServerInfoGUI(player, targetPlayer);
            } else {
                player.sendMessage(ChatColor.RED + "The specified player is not online.");
            }
        } else {
            // No other user specified, open GUI for the player
            openServerInfoGUI(player, player);
        }

        return true;
    }

    private void openServerInfoGUI(Player viewer, Player targetPlayer) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + (ChatColor.BOLD +"Server Info"));

        // Create the items
        ItemStack rulesItem = createItemStack(Material.BOOK, ChatColor.GREEN + "Rules");
        ItemStack discordItem = createItemStack(Material.PAPER, ChatColor.YELLOW + "Discord Link");
        ItemStack guideItem = createItemStack(Material.BOOK, ChatColor.BLUE + "Player guide");
        ItemStack supportServerItem = createItemStack(Material.GOLD_INGOT, ChatColor.RED + "How to support the server");

        // Set the lore for the "Rules" item
        ItemMeta rulesMeta = rulesItem.getItemMeta();
        List<String> rulesLore = new ArrayList<>();
        rulesLore.add(ChatColor.GRAY + "Click to receive the server rules book.");
        rulesMeta.setLore(rulesLore);
        rulesItem.setItemMeta(rulesMeta);

        // Set the lore for the "Discord Link" item
        ItemMeta discordMeta = discordItem.getItemMeta();
        List<String> discordLore = new ArrayList<>();
        discordLore.add(ChatColor.GRAY + "Click to join our Discord server!");
        discordMeta.setLore(discordLore);
        discordItem.setItemMeta(discordMeta);

        // Set the lore for the "Player guide" item
        ItemMeta guideMeta = guideItem.getItemMeta();
        List<String> guideLore = new ArrayList<>();
        guideLore.add(ChatColor.GRAY + "Click to receive the Player guide book.");
        guideMeta.setLore(guideLore);
        guideItem.setItemMeta(guideMeta);

        // Set the lore for the "How to support the server" item
        ItemMeta supportMeta = supportServerItem.getItemMeta();
        List<String> supportLore = new ArrayList<>();
        supportLore.add(ChatColor.GRAY + "Click to visit our online store.");
        supportMeta.setLore(supportLore);
        supportServerItem.setItemMeta(supportMeta);

        // Calculate the starting position for the center items
        int centerIndex = 13;
        int offset = 2;
        int start = centerIndex - offset;
        int end = centerIndex + offset;

        // Fill the GUI with light gray glass panes
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, createItemStack(Material.GRAY_STAINED_GLASS_PANE, ""));
        }

        // Set the center items in the GUI
        gui.setItem(centerIndex + 3, rulesItem);
        gui.setItem(centerIndex + 1, discordItem);
        gui.setItem(centerIndex - 1, guideItem);
        gui.setItem(centerIndex - 3, supportServerItem);

        // Open the GUI for the viewer
        viewer.openInventory(gui);
    }

    private ItemStack createItemStack(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // Helper method to create a Book and Quill with the specified title and pages
    private ItemStack createBook(String title, List<String> pages) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle(ChatColor.GREEN + "" + ChatColor.BOLD + title);
        meta.setAuthor("Zeus2");
        meta.setPages(pages);
        book.setItemMeta(meta);
        return book;
    }

    // Helper method to handle when a player clicks on an item in the GUI
    public void handleClick(ItemStack item, Player player) {
        if (item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Rules")) {
            List<String> pages = new ArrayList<>();

            // Load the pages of the book from the rules.yml file
            // You can customize this code to load the pages from a different file or location
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/infogui/rules.yml"));
            for (String page : config.getStringList("pages")) {
                pages.add(ChatColor.translateAlternateColorCodes('&', page));
            }
            // Create the Book and Quill with the loaded pages and give it to the player
            ItemStack book = createBook("Server Rules", pages);
            player.getInventory().addItem(book);
            player.sendMessage(ChatColor.GREEN + "You have received the server rules book.");
            player.closeInventory();
        } else if (item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Discord Link")) {
            player.sendMessage(ChatColor.GREEN + "Link: https://discord.gg/3HKdGvNaqR");
            player.closeInventory();
        } else if (item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Player guide")) {
            List<String> pages = new ArrayList<>();

            // Load the pages of the book from the guide.yml file
            // You can customize this code to load the pages from a different file or location
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/infogui/guide.yml"));
            for (String page : config.getStringList("pages")) {
                pages.add(ChatColor.translateAlternateColorCodes('&', page));
            }
            // Create the Book and Quill with the loaded pages and give it to the player
            ItemStack book = createBook("Player Guide", pages);
            player.getInventory().addItem(book);
            player.sendMessage(ChatColor.GREEN + "You have received the Player guide book.");
            player.closeInventory();
        } else if (item.getType() == Material.GOLD_INGOT && item.getItemMeta().getDisplayName().equals(ChatColor.RED + "How to support the server")) {
            player.sendMessage(ChatColor.GREEN + "link: https://jaystechvault.craftingstore.net/category/234313");
            player.closeInventory();
        }
    }
}
