package kr.asyu.rpg.statlib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FloatStatMember {
    /**
     * 스탯끼리 더할 때 연산 할 방식입니다.
     * This is the method to calculate when sum IStat.
     */
    FloatSumType type() default FloatSumType.DEFAULT;
}