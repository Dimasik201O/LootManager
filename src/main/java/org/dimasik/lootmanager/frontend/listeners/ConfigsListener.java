package org.dimasik.lootmanager.frontend.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.dimasik.lootmanager.frontend.menus.*;

public class ConfigsListener implements Listener {
    @EventHandler
    public void on(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if (inventory.getHolder() instanceof Configs) {
            event.setCancelled(true);
            if (event.getClickedInventory() == null || event.getClickedInventory() != inventory) {
                return;
            }
            Configs configs = (Configs) inventory.getHolder();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if (slot < 45) {
                String config = configs.getConfigs().get(slot);
                if (config != null) {
                    new Config(config, configs).setPlayer(player).compile().open();
                }
            }
            else if (slot == 45) {
                Main main = configs.getBack();
                main.compileAsync().thenAccept(Main::openAsync);
            } else if (slot == 47) {
                player.sendMessage(Parser.color("&#00D4FB▶ &fКонфиги обновлены."));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_LAUNCH, 1f, 1f);

                int currentPage = configs.getPage();

                LootManager.getInstance().getDatabaseManager().getConfigNames()
                        .thenAccept(cfgs -> {
                            int pages = cfgs.size() / 45 + (cfgs.size() % 45 == 0 ? 0 : 1);
                            int newPage = Math.min(pages, currentPage);
                            int finalPage = Math.max(1, newPage);

                            new Configs(finalPage, configs.getBack()).setPlayer(player).compileAsync().thenAccept(Configs::openAsync);
                        });
            } else if (slot == 48) {
                int currentPage = configs.getPage() - 1;

                LootManager.getInstance().getDatabaseManager().getConfigNames()
                        .thenAccept(cfgs -> {
                            int pages = cfgs.size() / 45 + (cfgs.size() % 45 == 0 ? 0 : 1);
                            int newPage = Math.min(pages, currentPage);
                            int finalPage = Math.max(1, newPage);

                            if (finalPage != configs.getPage()) {
                                Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                    player.playSound(player.getLocation(), Sound.ENTITY_EGG_THROW, 1f, 1f);
                                });
                                new Configs(finalPage, configs.getBack()).setPlayer(player).compileAsync().thenAccept(Configs::openAsync);
                            }
                        });
            } else if (slot == 50) {
                int currentPage = configs.getPage() + 1;

                LootManager.getInstance().getDatabaseManager().getConfigNames()
                        .thenAccept(cfgs -> {
                            int pages = cfgs.size() / 45 + (cfgs.size() % 45 == 0 ? 0 : 1);
                            int newPage = Math.min(pages, currentPage);
                            int finalPage = Math.max(1, newPage);

                            if (finalPage != configs.getPage()) {
                                Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                    player.playSound(player.getLocation(), Sound.ENTITY_EGG_THROW, 1f, 1f);
                                });
                                new Configs(finalPage, configs.getBack()).setPlayer(player).compileAsync().thenAccept(Configs::openAsync);
                            }
                        });
            }
            else if(slot == 53){
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(LootManager.getInstance(), () -> {
                    new NameSelect(player, "", configs);
                }, 1);
            }
        }
    }
}
