package com.prind.ctf.kits;

import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.impl.StrikerKit;
import com.prind.ctf.kits.impl.WarperKit;
import com.prind.ctf.kits.impl.WizardKit;

import java.util.ArrayList;
import java.util.List;

public class KitManager {

    private static final ArrayList<Kit> kits = new ArrayList<>();

    static {
        kits.addAll(List.of(
                new StrikerKit(),
                new WizardKit(),
                new WarperKit()
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
