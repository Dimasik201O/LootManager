package org.dimasik.lootmanager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.dimasik.lootmanager.backend.command.LootCommand;
import org.dimasik.lootmanager.backend.database.DatabaseManager;
import org.dimasik.lootmanager.backend.managers.ItemManager;
import org.dimasik.lootmanager.backend.models.NameData;
import org.dimasik.lootmanager.frontend.listeners.*;

public final class LootManager extends JavaPlugin {
    @Getter
    private static LootManager instance;
    private File itemsFile;
    @Getter
    private YamlConfiguration itemsConfig;
    @Getter
    public ItemManager itemManager;
    @Getter
    private DatabaseManager databaseManager;
    public static HashMap<Player, NameData> nameDatas = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        setupItemsFile();
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        this.itemManager = new ItemManager(this);
        this.databaseManager = new DatabaseManager(config.getString("mysql.host"), config.getString("mysql.user"), config.getString("mysql.password"), config.getString("mysql.database"));
        getCommand("lootmanager").setExecutor(new LootCommand(this));
        getServer().getPluginManager().registerEvents(new MainListener(), this);
        getServer().getPluginManager().registerEvents(new RemoveItemListener(), this);
        getServer().getPluginManager().registerEvents(new EditItemListener(), this);
        getServer().getPluginManager().registerEvents(new PropertiesListener(), this);
        getServer().getPluginManager().registerEvents(new RandomItemsListener(), this);
        getServer().getPluginManager().registerEvents(new ConfigsListener(), this);
        getServer().getPluginManager().registerEvents(new ConfigListener(), this);
        getServer().getPluginManager().registerEvents(new NameSelectListener(), this);
    }

    @Override
    public void onDisable() {
        saveItemsConfig();
    }

    private void setupItemsFile() {
        itemsFile = new File(getDataFolder(), "items.yml");
        if (!itemsFile.exists()) {
            itemsFile.getParentFile().mkdirs();
            saveResource("items.yml", false);
        }
        itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
    }

    public void saveItemsConfig() {
        try {
            itemsConfig.save(itemsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addItemInventory(Inventory inventory, ItemStack itemStack, Location location) {
        for(int id = 0; id < inventory.getStorageContents().length; ++id) {
            ItemStack item = inventory.getItem(id);
            if (item == null || item.getType().isAir()) {
                inventory.addItem(new ItemStack[]{itemStack});
                return;
            }

            if (item.isSimilar(itemStack)) {
                int count = item.getMaxStackSize() - item.getAmount();
                if (count > 0) {
                    if (itemStack.getAmount() <= count) {
                        inventory.addItem(new ItemStack[]{itemStack});
                        return;
                    }

                    ItemStack i = itemStack.clone();
                    i.setAmount(count);
                    inventory.addItem(new ItemStack[]{i});
                    itemStack.setAmount(itemStack.getAmount() - count);
                }
            }
        }

        Bukkit.getScheduler().runTask(instance, () -> {
            location.getWorld().dropItemNaturally(location, itemStack);
        });
    }
}