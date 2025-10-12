package org.dimasik.lootmanager.backend.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.models.LootItem;

import java.util.*;

public class ItemManager {
    private final LootManager plugin;
    private final YamlConfiguration itemsConfig;
    private final Map<String, LootItem> items = new HashMap<>();

    public ItemManager(LootManager plugin) {
        this.plugin = plugin;
        this.itemsConfig = plugin.getItemsConfig();
        loadItems();
    }

    public void clearItems() {
        items.clear();
    }

    public void loadItems() {
        for (String id : itemsConfig.getKeys(false)) {
            ItemStack item = itemsConfig.getItemStack(id + ".item");
            int maxAmount = itemsConfig.getInt(id + ".maxAmount");
            double chance = itemsConfig.getDouble(id + ".chance");
            int level = itemsConfig.getInt(id + ".level");
            String category = itemsConfig.getString(id + ".category", "Все подряд");
            items.put(id, new LootItem(id, item, maxAmount, chance, level, category));
        }
    }

    public String addItem(ItemStack item, double chance, int maxAmount, int level, String category) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        LootItem lootItem = new LootItem(id, item.clone(), maxAmount, chance, level, category);
        items.put(id, lootItem);
        saveItemToConfig(lootItem);
        return id;
    }

    public boolean removeItem(String id) {
        if (items.containsKey(id)) {
            items.remove(id);
            itemsConfig.set(id, null);
            plugin.saveItemsConfig();
            return true;
        }
        return false;
    }

    public boolean setItem(LootItem lootItem) {
        if (items.containsKey(lootItem.getId())) {
            items.put(lootItem.getId(), lootItem);
            saveItemToConfig(lootItem);
            return true;
        }
        items.put(lootItem.getId(), lootItem);
        saveItemToConfig(lootItem);
        return false;
    }

    public LootItem getItem(String id) {
        return items.get(id);
    }

    public Collection<LootItem> getAllItems() {
        return items.values();
    }

    public List<String> getAllCategories() {
        return items.values().stream()
                .map(LootItem::getCategory)
                .distinct()
                .toList();
    }

    public void saveItemToConfig(LootItem lootItem) {
        String id = lootItem.getId();
        itemsConfig.set(id + ".item", lootItem.getItem());
        itemsConfig.set(id + ".maxAmount", lootItem.getMaxAmount());
        itemsConfig.set(id + ".chance", lootItem.getChance());
        itemsConfig.set(id + ".level", lootItem.getLevel());
        itemsConfig.set(id + ".category", lootItem.getCategory());
        items.put(lootItem.getId(), lootItem);
        plugin.saveItemsConfig();
    }

    public void removeItemFromConfig(LootItem lootItem) {
        String id = lootItem.getId();
        itemsConfig.set(id, null);
        plugin.saveItemsConfig();
    }

    public List<ItemStack> getRandomItems(int count, int level) {
        if (items.size() < count) return new ArrayList<>();

        List<LootItem> eligibleItems = new ArrayList<>();
        double totalChance = 0;

        for (LootItem lootItem : items.values()) {
            double modifiedChance = calculateModifiedChance(lootItem, level);
            if (modifiedChance > 0) {
                eligibleItems.add(lootItem);
                totalChance += modifiedChance;
            }
        }

        if (eligibleItems.size() < count) return new ArrayList<>();

        List<ItemStack> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            double randomValue = random.nextDouble() * totalChance;
            double currentSum = 0;

            for (LootItem lootItem : eligibleItems) {
                currentSum += calculateModifiedChance(lootItem, level);
                if (randomValue <= currentSum) {
                    ItemStack item = lootItem.getItem();
                    int amount = lootItem.getMaxAmount() > 1 ?
                            random.nextInt(lootItem.getMaxAmount()) + 1 : 1;
                    item.setAmount(amount);
                    result.add(item);
                    break;
                }
            }
        }

        return result;
    }

    private double calculateModifiedChance(LootItem lootItem, int level) {
        double baseChance = lootItem.getChance();
        int itemLevel = lootItem.getLevel();

        if (itemLevel < level) {
            return baseChance;
        } else if (itemLevel > level) {
            return baseChance / (Math.abs(itemLevel - level) + 1);
        }
        return baseChance;
    }
}