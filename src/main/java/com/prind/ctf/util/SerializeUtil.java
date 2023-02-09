package com.prind.ctf.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;

public class SerializeUtil {

    public static String serializeArrayList(ArrayList<?> arrayList) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(arrayList);
        oos.close();

        return baos.toString(StandardCharsets.ISO_8859_1);
    }

    public static ArrayList<?> deserializeArrayList(String str) throws IOException, ClassNotFoundException {
        byte[] data = str.getBytes(StandardCharsets.ISO_8859_1);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        ArrayList<?> newList = (ArrayList<?>) ois.readObject();
        ois.close();
        return newList;
    }

    public static String serializeSet(Set<?> set) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(set);
        oos.close();

        return baos.toString(StandardCharsets.ISO_8859_1);
    }

    public static Set<?> deserializeSet(String str) throws IOException, ClassNotFoundException {
        byte[] data = str.getBytes(StandardCharsets.ISO_8859_1);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Set<?> newList = (Set<?>) ois.readObject();
        ois.close();
        return newList;
    }

}
