package com.system32.kitrenamer.Menus;

import com.system32.kitrenamer.KitRenamer;
import com.system32.kitrenamer.ReplacementData;
import com.system32.kitrenamer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReplacementsMenu {
    public static void openMainMenu(Player player, int page) {
        player.openInventory(createMainMenu(page));
    }

    private static Inventory createMainMenu(int page) {
        FileConfiguration config = KitRenamer.getInstance().getConfig();
        Inventory inventory = Bukkit.createInventory(null, 54, "Replacements Menu");
        List<Integer> slots = new ArrayList<>();
        for(int i = 0; i < 54; i++) {
            slots.add(i);
        }
        Utils.fillWithPanel(inventory, slots);

        List<ReplacementData> replacements = new ArrayList<>();
        config.getConfigurationSection("replacements").getKeys(false).forEach(replacement -> {
            replacements.add(new ReplacementData(replacement, config.getString("replacements."+replacement)));
        });

        int maxPerPage = 28;
        int maxPages = (int) Math.ceil(replacements.size() / (double) maxPerPage);

        if (page > maxPages) {
            page = maxPages;
        }
        if (page <= 0) {
            page = 1;
        }

        int replacementsAlreadyPassed = maxPerPage * (page - 1);
        int replacementsOnCurrentPage = Math.min(replacements.size(), Math.min(maxPerPage, replacements.size() - replacementsAlreadyPassed));
        List<Integer> validSlots = Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43);

        for (int i = 0; i < replacementsOnCurrentPage; i++) {
            try{
                ItemStack replace = new ItemStack(Material.NAME_TAG);
                ItemMeta replaceMeta = replace.getItemMeta();
                replaceMeta.setDisplayName(Utils.colorMessage("&c"+replacements.get(replacementsAlreadyPassed + i).getRegex()+ " &f» &a" + replacements.get(replacementsAlreadyPassed + i).getReplacement()));
                replaceMeta.setLore(Arrays.asList(Utils.colorMessage("&8• #a1ffbaLeft Click &fto edit this replacement"), Utils.colorMessage("&8• #ffa1a1Right click &fto delete this replacement")));
                replace.setItemMeta(replaceMeta);
                inventory.setItem(validSlots.get(i), replace);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }


        if(page == 1) inventory.setItem(45, back());
        if(page > 1) inventory.setItem(45, backward());
        if(maxPages > 1 && page != maxPages) inventory.setItem(53, forward());
        if (page < maxPages) inventory.setItem(53, forward());
        inventory.setItem(48, pageButton(page));
        inventory.setItem(50, addNew());

        return inventory;
    }

    public static ItemStack back(){
        return Utils.skullBuilder("&cBack to Main Menu", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRmNWMyZjg5M2JkM2Y4OWNhNDA3MDNkZWQzZTQyZGQwZmJkYmE2ZjY3NjhjODc4OWFmZGZmMWZhNzhiZjYifX19", "&8• &fReturn to the main menu");
    }
    public static ItemStack backward(){
        return Utils.skullBuilder("&cPrevious Page", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ5ZDI3MWM1ZGY4NGY4YTNjOGFhNWQxNTQyN2Y2MjgzOTM0MWRhYjUyYzYxOWE1OTg3ZDM4ZmJlMThlNDY0In19fQ==", "&8• &fGo to the previous page");
    }
    public static ItemStack forward(){
        return Utils.skullBuilder("&aNext Page", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdiNzMzZTBiYTk2NGU4MTU3NDc2ZjMzNTM0MjZhODdjZWFiM2RiYzNmYjRiZGRhYTJkNTc4ODZkZjM3YmE2In19fQ==", "&8• &fGo to the next page");
    }
    public static ItemStack addNew(){
        return Utils.skullBuilder("&eAdd new Replacement", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19", "&8• &fClick to add one replacement");
    }

    public static ItemStack pageButton(int page){
        return Utils.itemBuilder(Material.PAPER, "&cPage "+ page, "");
    }
}
