package teon.grouby;

import teon.grouby.base.IKeyFunc;
import teon.grouby.base.PrimaryFuncs;

import java.util.function.Function;

public enum SimpleKeyFuncs {
	
	SAME(PrimaryFuncs.SAME_FUNC);
	
	protected Function<Object, Object> f;

	SimpleKeyFuncs(Function<Object, Object> f) {
		this.f = f;
	}
	
	public IKeyFunc<DataUnit> of(final String field) {
		return map -> f.apply(map.get(field));
	}
	
}
