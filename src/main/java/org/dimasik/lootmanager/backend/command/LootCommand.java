package org.dimasik.lootmanager.backend.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.dimasik.lootmanager.frontend.menus.Main;

import java.util.ArrayList;
import java.util.List;

public class LootCommand implements CommandExecutor, TabCompleter {
    private final LootManager plugin;

    public LootCommand(LootManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length == 0) {
            Player player = (Player) sender;
            new Main(1).setCurrentCategory("Все подряд").setCurrentFilterLevel(0).setPlayer(player)
                    .compileAsync().thenAccept(Main::openAsync);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "additem":
                handleAddItem(sender, command, args);
                break;
            case "cloud":
                handleCloudCommand(sender, args);
                break;
            default:
                sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Неизвестная команда"));
                break;
        }
        return true;
    }

    private void handleCloudCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Использование: &#00D4FB/" + "lootmanager cloud [upload|download|kill] [имя]"));
            return;
        }

        switch (args[1].toLowerCase()) {
            case "upload":
                uploadConfig(sender, args[2]);
                break;
            case "download":
                downloadConfig(sender, args[2]);
                break;
            case "kill":
                deleteConfig(sender, args[2]);
                break;
            default:
                sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Неизвестное действие. Используйте &#FF2222upload/download/kill"));
                break;
        }
    }

    private void uploadConfig(CommandSender sender, String name) {
        String configData = plugin.getItemsConfig().saveToString();

        plugin.getDatabaseManager().saveConfig(name, configData)
                .thenAccept(success -> {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        if (success) {
                            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7успешно загружена!"));
                        } else {
                            sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при загрузке конфигурации"));
                        }
                    });
                })
                .exceptionally(e -> {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при загрузке: &#FF2222" + e.getMessage()));
                    });
                    return null;
                });
    }

    private void downloadConfig(CommandSender sender, String name) {
        plugin.getDatabaseManager().getConfig(name)
                .thenAccept(configData -> {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        if (configData != null) {
                            try {
                                plugin.getItemManager().clearItems();
                                plugin.getItemsConfig().loadFromString(configData);
                                plugin.saveItemsConfig();
                                plugin.getItemManager().loadItems();
                                sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7успешно скачана!"));
                            } catch (org.bukkit.configuration.InvalidConfigurationException e) {
                                sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка формата конфигурации"));
                            }
                        } else {
                            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7не найдена."));
                        }
                    });
                })
                .exceptionally(e -> {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при скачивании: &#FF2222" + e.getMessage()));
                    });
                    return null;
                });
    }

    private void deleteConfig(CommandSender sender, String name) {
        plugin.getDatabaseManager().deleteConfig(name)
                .thenAccept(success -> {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        if (success) {
                            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Конфигурация &#00D4FB" + name + " &#E7E7E7успешно удалена!"));
                        } else {
                            sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Конфигурация &#FF2222" + name + " &#E7E7E7не найдена."));
                        }
                    });
                })
                .exceptionally(e -> {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Ошибка при удалении: &#FF2222" + e.getMessage()));
                    });
                    return null;
                });
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("additem");
            completions.add("cloud");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cloud")) {
            completions.add("upload");
            completions.add("download");
            completions.add("kill");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("cloud")) {
            try {
                List<String> configNames = plugin.getDatabaseManager().getConfigNames().get();
                completions.addAll(configNames);
            }
            catch (Exception e){
                // Tab completion is synchronous by design, just return empty if async fails
            }
        }
        return completions;
    }

    private void handleAddItem(CommandSender sender, Command command, String[] args) {
        if (args.length < 5) {
            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Использование: &#00D4FB/" + command.getName() + " additem [категория] [шанс] [макс.количество] [уровень]"));
            return;
        }
        Player player = (Player) sender;
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            sender.sendMessage(Parser.color("&#FF2222▶ &#E7E7E7Вы не можете добавить &#FF2222воздух."));
            return;
        }
        try {
            String category = args[1];
            double chance = Double.parseDouble(args[2]);
            int maxcount = Integer.parseInt(args[3]);
            int level = Integer.parseInt(args[4]);
            chance = Math.max(chance, 0);
            chance = Math.min(chance, 100);
            maxcount = Math.max(maxcount, 1);
            maxcount = Math.min(maxcount, 64);
            level = Math.max(level, 1);
            level = Math.min(level, 100);
            plugin.getItemManager().addItem(player.getItemInHand(), chance, maxcount, level, category);
            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Предмет &#00D4FBдобавлен."));
        } catch (NumberFormatException e) {
            sender.sendMessage(Parser.color("&#00D4FB▶ &#E7E7E7Использование: &#00D4FB/" + command.getName() + " additem [категория] [шанс] [макс.количество] [уровень]"));
        }
    }
}
