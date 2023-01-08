package kr.asyu.rpg.statlib.annotations;

public enum IntegerSumType {
    DEFAULT(Long::sum)
    ;

    private final StatSumFunction<Long> func;

    IntegerSumType(StatSumFunction<Long> func) {
        this.func = func;
    }

    public StatSumFunction<Long> getFunction() {
        return func;
    }

    public Long sum(Long item1, Long item2) {
        return func.sum(item1, item2);
    }
}