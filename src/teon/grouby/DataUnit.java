package teon.grouby;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 一条数据
 *
 * @author hutt
 */
public class DataUnit {

    public Map<String, Object> self;

    public DataUnit() {
        self = Maps.newHashMap();
    }

    public DataUnit(Map<String, Object> d) {
        self = d;
    }

    public void put(String s, Object o) {
        self.put(s, o);
    }

    public Object get(String s) {
        return self.get(s);
    }


}
