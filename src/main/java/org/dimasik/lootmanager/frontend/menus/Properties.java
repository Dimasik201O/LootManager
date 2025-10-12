package org.dimasik.lootmanager.frontend.menus;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.models.LootItem;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Properties implements InventoryHolder {

    private Inventory inventory;
    @Getter
    private Player viewer;
    @Setter
    @Getter
    private int items;
    @Setter
    @Getter
    private int level;
    @Getter
    private Main back;

    public Properties(int items, int level, Main back){
        this.items = items;
        this.level = level;
        this.back = back;
    }

    public Properties compile(){
        inventory = Bukkit.createInventory(this, InventoryType.HOPPER, Parser.color("&0Выбор настроек"));
        if(true){
            ItemStack itemStack = new ItemStack(Material.CLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Количество предметов"));
            List<String> lore = new ArrayList<>();
            lore.add(Parser.color("&o&x&9&C&F&9&F&F ●&f Текущее количество: &x&D&5&D&B&D&C" + items));
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ЛКМ, чтобы увеличить на 1"));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ПКМ, чтобы уменьшить на 1"));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ШИФТ + ЛКМ, чтобы увеличить на 16"));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ШИФТ + ПКМ, чтобы уменьшить на 16"));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(0, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.PRISMARINE_SHARD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Уровень"));
            List<String> lore = new ArrayList<>();
            lore.add(Parser.color("&o&x&9&C&F&9&F&F ●&f Текущий уровень: &x&D&5&D&B&D&C" + level));
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ЛКМ, чтобы увеличить на 1"));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ПКМ, чтобы уменьшить на 1"));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(1, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.EMERALD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F⇵ &x&D&5&D&B&D&CСгенерировать предметы"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(3, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.ARROW);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&F&F&2&2&2&2◀ &x&D&5&D&B&D&CНазад"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(4, itemStack);
        }
        return this;
    }

    public Properties setPlayer(Player player){
        this.viewer = player;
        return this;
    }

    public void open(){
        viewer.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
