package org.dimasik.lootmanager;

import org.bukkit.inventory.ItemStack;
import org.dimasik.lootmanager.backend.managers.ItemManager;

import java.util.List;

public class LootAPI {
    private final ItemManager itemManager;

    public LootAPI() {
        this.itemManager = LootManager.getInstance().itemManager;
    }

    public List<ItemStack> getRandomItems(int count, int level) {
        return itemManager.getRandomItems(count, level);
    }
}