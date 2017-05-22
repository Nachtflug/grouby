package teon.grouby.base;

import java.util.List;
import java.util.function.Function;


public interface PrimaryFuncs {

    Function<List<Object>, Object> SUM_FUNC =
            list -> list.stream()
                    .mapToDouble(o -> Double.parseDouble(o.toString()))
                    .sum();


    Function<List<Object>, Object> COUNT_FUNC = List::size;


    Function<List<Object>, Object> AVERAGE_FUNC =
            list -> list.stream()
                    .mapToDouble(o -> Double.parseDouble(o.toString()))
                    .average();

    Function<Object, Object> SAME_FUNC = o -> o;


}
