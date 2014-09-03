package nv.fhir.utils;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * ��Ȩ���ڹ���Ѷ <br/>
 * ���ߣ�kai.gao@rogrand.com <br/>
 * �������ڣ�2013-10-24 <br/>
 * ������JSON���Թ���������Ҫ���ڹ��˲���Ҫ���л������ԣ����߰�����Ҫ���л�������
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
