package com.prind.ctf.menus;

import com.prind.ctf.CTF;
import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.KitManager;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.stats.PlayerStats;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitShopMenu implements InventoryHolder {

    private Inventory inventory = Bukkit.createInventory(this, 3 * 9, "Kit Selection");
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
            inventory.addItem(kit.getIcon());
        }
    }

    public void buyKit(ItemStack item) {
        Kit wantedKit;

        NBTItem nbtItem = new NBTItem(item);
        KitEnum kitEnum = nbtItem.getEnum("kit-id", KitEnum.class);

        wantedKit = KitManager.getKitByEnum(kitEnum);

        if (playerStats.getUnlockedKitsSet().contains(wantedKit)) {
            // Already Has This Kit
        }

        if (playerStats.getCoins() >= wantedKit.getPrice()) {
            playerStats.addUnlockedKit(wantedKit);

            this.inventory.close();

            MiniMessage mm = MiniMessage.miniMessage();
            Component text = mm.deserialize("<green>You bought the <yellow><kit></yellow> for <yellow><amount></yellow> coins</green>",
                    Placeholder.parsed("amount", String.valueOf(wantedKit.getPrice())),
                    Placeholder.parsed("kit", wantedKit.getName()));
            player.sendMessage(text);
        } else {
            MiniMessage mm = MiniMessage.miniMessage();
            Component text = mm.deserialize("<red>You need <green><amount></green> more coins to buy the <yellow><kit></yellow></red>",
                    Placeholder.parsed("amount", String.valueOf(wantedKit.getPrice())),
                    Placeholder.parsed("kit", wantedKit.getName()));
            player.sendMessage(text);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
