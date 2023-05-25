package me.zeus2.infogui;

import me.zeus2.infogui.Commands.ServerInfo;
import me.zeus2.infogui.utility.GUIClickListener;
import me.zeus2.infogui.utility.GuideYamlGenerator;
import me.zeus2.infogui.utility.RulesYamlGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class InfoGui extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register the ServerInfo command executor
        getCommand("ServerInfo").setExecutor(new ServerInfo());

        // Generate the rules.yml if it doesn't exist
        RulesYamlGenerator.generateRulesYaml(this);

        // Generate the guide.yml if it doesn't exist
        GuideYamlGenerator.generateGuideYaml(this);

        // Register the event listener
        getServer().getPluginManager().registerEvents(new GUIClickListener(), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
