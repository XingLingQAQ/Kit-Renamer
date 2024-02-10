package com.system32.kitrenamer;

import com.system32.kitrenamer.Menus.MainMenu;
import com.system32.kitrenamer.Menus.ReplacementsMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.system32.kitrenamer.Utils.*;

public class MenusListener implements Listener {
    public HashMap<String, String> aSync = new HashMap<>();

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(event.getView().getTitle().equals("KitRenamer Menu")){
            event.setCancelled(true);

            if(item==null) return;
            if(item.equals(grayPane())) return;


            String icon = item.getItemMeta().getDisplayName();
            FileConfiguration config = KitRenamer.getInstance().getConfig();
            player.closeInventory();
            if(icon.equals(MainMenu.placeholder().getItemMeta().getDisplayName())){
                player.sendMessage(" ");
                playerMessage(player, "Please type the new value for the placeholder");
                aSync.put(player.getName(),"placeholder");
                player.sendMessage(" ");
            }
            if(icon.equals(MainMenu.format().getItemMeta().getDisplayName())){
                player.sendMessage(" ");
                playerMessage(player, "Please type the new value for the format");
                aSync.put(player.getName(),"format");
                player.sendMessage(" ");
            }
            if(icon.equals(MainMenu.replacements().getItemMeta().getDisplayName())){
                ReplacementsMenu.openMainMenu(player, 1);
            }
            if(icon.equals(MainMenu.reload().getItemMeta().getDisplayName())){
                playerMessage(player, "Config reloaded!");
                KitRenamer.getInstance().reloadConfig();
            }
            if(icon.equals(MainMenu.executeFormat().getItemMeta().getDisplayName())){
                for (ItemStack obj : player.getInventory()) {
                    if(obj == null) continue;
                    for (String replacements : config.getConfigurationSection("replacements").getKeys(false)) {
                        if(obj.getType().toString().toLowerCase().contains(replacements.toLowerCase())){
                            ItemMeta itemMeta = obj.getItemMeta();
                            itemMeta.setDisplayName(colorMessage(config.getString("format").replaceAll("%item%", config.getString("replacements." + replacements)).replaceAll("%placeholder%", config.getString("placeholder"))));
                            obj.setItemMeta(itemMeta);

                        }
                    }
                }
                playerMessage(player, "Items formatted!");
            }

        }
        if(event.getView().getTitle().equals("Replacements Menu")){
            event.setCancelled(true);

            if(item==null) return;
            if(item.equals(grayPane())) return;
            int page = Integer.parseInt(event.getInventory().getItem(48).getItemMeta().getDisplayName().split(" ")[1]);
            String icon = item.getItemMeta().getDisplayName();
            if(icon.equals(ReplacementsMenu.back().getItemMeta().getDisplayName())){
                MainMenu.openMainMenu(player);
            }

            if(icon.equals(ReplacementsMenu.backward().getItemMeta().getDisplayName())){
                ReplacementsMenu.openMainMenu(player, page-1);
            }

            if(icon.equals(ReplacementsMenu.forward().getItemMeta().getDisplayName())){
                ReplacementsMenu.openMainMenu(player, page+1);
            }
            if(icon.equals(ReplacementsMenu.addNew().getItemMeta().getDisplayName())){
                player.closeInventory();
                player.sendMessage(" ");
                playerMessage(player, "Please type the regex to search on the items ID");
                aSync.put(player.getName(),"regex:"+page);
                player.sendMessage(" ");
            }

            if(item.getType().equals(Material.NAME_TAG)){

                String[] parts = icon.split("§c", 2);
                String partAfterC = parts.length > 1 ? parts[1] : "";
                String[] parts2 = partAfterC.split(" §f", 2);
                String partBeforeF = parts2.length > 0 ? parts2[0] : "";
                if(event.getClick().isLeftClick()){
                    player.closeInventory();
                    player.sendMessage(" ");
                    playerMessage(player, "Please type the new replacement for the regex");
                    aSync.put(player.getName(),"replacement:"+partBeforeF+":"+page);
                    player.sendMessage(" ");
                }else if (event.getClick().isRightClick()){
                    KitRenamer.getInstance().getConfig().set("replacements."+partBeforeF, null);
                    KitRenamer.getInstance().saveConfig();
                    ReplacementsMenu.openMainMenu(player, page);
                }

            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        aSync.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void chatGet(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (aSync.containsKey(player.getName())) {
            KitRenamer kitRenamer = KitRenamer.getInstance();
            String action = aSync.get(player.getName());
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
            if(action.contains("regex")){
                aSync.put(player.getName(), "replacement:"+event.getMessage()+":"+action.split(":")[1]);
                player.sendMessage(" ");
                playerMessage(player, "Please type the replacement for the regex");
                player.sendMessage(" ");
                event.setCancelled(true);
                return;
            }
            if(action.contains("replacement")){
                aSync.remove(player.getName());
                Utils.playerMessage(player, "Setted the new replacement!");
                kitRenamer.getConfig().set("replacements."+action.split(":")[1], event.getMessage());
                kitRenamer.saveConfig();

                Future<Void> futureSetInv = kitRenamer.getServer().getScheduler().callSyncMethod(kitRenamer,
                        () -> {  ReplacementsMenu.openMainMenu(player, Integer.parseInt(action.split(":")[2]));; return null; });
                try { futureSetInv.get(); }
                catch (ExecutionException | InterruptedException ex) { ex.printStackTrace(); }
                event.setCancelled(true);
                return;
            }

            kitRenamer.getConfig().set(action, event.getMessage());
            kitRenamer.saveConfig();
            aSync.remove(player.getName());


            Future<Void> futureSetInv = kitRenamer.getServer().getScheduler().callSyncMethod(kitRenamer,
                    () -> { MainMenu.openMainMenu(player); return null; });
            try { futureSetInv.get(); }
            catch (ExecutionException | InterruptedException ex) { ex.printStackTrace(); }


        }
    }
}
