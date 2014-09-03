package nv.fhir.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

public class JsonUtil {

    private static final String CHARSET = "UTF-8";
    private static JSONObject jsonObject;

    public static final String loadElement(String key,JSONObject jsonObject) {
        String[] keyArr = key.split("\\.");
        JSONObject targetObject = jsonObject;
        int length = keyArr.length - 1;
        try {
            for (int i = 0; i < length; i++) {
                targetObject = targetObject.getJSONObject(keyArr[i]);
            }
            return targetObject.getString(keyArr[length]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Map<String, String> jsonToMap(String jsonsr) {
        Map<String, String> map = null;
        map = (Map<String, String>) JSON.parse(jsonsr) ;
        return map;
    }

    public static Map<String, String> jsonArrayToMap(JSONObject[] arr ) {
            Map map = new HashMap();
            if (arr == null)
                return map;
            if (arr.length % 2 != 0)
                throw new IllegalArgumentException("arr must of an even size");
            int i;
            for (i=0;i<arr.length;i+=2) {
                map.put(arr[i],arr[i+1]);
            }
            return map;
        }
    /**
     * 将非集合类java对象转换成为json object
     *
     * @param o
     * @return
     */
    public static JSONObject Object2JsonObject(Object o) {
        try {
            return (JSONObject) JSON.toJSON(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject jsonString2JsonObject(String json) {
        try {
            return (JSONObject) JSON.parseObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject bytes2JsonObject(byte[] bytes) {
        try {
            return (JSONObject) JSON.parseObject(new String(bytes, CHARSET));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T bytes2T(byte[] bytes, Class<T> classOfT) {
        try {
            return JSON.parseObject(new String(bytes, CHARSET), classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将集合类java对象装换成为json array
     *
     * @param collection
     * @return
     */
    public static JSONArray Collection2JsonArray(Collection collection) {
        try {
            return (JSONArray) JSON.toJSON(collection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象o转换成json格式数据
     *
     * @param o
     * @return
     */
    public static String Object2Json(Object o) {
        try {
            return JSON.toJSONString(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更加方便观察，但会增加存储空间
     *
     * @param o
     * @return
     */
    public static String Object2JsonPrettyFormat(Object o) {
        try {
            return JSON.toJSONString(o, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转换成json格式数据，使用全序列化方案
     *
     * @param o
     * @return
     */
    public static String Object2JsonSerial(Object o) {
        try {
            return JSON.toJSONString(o, SerializerFeature.WriteClassName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将列表转换成为json，使用全序列化方案
     *
     * @param l
     * @return
     */
    public static String list2JsonSerial(List l) {
        if (null == l) {
            return null;
        }
        try {
            return JSON.toJSONString(l, SerializerFeature.WriteClassName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将全序列化json转换成为java对象
     *
     * @param json
     * @return
     */
    public static Object JsonSerial2Object(String json) {
        try {
            return JSON.parse(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将全序列化json转换成为list对象
     *
     * @param json
     * @return
     */
    public static List JsonSerial2List(String json) {
        try {
            return JSON.parseArray(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将全序列化json转换为jsonArray对象
     *
     * @param json
     * @return
     */
    public static JSONArray JsonSerial2JsonArray(String json) {
        try {
            return JSON.parseArray(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json转换成为jsonArray对象
     *
     * @param str
     * @return
     */
    public static JSONArray Json2JsonArray(String str) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        try {
            return JSON.parseArray(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json转换成为java对象
     *
     * @param str
     * @param classOfT
     * @return
     */
    public static <T> T Json2T(String str, Class<T> classOfT) {
        if (null == str) {
            return null;
        }
        try {
            return JSON.parseObject(str, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json转换成为java数组
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> List<T> Json2List(String json, Class<T> classOfT) {
        if (null == json) {
            return null;
        }
        try {
            return JSON.parseArray(json, classOfT);
        } catch (Exception e) {
            return null;
        }
    }


}
