package org.dimasik.lootmanager.backend.utils;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetSlot;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class PacketSender {
    public static void sendAnvilSlotUpdate(Player player, int slot, org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(
                nmsPlayer.activeContainer.windowId,
                slot,
                nmsItem
        );

        nmsPlayer.playerConnection.sendPacket(packet);
    }
}