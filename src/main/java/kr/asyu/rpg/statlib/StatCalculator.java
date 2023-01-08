package kr.asyu.rpg.statlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.asyu.rpg.statlib.annotations.FloatStatMember;
import kr.asyu.rpg.statlib.annotations.IntegerStatMember;
import kr.asyu.rpg.statlib.annotations.StatSumFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatCalculator<T> {
    private static final Map<Class<?>, StatCalculator<?>> calculators = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> StatCalculator<T> getInstance(@NotNull Class<T> clazz) {
        if (calculators.containsKey(clazz)) {
            return (StatCalculator<T>) calculators.get(clazz);
        }
        try {
            StatCalculator<T> calculator = new StatCalculator<>(clazz);
            calculators.put(clazz, calculator);
            return calculator;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    private final Class<T> clazz;

    @NotNull
    private final List<ICacheMember> memberList;

    @NotNull
    private final Constructor<T> constructor;

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    private StatCalculator(@NotNull Class<T> clazz) throws NoSuchMethodException {
        this.clazz = clazz;
        this.constructor = clazz.getConstructor(null);
        this.memberList = new ArrayList<>();

        cache();
    }

    @Nullable
    private T newInstance() {
        try {
            return this.constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get StatCalculator's IStat Class
     * @return StatCalculator's IStat Class Instance
     */
    @NotNull
    public Class<T> getStatClass() {
        return this.clazz;
    }

    /**
     * Sum IStat member
     * @param item1 Item 1 for sum
     * @param item2 Item 2 for sum
     * @return if null new instance create failed, else sum result.
     */
    @Nullable
    public T safeSumToNewOne(@NotNull T item1, @NotNull T item2) {
        try {
            return sumToNewOne(item1, item2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sum IStat member
     * @param item1 Item 1 for sum
     * @param item2 Item 2 for sum
     * @return if null new instance create failed, else sum result.
     */
    @Nullable
    public T sumToNewOne(@NotNull T item1, @NotNull T item2) {
        T target = newInstance();
        if (target == null)
            return null;
        for (ICacheMember member : memberList) {
            member.sumToLeft(target, item1, item2);
        }
        return target;
    }

    /**
     * Sum IStat member
     * Add source value to target. (item1 value is modified)
     * @param target Target Object
     * @param source Source Object
     */
    public void safeSumToLeft(@NotNull T target, @NotNull T source) {
        try {
            sumToLeft(target, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sum IStat member
     * Add source value to target. (item1 value is modified)
     * @param target Target Object
     * @param source Source Object
     */
    public void sumToLeft(@NotNull T target, @NotNull T source) {
        for (ICacheMember member : memberList) {
            member.sumToLeft(target, source);
        }
    }

    /**
     * Cache Class Field Members.
     */
    private void cache() {
        memberList.clear();

        Field[] fields = this.clazz.getDeclaredFields();

        for(Field field : fields) {
            FloatStatMember fsm = field.getAnnotation(FloatStatMember.class);
            IntegerStatMember ism = field.getAnnotation(IntegerStatMember.class);

            if (fsm != null) {
                memberList.add(new FloatCache(field, fsm.type().getFunction()));
            } else if (ism != null) {
                memberList.add(new IntegerCache(field, ism.type().getFunction()));
            }
        }
    }

    public static final DecimalFormat floatStatFormat = new DecimalFormat("0.#%");
    public static final DecimalFormat integerStatFormat = new DecimalFormat("#0");

    private interface ICacheMember {
        Object get(Object target);
        void sumToLeft(Object target, Object source);
        void sumToLeft(Object target, Object source1, Object source2);
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class FloatCache implements ICacheMember {
        private final Field field;
        private final StatSumFunction<Double> func;

        public FloatCache(Field field, StatSumFunction<Double> func) {
            this.field = field;
            this.func = func;
        }

        public Object get(Object target) {
            try {
                return this.field.get(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void sumToLeft(Object target, Object source) {
            try {
                Double item1 = (Double) this.field.get(target);
                Double item2 = (Double) this.field.get(source);

                this.field.set(target, func.sum(item1, item2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sumToLeft(Object target, Object source1, Object source2) {
            try {
                Double item1 = (Double) this.field.get(target);
                Double item2 = (Double) this.field.get(source1);
                Double item3 = (Double) this.field.get(source2);

                this.field.set(target, func.sum(func.sum(item1, item2), item3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "FloatCache{" +
                "field=" + field +
                ", func=" + func +
                '}';
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class IntegerCache implements ICacheMember {
        private final Field field;
        private final StatSumFunction<Long> func;

        public IntegerCache(Field field, StatSumFunction<Long> func) {
            this.field = field;
            this.func = func;
        }

        public Object get(Object target) {
            try {
                return this.field.get(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void sumToLeft(Object target, Object source) {
            try {
                Long item1 = (Long) this.field.get(target);
                Long item2 = (Long) this.field.get(source);

                this.field.set(target, func.sum(item1, item2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sumToLeft(Object target, Object source1, Object source2) {
            try {
                Long item1 = (Long) this.field.get(target);
                Long item2 = (Long) this.field.get(source1);
                Long item3 = (Long) this.field.get(source2);

                this.field.set(target, func.sum(func.sum(item1, item2), item3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "IntegerCache{" +
                "field=" + field +
                ", func=" + func +
                '}';
        }
    }
}