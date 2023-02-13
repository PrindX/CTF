package com.prind.ctf.kits;

import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class WarperKit implements Kit {
    @Override
    public ItemStack getIcon() {
        return ItemBuilder.getKitIcon(
                Material.IRON_SWORD,
                KitEnum.WARPER_KIT,
                getName(),
                "Get to the other side of the map in a blink"
        );
    }

    @Override
    public String getName() {
        return "Warper Kit";
    }

    @Override
    public KitEnum getKitEnum() {
        return KitEnum.WARPER_KIT;
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        inv.setArmorContents(getArmorContents());

        inv.setItem(0, ItemBuilder.getCustomItem(
                Material.DIAMOND_SWORD,
                ItemEnum.WARPER_SWORD,
                "Warper Sword",
                "A Good weapon to trick your enemies",
                "Blink",
                "Teleport 4 blocks forward where you are looking"
        ));
        ItemStack sword = new ItemStack(Material.IRON_AXE);
        ItemMeta metaSword = sword.getItemMeta();
        metaSword.setUnbreakable(true);
        sword.setItemMeta(metaSword);
        inv.setItem(1, sword);
        inv.setItem(2, new ItemStack(Material.GOLDEN_APPLE, 6));
    }

    private ItemStack[] getArmorContents() {
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);

        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);

        ItemMeta leggingsMeta = boots.getItemMeta();
        leggingsMeta.setUnbreakable(true);
        leggings.setItemMeta(leggingsMeta);

        ItemMeta chestplateMeta = boots.getItemMeta();
        chestplateMeta.setUnbreakable(true);
        chestplate.setItemMeta(chestplateMeta);

        ItemMeta helmetMeta = boots.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);

        return new ItemStack[] {
                boots,
                leggings,
                chestplate,
                helmet
        };
    }
}
