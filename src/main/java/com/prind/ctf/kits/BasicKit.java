package com.prind.ctf.kits;

import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BasicKit implements Kit {
    @Override
    public ItemStack getIcon() {
        return ItemBuilder.getKitIcon(
                Material.DIAMOND_SWORD,
                KitEnum.BASIC_KIT,
                getName(),
                "Get a quick start in the arena"
        );
    }

    @Override
    public String getName() {
        return "Basic Kit";
    }

    @Override
    public KitEnum getKitEnum() {
        return KitEnum.BASIC_KIT;
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        inv.setArmorContents(new ItemStack[]
                {
                     new ItemStack(Material.IRON_BOOTS),
                     new ItemStack(Material.IRON_LEGGINGS),
                     new ItemStack(Material.IRON_CHESTPLATE),
                     new ItemStack(Material.IRON_HELMET)
                }
        );

        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        inv.setItem(1, new ItemStack(Material.BOW));
        inv.setItem(2, new ItemStack(Material.ARROW, 16));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 6));
    }
}
