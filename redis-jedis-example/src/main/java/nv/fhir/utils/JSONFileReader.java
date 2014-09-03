package nv.fhir.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JSONFileReader {
    private static JSONObject jsonObject;

    public static final Object loadFile(String fileName) {
        String filefullName = JSONFileReader.class.getResource("/").getPath() + "config/" + fileName + ".json";
        try {
            String json = FileUtils.readFileToString(new File(filefullName));
            jsonObject = JSON.parseObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static final String loadElement(String key) {
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
}