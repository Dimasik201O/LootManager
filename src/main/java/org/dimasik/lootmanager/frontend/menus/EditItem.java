package org.dimasik.lootmanager.frontend.menus;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dimasik.lootmanager.backend.models.LootItem;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EditItem implements InventoryHolder {
    @Getter
    private LootItem lootItem;
    @Getter
    private Player viewer;
    @Getter
    private Main back;
    @Getter
    private Inventory inventory;

    public EditItem(LootItem sellItem, Main back){
        this.lootItem = sellItem.clone();
        this.back = back;
    }

    public EditItem compile(){
        try{
            inventory = Bukkit.createInventory(this, 27, Parser.color("Редактирование предмета"));
            if(true){
                ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color(" "));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(1, itemStack);
                inventory.setItem(7, itemStack);
                inventory.setItem(8, itemStack);
                inventory.setItem(9, itemStack);
                inventory.setItem(10, itemStack);
                inventory.setItem(16, itemStack);
                inventory.setItem(19, itemStack);
                inventory.setItem(25, itemStack);
                inventory.setItem(26, itemStack);
            }
            if(true){
                ItemStack itemStack = lootItem.getItem().clone();
                itemStack.setAmount(lootItem.getMaxAmount());
                inventory.setItem(0, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.SNOWBALL);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Шанс"));
                List<String> lore = new ArrayList<>();
                lore.add(Parser.color("&o&x&9&C&F&9&F&F ●&f Текущий шанс: &x&D&5&D&B&D&C" + lootItem.getChance()));
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ЛКМ, чтобы увеличить на 1"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ПКМ, чтобы уменьшить на 1"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ШИФТ + ЛКМ, чтобы увеличить на 10"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ШИФТ + ПКМ, чтобы уменьшить на 10"));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(3, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.CLOCK);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Максимальное количество"));
                List<String> lore = new ArrayList<>();
                lore.add(Parser.color("&o&x&9&C&F&9&F&F ●&f Текущее количество: &x&D&5&D&B&D&C" + lootItem.getMaxAmount()));
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ЛКМ, чтобы увеличить на 1"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ПКМ, чтобы уменьшить на 1"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ШИФТ + ЛКМ, чтобы увеличить на 10"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ШИФТ + ПКМ, чтобы уменьшить на 10"));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(4, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.PRISMARINE_SHARD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Уровень"));
                List<String> lore = new ArrayList<>();
                lore.add(Parser.color("&o&x&9&C&F&9&F&F ●&f Текущий уровень: &x&D&5&D&B&D&C" + lootItem.getLevel()));
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ЛКМ, чтобы увеличить на 1"));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ПКМ, чтобы уменьшить на 1"));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(5, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.STRUCTURE_VOID);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color(" "));
                List<String> lore = new ArrayList<>();
                lore.add(Parser.color(" &x&0&0&D&8&F&F● &x&D&5&D&B&D&CПоложите сюда предмет,"));
                lore.add(Parser.color(" &0.&x&D&5&D&B&D&C  чтобы его &x&0&0&D&8&F&Fзаменить&x&D&5&D&B&D&C."));
                lore.add(Parser.color(""));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(17, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.LIME_DYE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F⇵ &x&D&5&D&B&D&CПолучить предмет"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(18, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.ARROW);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&F&F&2&2&2&2◀ &x&D&5&D&B&D&CНазад"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(21, itemStack);
            }
            if(true){
                ItemStack itemStack = new ItemStack(Material.EMERALD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F⇵ &x&D&5&D&B&D&CСохранить предмет"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(23, itemStack);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public EditItem setPlayer(Player player){
        this.viewer = player;
        return this;
    }

    public void open(){
        viewer.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
