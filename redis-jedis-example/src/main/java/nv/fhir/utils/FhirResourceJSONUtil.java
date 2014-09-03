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
     * ���Ǽ�����java����ת����Ϊjson object
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
     * ת��ΪObject
     *
     * @param str
     * @return
     */
    public static Object JsonToObject(String str, Class<?> clz) {
        return JSON.parseObject(str, clz);
    }

    /**
     * ���л�object������obj��������Ϣ
     *
     * @param obj
     * @return
     */
    public static String ObjectToJsonWithClassName(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteClassName,
                SerializerFeature.PrettyFormat);
    }

    /**
     * ��objectת��Ϊjson����
     *
     * @param obj
     * @return
     */
    public static  String ObjectToJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }

}
