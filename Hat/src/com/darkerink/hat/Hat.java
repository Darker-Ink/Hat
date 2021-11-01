package com.darkerink.hat;

import com.darkerink.hat.events.HatEvents;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Hat extends JavaPlugin {
    private static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new HatEvents(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Enabled! Version 1.2.1");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Disabling...");
    }
    public static Plugin getInstance() {
        return instance;
    }
}
