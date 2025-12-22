package org.dimasik.lootmanager.frontend.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.dimasik.lootmanager.frontend.menus.Config;
import org.dimasik.lootmanager.frontend.menus.Configs;

public class ConfigListener implements Listener {
    @EventHandler
    public void on(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if (inventory.getHolder() instanceof Config) {
            event.setCancelled(true);
            if (event.getClickedInventory() == null || event.getClickedInventory() != inventory) {
                return;
            }
            Config config = (Config) inventory.getHolder();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if(slot == 0){
                String name = config.getConfigName();
                String configData = LootManager.getInstance().getItemsConfig().saveToString();
                LootManager.getInstance().getDatabaseManager().saveConfig(name, configData)
                        .thenAccept(success -> {
                            Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                if (success) {
                                    player.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7успешно загружена!"));
                                } else {
                                    player.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при загрузке конфигурации"));
                                }
                            });
                        })
                        .exceptionally(e -> {
                            Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                player.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при загрузке: &#FF2222" + e.getMessage()));
                            });
                            return null;
                        });
            }
            else if(slot == 1){
                String name = config.getConfigName();
                LootManager.getInstance().getDatabaseManager().getConfig(name)
                        .thenAccept(configData -> {
                            Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                if (configData != null) {
                                    try {
                                        LootManager.getInstance().getItemManager().clearItems();
                                        LootManager.getInstance().getItemsConfig().loadFromString(configData);
                                        LootManager.getInstance().saveItemsConfig();
                                        LootManager.getInstance().getItemManager().loadItems();
                                        player.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7успешно скачана!"));
                                    } catch (org.bukkit.configuration.InvalidConfigurationException e) {
                                        player.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка формата конфигурации"));
                                    }
                                } else {
                                    player.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7не найдена."));
                                }
                                Configs configs = config.getBack();
                                configs.compileAsync().thenAccept(Configs::openAsync);
                            });
                        })
                        .exceptionally(e -> {
                            Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                player.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при скачивании: &#FF2222" + e.getMessage()));
                                Configs configs = config.getBack();
                                configs.compileAsync().thenAccept(Configs::openAsync);
                            });
                            return null;
                        });
            }
            else if(slot == 2){
                String name = config.getConfigName();
                LootManager.getInstance().getDatabaseManager().deleteConfig(name)
                        .thenAccept(success -> {
                            Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                if (success) {
                                    player.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7успешно удалена!"));
                                } else {
                                    player.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Конфигурация &#FF2222" + name + " &#E7E7E7не найдена."));
                                }
                                Configs configs = config.getBack();
                                configs.compileAsync().thenAccept(Configs::openAsync);
                            });
                        })
                        .exceptionally(e -> {
                            Bukkit.getScheduler().runTask(LootManager.getInstance(), () -> {
                                player.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при удалении: &#FF2222" + e.getMessage()));
                                Configs configs = config.getBack();
                                configs.compileAsync().thenAccept(Configs::openAsync);
                            });
                            return null;
                        });
            }
            else if(slot == 7){
                Configs configs = config.getBack();
                configs.compileAsync().thenAccept(Configs::openAsync);
            }
        }
    }
}
