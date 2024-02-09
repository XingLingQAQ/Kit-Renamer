package com.system32.kitrenamer.Menus;

import com.system32.kitrenamer.KitRenamer;
import com.system32.kitrenamer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu {

    public static void openMainMenu(Player player) {
        player.openInventory(createMainMenu());
    }

    private static Inventory createMainMenu() {
        Inventory inventory = Bukkit.createInventory(null, 45, "KitRenamer Menu");
        List<Integer> slots = new ArrayList<>();
        for(int i = 0; i < 45; i++) {
            slots.add(i);
        }
        Utils.fillWithPanel(inventory, slots);
        inventory.setItem(20, placeholder());

        inventory.setItem(22, format());

        inventory.setItem(24, replacements());

        inventory.setItem(36, reload());

        inventory.setItem(40, executeFormat());

        return inventory;
    }

    public static ItemStack placeholder(){
        return Utils.skullBuilder("&aPlaceholder", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg3ZmQyM2E3ODM2OWJkMzg3NWRhODg5NmYxNTBjNGFmOWYyMzM3NGUwNDhlMzA5MTM5MDBlM2ZkZDc3ODU5YSJ9fX0=", "&8• &fYou can add a #669966g#74a774r#83b683a#91c491d#a0d3a0i#aee1aee#bdf0bdn#ccffcct &for a simple placeholder text to your items here", "&8• &7Actual: &f"+ KitRenamer.getInstance().getConfig().getString("placeholder"), "&a→ Click to change the placeholder");
    }

    public static ItemStack format(){
        return Utils.itemBuilder(Material.BOOK ,"&bFormat", "&8• &fYou can modify the format of the rename here", "&8• &7Actual: &f"+ KitRenamer.getInstance().getConfig().getString("format"), "&a→ Click to change the format");
    }
    public static ItemStack replacements(){
        return Utils.itemBuilder(Material.NAME_TAG ,"&eReplacements", "&8• &fYou can modify the replacements here", "&a→ Click to modify the replacements");
    }

    public static ItemStack reload(){
        return Utils.skullBuilder("&4Reload", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UwNzIzNTVhZmE2MTQyYmFmNTY2MGM5MDY4N2M4YzUwZGM2M2U1Nzc4MWRkYmNhNWNlM2YzNTU0ZDFlMzc1ZSJ9fX0=","&8• &fYou can reload the config here, in case if you want to modify it manually", "&a→ Click to reload the config");
    }

    public static ItemStack executeFormat(){
        return Utils.skullBuilder("&aExecute the Format", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTc5YTVjOTVlZTE3YWJmZWY0NWM4ZGMyMjQxODk5NjQ5NDRkNTYwZjE5YTQ0ZjE5ZjhhNDZhZWYzZmVlNDc1NiJ9fX0=","&a→ If you are done click to rename your items!");
    }




}
