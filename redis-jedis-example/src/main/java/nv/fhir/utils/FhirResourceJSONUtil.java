package nv.fhir.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wd on 2014/9/3.
 */
public class FhirResourceJSONUtil {


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

    public static String getJsonString(Object result) {
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    public static String toPrettyJson(String json) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为Object
     *
     * @param str
     * @return
     */
    public static Object JsonToObject(String str, Class<?> clz) {
        return JSON.parseObject(str, clz);
    }

    /**
     * 序列化object，带有obj的类型信息
     *
     * @param obj
     * @return
     */
    public static String ObjectToJsonWithClassName(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteClassName,
                SerializerFeature.PrettyFormat);
    }

    /**
     * 将object转换为json数据
     *
     * @param obj
     * @return
     */
    public static  String ObjectToJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }

}
