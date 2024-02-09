package com.system32.kitrenamer;

import org.bukkit.plugin.java.JavaPlugin;

public final class KitRenamer extends JavaPlugin {
    private static KitRenamer instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginCommand("kitrenamer").setExecutor(new MainCommand());
        instance = this;
        getServer().getPluginManager().registerEvents(new MenusListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static KitRenamer getInstance() {
        return instance;
    }
}
