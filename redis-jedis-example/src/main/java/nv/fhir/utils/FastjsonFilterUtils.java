package nv.fhir.utils;


import java.util.Arrays;

/**
 * 版权：融贯资讯 <br/>
 * 作者：kai.gao@rogrand.com <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：JSON属性过渡器获取类
 */
public class FastjsonFilterUtils {

    private static final FastjsonFilterUtils instance = new FastjsonFilterUtils();

    private FastjsonFilterUtils() {

    }

    public final static FastjsonFilterUtils getInstance() {
        return instance;
    }

    /**
     * 获取JSON属性过渡器
     */
    public FastjsonFilter getFilter(String[] includesProperties, String[] excludesProperties) {
        FastjsonFilter filter = new FastjsonFilter();
        // excludes优先于includes
        if (excludesProperties != null && excludesProperties.length > 0) {
            filter.getExcludes().addAll(Arrays.<String>asList(excludesProperties));
        }
        if (includesProperties != null && includesProperties.length > 0) {
            filter.getIncludes().addAll(Arrays.<String>asList(includesProperties));
        }
        return filter;
    }

    /**
     * 包含字段属性
     */
    public FastjsonFilter getFilterByIncludes(String[] includesProperties) {
        return getFilter(includesProperties, null);
    }

    /**
     * 不包含字段属性
     */
    public FastjsonFilter getFilterByExcludes(String[] excludesProperties) {
        return getFilter(null, excludesProperties);
    }

}
