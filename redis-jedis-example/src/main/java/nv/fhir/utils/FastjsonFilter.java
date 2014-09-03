package nv.fhir.utils;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * 版权：融贯资讯 <br/>
 * 作者：kai.gao@rogrand.com <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：JSON属性过渡器，主要用于过滤不需要序列化的属性，或者包含需要序列化的属性
 */
public class FastjsonFilter implements PropertyFilter {

    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public boolean apply(Object source, String name, Object value) {
        if (excludes.contains(name)) {
            return false;
        }
        if (excludes.contains(source.getClass().getSimpleName() + "." + name)) {
            return false;
        }
        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }
        if (includes.contains(source.getClass().getSimpleName() + "." + name)) {
            return true;
        }
        return false;
    }

}
