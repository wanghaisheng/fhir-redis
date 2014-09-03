package nv;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu.resource.Device;
import ca.uhn.fhir.model.dstu.resource.Patient;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.parser.IParser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import nv.fhir.utils.FhirResourceJSONUtil;
import nv.fhir.utils.JsonUtil;
import nv.fhir.utils.StringUtil;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by wd on 2014/9/3.
 */
public class FastJson {
    public static void main(String[] args) {
        // Fastjson读取json实例
        JSONObject patient1 = JsonUtil.jsonString2JsonObject(StringUtil.StringFromFile("patient-example"));
        Map patientmap = JsonUtil.jsonToMap(StringUtil.StringFromFile("patient-example"));
        JSONArray identifier_jsonArray = patient1.getJSONArray("identifier");
        String identifier_jsonArrayString = identifier_jsonArray.getString(0);
        Map identifierMap = JsonUtil.jsonToMap(identifier_jsonArrayString);
        System.out.println(identifierMap.get("use"));
        JSONObject identifier = JsonUtil.jsonString2JsonObject(identifier_jsonArrayString);

        JSONObject assigner_jSONObject = identifier.getJSONObject("assigner");
        Map assigner_Map = JsonUtil.jsonToMap(assigner_jSONObject.toJSONString());
        System.out.println(assigner_Map.get("display"));

/*

        //hapi自带序列化
        FhirContext ctx = new FhirContext();
        IParser parser = ctx.newJsonParser();
        Patient patient = parser.parseResource(Patient.class, StringUtil.StringFromFile("patient-example"));

// The various datatype classes have accessors called getValue()
        System.out.println(patient.getIdentifier().get(0).getUse().getValue()); // PRP1660
        System.out.println(patient.getIdentifier().get(0).getAssigner().getDisplay()); // PRP1660
*/


    }
}
