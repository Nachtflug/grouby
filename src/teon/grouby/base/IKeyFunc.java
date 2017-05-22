package teon.grouby.base;


import com.google.common.base.Function;

/**
 * 聚合时，对一个或多个字段进行转换，成为新的Key的运算函数
 * @author hutt
 *
 * @param <S> 某条数据的数据类型
 */
public interface IKeyFunc<S> extends Function<S, Object> {

}
