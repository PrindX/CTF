package com.prind.ctf.menus;

import com.prind.ctf.CTF;
import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.KitManager;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.stats.PlayerStats;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitSelectionMenu implements InventoryHolder {

    private Inventory inventory = Bukkit.createInventory(this, 3 * 9, "Kit Selection");
    private Player player;
    private PlayerStats playerStats;

    public KitSelectionMenu(Player player) {
        this.player = player;
        this.playerStats = CTF.getInstance().getStatsManager().get(player.getUniqueId());
        if (this.playerStats == null) return;
        fillInventory();
        player.openInventory(inventory);
    }

    private void fillInventory() {
        for (Kit kit : KitManager.getKits()) {
            if (kit.isFree())
                inventory.addItem(kit.getIcon());
        }
        for (Kit kit : playerStats.getUnlockedKitsSet()) {
            inventory.addItem(kit.getIcon());
        }
    }

    public void selectKit(ItemStack item) {
        Kit selectedKit;

        NBTItem nbtItem = new NBTItem(item);
        KitEnum kitEnum = nbtItem.getEnum("kit-id", KitEnum.class);

        selectedKit = KitManager.getKitByEnum(kitEnum);
        selectedKit.applyKit(player); // FOR TESTING DELETE LATER

        playerStats.setSelectedKit(selectedKit);

        this.inventory.close();

        player.sendMessage(Component.text(
                "You Selected The "
        ).color(TextColor.color(0, 255, 0)).append(Component.text(selectedKit.getName()).color(TextColor.color(110, 238, 255))));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
