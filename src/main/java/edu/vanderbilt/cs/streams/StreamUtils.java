package edu.vanderbilt.cs.streams;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtils {

    public static <T> Stream<List<T>> slidingWindow(List<T> data, int windowSize){

        if (windowSize < 1) {
            return Stream.empty();
        } else {
            return IntStream.range(0, data.size() - windowSize + 1)
                    .mapToObj(start -> data.subList(start, start + windowSize));
        }
    }

    public static <T> Function<List<T>, Double> averageOfProperty(ToDoubleFunction<T> f){
        return (List<T> window) -> {
             OptionalDouble stream = window.stream()
                     .mapToDouble(d -> f.applyAsDouble(d))
                     .average();

            return stream.getAsDouble();
        };
    }

}
