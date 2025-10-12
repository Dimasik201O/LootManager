package org.dimasik.lootmanager.backend.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHoverUtil {
    public static void sendHoverItemMessage(Player player, String message, ItemStack hoverItem) {
        player.sendMessage(message.replace("%item%", getItemDisplayName(hoverItem)));
    }

    public static String getHoverItemMessage(String message, ItemStack hoverItem) {
        return message.replace("%item%", getItemDisplayName(hoverItem));
    }

    private static String getItemDisplayName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return "[" + item.getType().toString().toUpperCase() + "]";
    }
}