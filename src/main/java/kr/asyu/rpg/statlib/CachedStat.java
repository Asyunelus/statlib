package kr.asyu.rpg.statlib;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class CachedStat<K, S extends IStat> {
    private final StatCalculator<S> calculator;
    private final S finalizeStat;
    private final Map<K, S> cachedMap;

    public CachedStat(Class<S> statClass) {
        this(new HashMap<>(), statClass);
    }

    public CachedStat(Map<K, S> map, Class<S> statClass) {
        cachedMap = map;
        calculator = StatCalculator.getInstance(statClass);
        finalizeStat = Objects.requireNonNull(calculator).newInstance();
    }

    public void refreshAll() {
        finalizeStat.reset();
        for(S stat : cachedMap.values()) {
            calculator.safeSumToLeft(finalizeStat, stat);
        }
    }

    public void setStat(K key, @Nullable S stat) {
        setStat(key, stat, true);
    }

    public void setStat(K key, @Nullable S stat, boolean refreshAutoCall) {
        if (stat == null) {
            cachedMap.remove(key);
        } else {
            cachedMap.put(key, stat);
        }
        if (refreshAutoCall)
            refreshAll();
    }

    public S getFinalizeStat() {
        return finalizeStat;
    }
}
