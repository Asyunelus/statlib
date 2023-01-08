package kr.asyu.rpg.statlib.annotations;

@FunctionalInterface
public interface StatSumFunction<T> {
    T sum(T item1, T item2);
}
