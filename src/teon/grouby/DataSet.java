package teon.grouby;

import com.google.common.collect.Lists;
import teon.grouby.base.IGroupFunc;
import teon.grouby.base.IKeyFunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DataSet {

    List<DataUnit> table;

    public DataSet(List<DataUnit> table) {
        this.table = table;
    }

    public void addAll(DataSet anotherDataSet) {
        table.addAll(anotherDataSet.table);
    }

    public void addAll(List<Map<String, Object>> anotherDataSet) {

        addAll(new DataSet(
                anotherDataSet.stream()
                        .map(DataUnit::new)
                        .collect(Collectors.toList())));
    }

    /**
     * 数据聚合
     *
     * @param reduceFunctions 聚合时依据的函数表，Key是聚合后字段名，Value是对应聚合函数
     * @param keyFunctions    根据哪几个字段进行聚合, 由函数表取对应字段生成key
     * @return 聚合后数据集
     * @author hutt
     * @date 20170515
     */
    public DataSet groupBy(
            Map<String, IGroupFunc<DataUnit>> reduceFunctions,
            Map<String, IKeyFunc<DataUnit>> keyFunctions) {

        List<DataUnit> groupedData = Lists.newArrayList();
        this.clusterBy(keyFunctions)
                .forEach((keyMap, valueList) -> {
                    DataUnit re = aggregate(valueList, reduceFunctions);
                    re.self.putAll(keyMap);
                    groupedData.add(re);
                });
        return new DataSet(groupedData);
    }

    /**
     * 数据分类
     *
     * @param keyFunctions 根据哪几个字段进行聚合, 由函数表取对应字段生成key
     * @return 分类后数据集
     */
    public Map<KeyMap, List<DataUnit>> clusterBy(
            Map<String, IKeyFunc<DataUnit>> keyFunctions) {
        return table.stream().collect(
                Collectors.groupingBy(unit -> {
                    KeyMap newKeyMap = new KeyMap();
                    keyFunctions.forEach((key, func) ->
                            newKeyMap.put(key, func.apply(unit)));
                    return newKeyMap;
                })
        );
    }

    /**
     * 对一组数据进行聚合运算
     *
     * @param list            一组数据
     * @param reduceFunctions 聚合时依据的函数表，Key是聚合后字段名，Value是对应聚合函数
     * @return 聚合后的一条数据
     */
    public static DataUnit aggregate(
            List<DataUnit> list,
            Map<String, IGroupFunc<DataUnit>> reduceFunctions) {
        DataUnit ret = new DataUnit();
        reduceFunctions.forEach((field, func) -> ret.put(field, func.apply(list)));
        return ret;
    }

    private static class KeyMap extends HashMap<String, Object> {

        private static final long serialVersionUID = 2333L;

    }

}
