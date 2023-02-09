package com.prind.ctf.util;

import java.io.*;
import java.util.ArrayList;

public class SerializeUtil {

    public static String serializeArrayList(ArrayList<?> arrayList) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(arrayList);
        oos.close();

        return baos.toString("ISO-8859-1");
    }

    public static ArrayList<?> deserializeArrayList(String str) throws IOException, ClassNotFoundException {
        byte[] data = str.getBytes("ISO-8859-1");
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        ArrayList<?> newList = (ArrayList<?>) ois.readObject();
        ois.close();
        return newList;
    }
    
}