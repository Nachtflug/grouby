package teon.grouby;

import teon.grouby.base.IGroupFunc;
import teon.grouby.base.PrimaryFuncs;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * 一些简单的聚合函数
 *
 * @author hutt
 */
public enum SimpleGroupFuncs {

    SUM(PrimaryFuncs.SUM_FUNC),
    AVG(PrimaryFuncs.AVERAGE_FUNC),
    COUNT(PrimaryFuncs.COUNT_FUNC);

    protected Function<List<Object>, Object> f;

    SimpleGroupFuncs(Function<List<Object>, Object> f) {
        this.f = f;
    }

    public IGroupFunc<DataUnit> of(final String field) {
        return list -> f.apply(
                list.stream()
                        .map(unit -> unit.get(field))
                        .collect(toList())
        );
    }

    public IGroupFunc<DataUnit> of(
            final Function<List<Object>, Object> combinationFunc,
            final String... fields) {
        return list ->
                f.apply(list.stream().map(
                        unit -> combinationFunc.apply(
                                stream(fields).map(unit::get).collect(toList())
                                )
                        ).collect(toList())
                );

    }

    public IGroupFunc<DataUnit> of(
            final SimpleGroupFuncs combinationFunc, final String... field) {
        return of(combinationFunc.f, field);
    }

    /**
     * 给选定单字段添加过滤条件
     *
     * @param p 过滤函数
     * @return 被过滤函数包裹的新函数
     */
    public SimpleGroupFuncs when(final Predicate<Object> p) {
        f = list -> f.apply(list.stream().filter(p).collect(toList()));
        return this;
    }

}
