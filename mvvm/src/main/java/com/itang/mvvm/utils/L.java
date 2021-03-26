package com.itang.mvvm.utils;

import android.util.Log;
import android.util.Pair;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.orhanobut.logger.Settings;

import com.itang.mvvm.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * using beautiful Open-Source log util .
 *
 * @see "https://github.com/orhanobut/logger.git"
 */
public class L {

    private L() {
    }

    public static Settings init(boolean debug) {
        isDebug = debug;
        return Logger.init();
    }

    public static Settings init(String tag) {
        return Logger.init(tag);
    }

    public static Printer tt(String tag) {
        return Logger.t(tag);
    }

    public static Printer tt(int methodCount) {
        return Logger.t(methodCount);
    }

    public static Printer tt(String tag, int methodCount) {
        return Logger.t(tag, methodCount);
    }

    public static void dd(String message, Object... args) {
        Logger.t(getClassName()).d(message, args);
    }

    public static void ee(String message, Object... args) {
        Logger.t(getClassName()).e(message, args);
    }

    public static void ee(Throwable throwable, String message, Object... args) {
        Logger.t(getClassName()).e(throwable, message, args);
    }

    public static void ii(String message, Object... args) {
        Logger.t(getClassName()).i(message, args);
    }

    public static void vv(String message, Object... args) {
        Logger.t(getClassName()).v(message, args);
    }

    public static void ww(String message, Object... args) {
        Logger.t(getClassName()).w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.t(getClassName()).wtf(message, args);
    }

    public static void json(String json) {
        Logger.t(getClassName()).json(json);
    }

    public static void xml(String xml) {
        Logger.t(getClassName()).xml(xml);
    }

    /**
     * support list、map、array
     *
     * @see "https://github.com/pengwei1024/LogUtils"
     */
    public static void object(Object object) {
        if (object != null) {
            final String simpleName = object.getClass().getSimpleName();
            if (object.getClass().isArray()) {
                String msg = "Temporarily not support more than two dimensional Array!";
                int dim = ArrayUtils.getArrayDimension(object);
                switch (dim) {
                    case 1:
                        Pair pair = ArrayUtils.arrayToString(object);
                        msg = simpleName.replace("[]", "[" + pair.first + "] {\n");
                        msg += pair.second + "\n";
                        break;
                    case 2:
                        Pair pair1 = ArrayUtils.arrayToObject(object);
                        Pair pair2 = (Pair) pair1.first;
                        msg = simpleName.replace("[][]", "[" + pair2.first + "][" + pair2.second + "] {\n");
                        msg += pair1.second + "\n";
                        break;
                    default:
                        break;
                }
                Logger.t(getClassName()).d(msg + "}");
            } else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                String msg = "%s size = %dd [\n";
                msg = String.format(msg, simpleName, collection.size());
                if (!collection.isEmpty()) {
                    Iterator<Object> iterator = collection.iterator();
                    int flag = 0;
                    while (iterator.hasNext()) {
                        String itemString = "[%dd]:%s%s";
                        Object item = iterator.next();
                        msg += String.format(itemString, flag, objectToString(item),
                                flag++ < collection.size() - 1 ? ",\n" : "\n");
                    }
                }
                Logger.t(getClassName()).d(msg + "\n]");
            } else if (object instanceof Map) {
                String msg = simpleName + " {\n";
                Map<Object, Object> map = (Map<Object, Object>) object;
                Set<Object> keys = map.keySet();
                for (Object key : keys) {
                    String itemString = "[%s -> %s]\n";
                    Object value = map.get(key);
                    msg += String.format(itemString, objectToString(key),
                            objectToString(value));
                }
                Logger.t(getClassName()).d(msg + "}");
            } else {
                Logger.t(getClassName()).d(objectToString(object));
            }
        } else {
            Logger.t(getClassName()).d(objectToString(object));
        }
    }

    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    // 基本数据类型
    private final static String[] types = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static <T> String objectToString(T object) {
        if (object == null) {
            return "object{object is null}";
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean flag = false;
                for (String type : types) {
                    if (field.getType().getName().equalsIgnoreCase(type)) {
                        flag = true;
                        Object value = null;
                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException e) {
                            value = e;
                        } finally {
                            builder.append(String.format("%s=%s, ", field.getName(),
                                    value == null ? "null" : value.toString()));
                            break;
                        }
                    }
                }
                if (!flag) {
                    builder.append(String.format("%s=%s, ", field.getName(), "object"));
                }
            }
            return builder.replace(builder.length() - 2, builder.length() - 1, "}").toString();
        } else {
            return object.toString();
        }
    }


    /**
     * 系统原生的Log
     */
    public static boolean isDebug = true;
    public static final String TAG = "[app_log]";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }


    // 下面是传入自定义tag的函数
    public static void i(String TAG, String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String TAG, String msg) {
        if (isDebug && !StringUtils.isEmpty(msg))
            Log.d(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (isDebug && msg != null)
            Log.e(TAG, msg);
    }

    public static void v(String TAG, String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

}
