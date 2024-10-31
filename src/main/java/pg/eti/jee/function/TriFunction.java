package pg.eti.jee.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T1, T2, U, R> {
    R apply(T1 var1, T2 var2, U var3);

    default <V> pg.eti.jee.function.TriFunction<T1, T2, U, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t1, t2, u) -> {
            return after.apply(this.apply(t1, t2, u));
        };
    }
}
