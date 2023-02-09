package com.prind.ctf.kits;

import com.prind.ctf.kits.enums.KitEnum;

import java.util.ArrayList;
import java.util.List;

public class KitManager {

    private static final ArrayList<Kit> kits = new ArrayList<>();

    static {
        kits.addAll(List.of(
                new BasicKit()
        ));
    }

    public static ArrayList<Kit> getKits() {
        return kits;
    }

    public static Kit getKitByEnum(KitEnum kitEnum) {
        for (Kit kit : kits) {
            if (kit.getKitEnum() == kitEnum)
                return kit;
        }
        return null;
    }

}
