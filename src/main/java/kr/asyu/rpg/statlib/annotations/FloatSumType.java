package kr.asyu.rpg.statlib.annotations;

public enum FloatSumType {
    DEFAULT(Double::sum),
    AMPLIFICATION((p1, p2) -> (p1 + 1.0d) * (p2 + 1.0d) - 1.0d),
    DIMINISHING((p1, p2) -> 1.0d - (1.0d - p1) * (1.0d - p2)),
    ;

    private final StatSumFunction<Double> func;

    FloatSumType(StatSumFunction<Double> func) {
        this.func = func;
    }

    public StatSumFunction<Double> getFunction() {
        return func;
    }

    public Double sum(Double item1, Double item2) {
        return func.sum(item1, item2);
    }
}
