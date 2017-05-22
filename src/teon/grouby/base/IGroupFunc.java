package teon.grouby.base;

import java.util.List;
import java.util.function.Function;

/**
 * 聚合函数
 * @author hutt
 *
 * @param <S> 聚合函数要处理的单条信息的类型
 */
public interface IGroupFunc<S> extends Function<List<S>, Object> {
	
}
