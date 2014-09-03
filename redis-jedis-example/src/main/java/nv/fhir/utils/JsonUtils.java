package nv.fhir.utils;

import com.alibaba.fastjson.serializer.*;

/**
 * ��Ȩ���ڹ���Ѷ <br/>
 * ���ߣ�xuan.zhou@rogrand.com <br/>
 * �������ڣ�2014-1-10 <br/>
 * ��������Json�����ࡵ
 */
public class JsonUtils {

    @SuppressWarnings("unused")
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();

        // ʹ�ú�json-lib���ݵ����������ʽ
        // config.put(java.util.Date.class, new JSONLibDataFormatSerializer());

        // ʹ�ú�json-lib���ݵ����������ʽ
        // config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());

    }

    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // ��������ֶ�
            SerializerFeature.WriteNullListAsEmpty, // list�ֶ����Ϊnull�����Ϊ[]��������null
            SerializerFeature.WriteNullNumberAsZero, // ��ֵ�ֶ����Ϊnull�����Ϊ0��������null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean�ֶ����Ϊnull�����Ϊfalse��������null
            SerializerFeature.WriteNullStringAsEmpty, // �ַ������ֶ����Ϊnull�����Ϊ""��������null
            SerializerFeature.WriteDateUseDateFormat // ��ʽ������
    };

    public static String toJsonString(Object obj) {
        return toJsonString(obj, true);
    }

    public static String toJsonString(Object obj, boolean useDateFormat) {
        return toJsonString(obj, null, null, useDateFormat);
    }

    public static String toJsonString(Object obj, String[] includesProperties, String[] excludesProperties) {
        return toJsonString(obj, includesProperties, excludesProperties, true);
    }

    public static String toJsonString(Object obj, String[] includesProperties, String[] excludesProperties,
                                      boolean useDateFormat) {
        // excludes������includes
        FastjsonFilter filter = FastjsonFilterUtils.getInstance().getFilter(includesProperties, excludesProperties);

        return toJSONString(obj, filter, features, useDateFormat);
    }

    /**
     * ����������дJSON.toJSONString(Object object, SerializeFilter filter,
     * SerializerFeature[] features)���ܿ����Ƿ�ʹ�����ڸ�ʽ���� <br/>
     * ���ߣ�xuan.zhou@rogrand.com <br/>
     * �������ڣ�2014-3-26 <br/>
     */
    private static String toJSONString(Object object, SerializeFilter filter, SerializerFeature[] features,
                                       boolean useDateFormat) {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.config(SerializerFeature.WriteDateUseDateFormat, useDateFormat);

            if (filter != null) {
                if (filter instanceof PropertyPreFilter) {
                    serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
                }

                if (filter instanceof NameFilter) {
                    serializer.getNameFilters().add((NameFilter) filter);
                }

                if (filter instanceof ValueFilter) {
                    serializer.getValueFilters().add((ValueFilter) filter);
                }

                if (filter instanceof PropertyFilter) {
                    serializer.getPropertyFilters().add((PropertyFilter) filter);
                }
            }

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }
}
