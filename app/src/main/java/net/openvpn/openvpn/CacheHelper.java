package net.openvpn.openvpn;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CacheHelper {

    private static final String TAG = "CacheHelper";

    public static <T> void saveToCache(Context context, String fileName, ArrayList<T> data) {
        try {
            File cacheFile = new File(context.getCacheDir(), fileName);
            FileOutputStream fos = new FileOutputStream(cacheFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (java.io.IOException e) {
            Log.e(TAG, "Error saving to cache: " + fileName, e);
        }
    }

    public static <T> ArrayList<T> readFromCache(Context context, String fileName) {
        File cacheFile = new File(context.getCacheDir(), fileName);
        if (!cacheFile.exists()) {
            return null;
        }

        try (FileInputStream fis = new FileInputStream(cacheFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            @SuppressWarnings("unchecked")
            ArrayList<T> data = (ArrayList<T>) ois.readObject();
            return data;
        } catch (java.io.IOException | ClassNotFoundException | ClassCastException e) {
            Log.e(TAG, "Error reading from cache file, deleting: " + fileName, e);
            cacheFile.delete();
        }
        return null;
    }
}
