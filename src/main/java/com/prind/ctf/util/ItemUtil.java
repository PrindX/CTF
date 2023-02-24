package com.prind.ctf.util;

import com.prind.ctf.kits.enums.ItemEnum;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    public static ItemStack makeCustom(ItemStack item, Enum<?> itemEnum) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setEnum("item-id", itemEnum);
        nbtItem.mergeCustomNBT(item);

        item = nbtItem.getItem();

        return item;
    }

    public static boolean isCustom(ItemStack item, Enum<?> inputEnum) {
        NBTItem nbtItem = new NBTItem(item);
        Enum<?> itemEnum = nbtItem.getEnum("item-id", inputEnum.getDeclaringClass());

        if (itemEnum == null) return false;
        if (itemEnum != inputEnum) return false;
        return true;
    }
}
