package org.dimasik.lootmanager.backend.models;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.dimasik.lootmanager.LootManager;

@Getter
@Setter
public class LootItem {
    private final String id;
    private ItemStack item;
    private int maxAmount;
    private double chance;
    private int level;
    private String category;

    public LootItem(String id, ItemStack item, int maxAmount, double chance, int level, String category) {
        this.id = id;
        this.item = item;
        this.maxAmount = maxAmount;
        this.chance = chance;
        this.level = level;
        this.category = category == null || category.isEmpty() ? "Все подряд" : category;
    }

    public void setCategory(String category) {
        this.category = category == null || category.isEmpty() ? "Все подряд" : category;
    }

    public static LootItem fromItemStack(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return null;

        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(LootManager.getInstance(), "loot_item_data");
        String data = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (data == null) return null;

        String[] parts = data.split(";");
        if (parts.length != 5) return null;

        ItemStack newItem = itemStack.clone();
        newItem.setAmount(1);

        return new LootItem(
                parts[0],
                newItem,
                Integer.parseInt(parts[1]),
                Double.parseDouble(parts[2]),
                Integer.parseInt(parts[3]),
                parts[4]
        );
    }

    public ItemStack getItem(){
        return this.item.clone();
    }

    public LootItem clone(){
        return new LootItem(id, item, maxAmount, chance, level, category);
    }
}