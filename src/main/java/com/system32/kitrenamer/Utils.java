package com.system32.kitrenamer;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final String prefix = "#990000&lᴋ#a21212&lɪ#ab2525&lᴛ#b43737&lʀ#be4a4a&lᴇ#c75c5c&lɴ#d06f6f&lᴀ#d98181&lᴍ#e39494&lᴇ#eca6a6&lʀ#f5b9b9&l #ffcccc&l»&f ";
    public static String colorMessage(String message){
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);
        while(match.find()){
            String color = message.substring(match.start(), match.end());
            message = message.replace(color, ChatColor.of(color)+"");
            match = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', "&f"+message);
    }
    public static void playerMessage(Player player, String message){
        player.sendMessage(colorMessage(prefix+message));
    }
    public static ItemStack itemBuilder(Material material, String name, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(colorMessage(name));
        List<String> coloredLore = new ArrayList<>();
        coloredLore.add(colorMessage(" "));
        for (String s : lore) {
            coloredLore.add(colorMessage("&f"+s));
        }
        meta.setLore(coloredLore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack itemBuilder(Material material, String name, List<String> lore){
        return itemBuilder(material, name, lore.toArray(new String[0]));
    }

    public static ItemStack skullBuilder(String name, String texture, String... lore){
        ItemStack item = itemBuilder(Material.PLAYER_HEAD, name, lore);
        NBT.modify(item, nbt -> {
            final ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");
            skullOwnerCompound.setUUID("Id", UUID.randomUUID());
            skullOwnerCompound.getOrCreateCompound("Properties")
                    .getCompoundList("textures")
                    .addCompound()
                    .setString("Value", texture);
        });

        return item;
    }
    public static ItemStack skullBuilder(String name, Player owner, String... lore){
        ItemStack skull = itemBuilder(Material.PLAYER_HEAD, name, lore);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwnerProfile(owner.getPlayerProfile());
        skull.setItemMeta(meta);
        return skull;
    }
    public static ItemStack skullBuilder(String name, String texture, List<String> lore){
        return skullBuilder(name, texture, lore.toArray(new String[0]));
    }

    public static void fillWithPanel(Inventory inv, String type){
        if(type.equals("border")){
            int[] list = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 45, 35, 26, 17};
            for (int j : list) {
                inv.setItem(j, grayPane());
            }
        }
    }
    public static void fillWithPanel(Inventory inv, int[] list){
        for (int j : list) {
            inv.setItem(j, grayPane());
        }
    }

    public static void fillWithPanel(Inventory inv, List<Integer> list){
        for (int j : list) {
            inv.setItem(j, grayPane());
        }
    }

    public static ItemStack grayPane(){
        return itemBuilder(Material.GRAY_STAINED_GLASS_PANE, "&f ");
    }
}
