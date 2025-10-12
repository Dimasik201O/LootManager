package org.dimasik.lootmanager.frontend.menus;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config implements InventoryHolder {
    @Getter
    private Player viewer;
    @Getter
    private Configs back;
    @Getter
    private Inventory inventory;
    @Getter
    private String configName;

    public Config(String configName, Configs back){
        this.configName = configName;
        this.back = back;
    }

    public Config compile(){
        try{
            inventory = Bukkit.createInventory(this, InventoryType.DISPENSER, "Действия с конфигурацией");

            if(true){
                ItemStack itemStack = new ItemStack(Material.GRAY_DYE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&2&2&F&F&2&2↑ &x&D&5&D&B&D&CПерезаписать конфигурацию"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(0, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.LIME_DYE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F↓ &x&D&5&D&B&D&CЗагрузить конфигурацию"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(1, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.BROWN_DYE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&F&F&2&2&2&2✖ &x&D&5&D&B&D&CУдалить"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(2, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.ARROW);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&F&F&2&2&2&2◀ &x&D&5&D&B&D&CНазад"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(7, itemStack);
            }
            viewer.openInventory(inventory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Config setPlayer(Player player){
        this.viewer = player;
        return this;
    }

    public void open(){
        viewer.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
