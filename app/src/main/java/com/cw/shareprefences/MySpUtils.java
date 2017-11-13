package com.cw.shareprefences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Create By chao.wang on 2017/11/13 10:45
 * <p>
 * email：wc0811@163.com
 * <p>
 * 类描述：
 * <p>
 * 更改记录：
 */
public class MySpUtils {
    public static final String FILE_NAME = "cw_eawms";

    public static void save(String key, String object) throws Exception {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AESEncryptor.encrypt(key), AESEncryptor.encrypt(object));
        SharedPreferencesCompat.apply(editor);
    }

    public static void saveBoolean(String key, Boolean object) throws Exception {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(AESEncryptor.encrypt(key), object);
        SharedPreferencesCompat.apply(editor);
    }

    public static Object get(String key) throws Exception {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(AESEncryptor.encrypt(key), "");
    }

    public static Object getBoolean(String key) throws Exception {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(AESEncryptor.encrypt(key), false);
    }

    public static void remove(String key) throws Exception {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(AESEncryptor.encrypt(key));
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear() {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(String key) throws Exception {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(AESEncryptor.encrypt(key));
    }

    public static Map<String, ?> getAll() {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    public static <T> List<T> str2SceneList(String str)
            throws IOException, ClassNotFoundException {

        byte[] listBytes = Base64.decode(str.getBytes(), Base64.DEFAULT);

        ByteArrayInputStream listIS = new ByteArrayInputStream(listBytes);

        ObjectInputStream objIS = new ObjectInputStream(listIS);

        List<T> SceneList = (List<T>) objIS.readObject();

        objIS.close();

        return SceneList;

    }

    public static <T> String sceneList2Str(List<T> SceneList) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(SceneList);

        String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));

        objectOutputStream.close();

        return SceneListString;
    }

}
