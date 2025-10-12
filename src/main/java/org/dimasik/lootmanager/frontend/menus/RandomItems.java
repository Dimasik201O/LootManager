package org.dimasik.lootmanager.frontend.menus;

import lombok.Getter;
import lombok.Setter;
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

public class RandomItems implements InventoryHolder {
    @Getter
    private LootItem lootItem;
    @Getter
    private Player viewer;
    @Getter
    @Setter
    private int page;
    @Getter
    private Main back;
    @Getter
    private Inventory inventory;
    @Getter
    private final List<ItemStack> items;
    @Setter
    @Getter
    private int iitems;
    @Setter
    @Getter
    private int level;

    public RandomItems(int page, List<ItemStack> items, Main back){
        this.page = page;
        this.items = items;
        this.back = back;
    }

    public RandomItems compile(){
        int slot = 0;
        int startIndex = 45 * (page - 1);
        int pages = items.size() / 45 + (items.size() % 45 == 0 ? 0 : 1);
        inventory = Bukkit.createInventory(this, 54, Parser.color("&0Сгенерированные предметы (" + page + "/" + pages + ")"));
        for(int i = startIndex; i < items.size() && slot < 45; i++) {
            ItemStack itemStack = items.get(i);
            inventory.setItem(slot, itemStack);
            slot++;
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.ARROW);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&F&F&2&2&2&2◀ &x&D&5&D&B&D&CНазад"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(45, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F◀ Предыдущая страница"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(48, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Помощь по рандомным предметам"));
            List<String> lore = new ArrayList<>();
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &f&m                                      &f "));
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &x&9&C&F&9&F&F       Рандомные предметы"));
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &x&D&5&D&B&D&C Здесь находятся предметы,"));
            lore.add(Parser.color(" &x&D&5&D&B&D&C сгенерированные по вашем"));
            lore.add(Parser.color(" &x&D&5&D&B&D&C настройкам в прошлом меню."));
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &f&m                                      &f "));
            lore.add(Parser.color(""));
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите, чтобы вернуться к &x&0&0&D&8&F&Fнастройкам"));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(49, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.LIME_DYE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&6Следующая страница ▶"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(50, itemStack);
        }
        if(true){
            ItemStack itemStack = new ItemStack(Material.EMERALD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F⇵ &x&D&5&D&B&D&CСгенерировать еще раз"));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(53, itemStack);
        }
        return this;
    }

    public RandomItems setPlayer(Player player){
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
