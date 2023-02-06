package com.prind.ctf.kits;

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

}
