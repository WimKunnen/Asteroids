package asteroids.model;

import java.util.stream.Stream;

/**
 * Created by WimKunnen on 02/05/2017.
 */
public interface WorldInterface extends Iterable{
    default Stream<Object> stream() {
        Stream.Builder<Object> builder = Stream.builder();
        for (Object element: this)
            builder.accept(element);
        return builder.build();
    }
}
