package com.system32.kitrenamer;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
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
        Metrics metrics = new Metrics(this, 20952);
        Bukkit.getConsoleSender().sendMessage(Utils.colorMessage("&aPlugin started correctly"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Utils.colorMessage("&cPlugin turned off correctly"));
    }

    public static KitRenamer getInstance() {
        return instance;
    }
}
