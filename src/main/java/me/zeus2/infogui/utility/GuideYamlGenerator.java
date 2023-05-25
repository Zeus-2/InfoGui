package me.zeus2.infogui.utility;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GuideYamlGenerator {

    public static void generateGuideYaml(JavaPlugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "guide.yml");

        // Check if the guide.yml file already exists
        if (configFile.exists()) {
            return; // File already exists, no need to generate
        }

        // Create the data folder if it doesn't exist
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // Copy the default guide.yml from resources to the data folder
        try (InputStream inputStream = plugin.getResource("guide.yml");
             OutputStream outputStream = new FileOutputStream(configFile)) {
            if (inputStream == null) {
                throw new IOException("Failed to get resource: guide.yml");
            }
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to generate guide.yml: " + e.getMessage());
        }
    }
}
