package nv.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu.resource.Device;
import ca.uhn.fhir.model.dstu.resource.Patient;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.JsonParser;
import ca.uhn.fhir.parser.XmlParser;
import com.alibaba.fastjson.JSON;
import nv.fhir.utils.DateUtil;
import nv.fhir.utils.RedisUtil;
import nv.fhir.utils.StringUtil;
import org.apache.commons.io.IOUtils;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;
import java.util.Iterator;
import java.util.Set;
/**
 * Created by haha on 14-10-9.
 * HapiFHIR DEMO
 */
public class FhirRedis {

   public static FhirContext ctx = new FhirContext();
   public static Jedis redis = RedisUtil.getJedis();

    public static final String GenerateGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static final Bundle GetAllPatient() {

//        获取redis实例
        Set<String> patitent_logicalID_set = redis.smembers("Patient:all");
        Iterator<String> testSet = patitent_logicalID_set.iterator();
        List<IResource> patientALL = null;

        while (testSet.hasNext()) {
            String patitent_logicalID = testSet.next();
            IParser parser = ctx.newJsonParser();
            String key = "Patient:" +patitent_logicalID+ ":content";
            System.out.println("查到的逻辑标识符为："+patitent_logicalID);
            System.out.println("组合成的redis中key为："+key);
            String data = redis.get(key);
/*            try {
                data =  IOUtils.toString( new ByteArrayInputStream(data.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            System.out.println("资源的字符串为："+ data);
            IParser p = ctx.newJsonParser();
            // ourLog.info("Reading in message: {}", msg);
            Patient   res = p.parseResource(Patient.class,data);
//            Patient res = parser.parseResource(Patient.class,redis.get(key));
           patientALL.add(res);


//            patientALL.add(parser.parseResource(Patient.class, redis.get(key)));
        }

       return Bundle.withResources(patientALL,ctx,"localhost/fhir");
    }

    public static final Bundle GetAllPatientWithLogicalID(String patitent_logicalID) {
        String key = "Patient:" + patitent_logicalID + ":all";
        // LRANGE key start
        // stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
        List<String>   list = redis.lrange(key, 0, -1);
        List<IResource> patientALL = null;
        IParser parser = ctx.newJsonParser();

        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i));
            patientALL.add((parser.parseResource(Patient.class,list.get(i))));
        }

        return Bundle.withResources(patientALL,ctx,"localhost/fhir");
    }

    public static final Patient GetLatestPatientWithLogicalID(String patitent_logicalID) {


        // LRANGE key start
        // stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
        List<String>   list = redis.lrange("Patient:patitent_logicalID:all", 0, -1);
        IParser parser = ctx.newJsonParser();
        Patient patient = parser.parseResource(Patient.class,list.get(0));
        return patient;
    }

    public static final void CreatePatientResource(){
        //hapi读取json文件中的数据
        IParser parser = ctx.newJsonParser();

//        新增资源实例
        Patient patient = parser.parseResource(Patient.class, StringUtil.StringFromFile("/patient-example-a"));
        parser.setPrettyPrint(true);

// The various datatype classes have accessors called getValue()
        System.out.println(patient.getIdentifier().get(0).getUse().getValue()); // PRP1660
        System.out.println(patient.getIdentifier().get(0).getAssigner().getDisplay()); // PRP1660

//        生成GUID作为资源的logical ID
        String patitent_logicalID = GenerateGUID();
//        生成系统时间作为lastModifiedDate
        String patitent_LastModifiedDate = DateUtil.getNowWithZone();

//          设定资源实例版本
        String patitent_VersionId = String.valueOf(1);

//        获取redis实例
        Jedis redis = RedisUtil.getJedis();
//        将整个json字符串单独存储 key为Patient:LogicalID:content
        String Content_key = "Patient:" +patitent_logicalID+ ":content";
        String ContentAll_key = "Patient:" +patitent_logicalID+ ":all";
        String LastModifiedDate_key = "Patient:" +patitent_logicalID+ ":LastModifiedDate";
        String VersionId_key = "Patient:" +patitent_logicalID+ ":VersionId";
        
        
//        patient.setId(patitent_logicalID);
        String encoded = parser.encodeResourceToString(patient);

        try {
            String msg = IOUtils.toString(XmlParser.class.getResourceAsStream("/examples-json/patient-example-a.json"));


            redis.set(Content_key, msg);

        } catch (IOException e) {
            e.printStackTrace();
        }


/*
        System.out.println("key为"+Content_key);

        System.out.println("存储的字符串为"+redis.get(Content_key));
*/

        redis.lpush(ContentAll_key, encoded);

//        为Metadata建立索引
        redis.set(LastModifiedDate_key, patitent_LastModifiedDate);
        redis.set(VersionId_key, patitent_VersionId);
        redis.sadd("Patient:all", patitent_logicalID);
//        为查询变量建立索引

    }
    public static void main(String[] args) {
        FhirRedis test = new FhirRedis();
        test.CreatePatientResource();
        test.GetAllPatient();

    }
}
