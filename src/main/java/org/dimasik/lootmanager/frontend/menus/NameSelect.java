package org.dimasik.lootmanager.frontend.menus;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dimasik.lootmanager.LootManager;
import org.dimasik.lootmanager.backend.models.NameData;
import org.dimasik.lootmanager.backend.utils.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NameSelect implements InventoryHolder{
    public final Player player;
    private AnvilInventory anvilInventory;

    public NameSelect(Player player, String name, Configs back) {
        this.player = player;
        openAnvil();
        LootManager.nameDatas.put(player, new NameData(back, name, false));
    }

    private void openAnvil() {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        ContainerAnvil container = new CustomAnvilContainer(nmsPlayer);

        this.anvilInventory = (AnvilInventory) container.getBukkitView().getTopInventory();

        if(true) {
            ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(Parser.color("&f"));
            List<String> lore = new ArrayList<>();
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶&f Сбросить на начальное значение"));
            lore.add(Parser.color(""));
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            anvilInventory.setItem(0, itemStack);
        }
        if(true) {
            ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(" ");
            List<String> lore = new ArrayList<>();
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶&f Отменить ввод названия"));
            lore.add(Parser.color(""));
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            anvilInventory.setItem(1, itemStack);
        }
        if(true) {
            ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(" ");
            List<String> lore = new ArrayList<>();
            lore.add(Parser.color(" &x&0&0&D&8&F&F▶&f Утвердить название &x&0&5&F&B&0&0[ЛКМ]"));
            lore.add(Parser.color(""));
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            anvilInventory.setItem(2, itemStack);
        }

        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(
                container.windowId,
                Containers.ANVIL,
                new ChatMessage("Введите название")
        ));

        nmsPlayer.activeContainer = container;
        nmsPlayer.activeContainer.addSlotListener(nmsPlayer);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }

    private static class CustomAnvilContainer extends ContainerAnvil {
        public CustomAnvilContainer(EntityPlayer player) {
            super(player.nextContainerCounter(), player.inventory,
                    ContainerAccess.at(player.world, new BlockPosition(0, 0, 0)));
            this.checkReachable = false;
        }
    }
}
