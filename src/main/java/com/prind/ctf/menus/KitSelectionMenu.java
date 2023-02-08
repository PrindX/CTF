package com.prind.ctf.menus;

import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitSelectionMenu implements InventoryHolder {

    private Inventory inventory = Bukkit.createInventory(this, 6 * 9, "Enchanted Workshop");
    private Player player;

    public KitSelectionMenu(Player player) {
        this.player = player;
        fillInventory();
        player.openInventory(inventory);
    }

    private void fillInventory() {
        for (Kit kit : KitManager.getKits()) {
            inventory.addItem(kit.getIcon());
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
