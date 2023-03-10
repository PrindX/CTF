package com.prind.ctf.menus;

import com.prind.ctf.CTF;
import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.KitManager;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.stats.PlayerStats;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitShopMenu implements InventoryHolder {

    private Inventory inventory = Bukkit.createInventory(this, 3 * 9, "Kit Shop");
    private Player player;
    private PlayerStats playerStats;

    public KitShopMenu(Player player) {
        this.player = player;
        this.playerStats = CTF.getInstance().getStatsManager().get(player.getUniqueId());
        if (this.playerStats == null) return;
        fillInventory();
        player.openInventory(inventory);
    }

    private void fillInventory() {
        for (Kit kit : KitManager.getKits()) {
            if (kit.isFree()) continue;

            inventory.addItem(kit.getIcon());
        }
    }

    public void buyKit(ItemStack item) {
        Kit wantedKit;

        NBTItem nbtItem = new NBTItem(item);
        KitEnum kitEnum = nbtItem.getEnum("kit-id", KitEnum.class);

        wantedKit = KitManager.getKitByEnum(kitEnum);

        if (playerStats.getUnlockedKitsSet().contains(wantedKit)) {
            Component text = Component.text("You already have the " + wantedKit.getName()).color(NamedTextColor.RED);
            player.sendMessage(text);
            return;
        }

        if (playerStats.getCoins() >= wantedKit.getPrice()) {
            playerStats.addUnlockedKit(wantedKit);

            this.inventory.close();

            Component text = Component.text("You bought the " + wantedKit.getName() + " for " + wantedKit.getPrice() + " coins").color(NamedTextColor.GREEN);
            player.sendMessage(text);
        } else {
            Component text = Component.text("You need " + wantedKit.getPrice() + " more coins to by the " + wantedKit.getName()).color(NamedTextColor.RED);
            player.sendMessage(text);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
