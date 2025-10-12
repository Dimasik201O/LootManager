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
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.models.LootItem;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configs implements InventoryHolder {
    @Getter
    private Player viewer;
    @Getter
    private Main back;
    @Getter
    private Inventory inventory;
    @Getter
    @Setter
    private int page;
    @Getter
    private final HashMap<Integer, String> configs = new HashMap<>();

    public Configs(int page, Main back){
        this.page = page;
        this.back = back;
    }

    public Configs compile(){
        try{
            configs.clear();
            int slot = 0;
            int startIndex = 45 * (page - 1);
            List<String> configNames = LootManager.getInstance().getDatabaseManager().getConfigNames().get();
            int pages = configNames.size() / 45 + (configNames.size() % 45 == 0 ? 0 : 1);
            inventory = Bukkit.createInventory(this, 54, "Клауд конфиги (" + page + "/" + pages + ")");

            for(int i = startIndex; i < configNames.size() && slot < 45; i++) {
                String name = configNames.get(i);
                this.configs.put(slot, name);
                ItemStack itemStack = new ItemStack(Material.CHEST_MINECART);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&D&5&D&B&D&C● &x&0&0&D&8&F&F" + name));
                List<String> lore = new ArrayList<>();
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &x&0&0&D&8&F&F▶ &x&D&5&D&B&D&CНажмите ЛКМ, чтобы открыть"));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                itemStack.setAmount(i + 1);
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
                ItemStack itemStack = new ItemStack(Material.EMERALD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F⇵ &x&D&5&D&B&D&CОбновить предметы"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(47, itemStack);
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
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F Помощь по клауд конфигам"));
                List<String> lore = new ArrayList<>();
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &f&m                                      &f "));
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &x&9&C&F&9&F&F       Клауд-конфиги"));
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &x&D&5&D&B&D&C В этом разделе можно добавить,"));
                lore.add(Parser.color(" &x&D&5&D&B&D&C сохранить, удалить и загрузить"));
                lore.add(Parser.color(" &x&D&5&D&B&D&C конфигурации в базе данных."));
                lore.add(Parser.color(""));
                lore.add(Parser.color(" &f&m                                      &f "));
                lore.add(Parser.color(""));
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
                ItemStack itemStack = new ItemStack(Material.EGG);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Parser.color("&x&0&0&D&8&F&F⇵ &x&D&5&D&B&D&CСохранить конфигурацию"));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(53, itemStack);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Configs setPlayer(Player player){
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
