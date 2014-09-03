package nv.fhir.utils;


import java.util.Arrays;

/**
 * ��Ȩ���ڹ���Ѷ <br/>
 * ���ߣ�kai.gao@rogrand.com <br/>
 * �������ڣ�2013-10-24 <br/>
 * ������JSON���Թ�������ȡ��
 */
public class FastjsonFilterUtils {

    private static final FastjsonFilterUtils instance = new FastjsonFilterUtils();

    private FastjsonFilterUtils() {

    }

    public final static FastjsonFilterUtils getInstance() {
        return instance;
    }

    /**
     * ��ȡJSON���Թ�����
     */
    public FastjsonFilter getFilter(String[] includesProperties, String[] excludesProperties) {
        FastjsonFilter filter = new FastjsonFilter();
        // excludes������includes
        if (excludesProperties != null && excludesProperties.length > 0) {
            filter.getExcludes().addAll(Arrays.<String>asList(excludesProperties));
        }
        if (includesProperties != null && includesProperties.length > 0) {
            filter.getIncludes().addAll(Arrays.<String>asList(includesProperties));
        }
        return filter;
    }

    /**
     * �����ֶ�����
     */
    public FastjsonFilter getFilterByIncludes(String[] includesProperties) {
        return getFilter(includesProperties, null);
    }

    /**
     * �������ֶ�����
     */
    public FastjsonFilter getFilterByExcludes(String[] excludesProperties) {
        return getFilter(null, excludesProperties);
    }

}
