package kr.asyu.rpg.statlib;

import kr.asyu.rpg.statlib.annotations.FloatStatMember;
import kr.asyu.rpg.statlib.annotations.FloatSumType;
import kr.asyu.rpg.statlib.annotations.IntegerStatMember;

public class SimpleStat implements IStat {
    @IntegerStatMember
    public Long STR = 0L;

    @IntegerStatMember
    public Long DEX = 0L;

    @IntegerStatMember
    public Long INT = 0L;

    @IntegerStatMember
    public Long LUK = 0L;

    @FloatStatMember(type = FloatSumType.DIMINISHING)
    public Double DefenseIgnorePercent = 0.0d;

    @FloatStatMember(type = FloatSumType.AMPLIFICATION)
    public Double PvEDamage = 0.0d;

    @FloatStatMember(type = FloatSumType.DEFAULT)
    public Double MaxHP_Percent = 0.0d;

    public SimpleStat() {
        reset();
    }

    public void reset() {
        STR = 0L;
        DEX = 0L;
        INT = 0L;
        LUK = 0L;
        DefenseIgnorePercent = 0.0d;
        PvEDamage = 0.0d;
        MaxHP_Percent = 0.0d;
    }

    @Override
    public String toString() {
        return "SimpleStat{" +
            "STR=" + STR +
            ", DEX=" + DEX +
            ", INT=" + INT +
            ", LUK=" + LUK +
            ", DefenseIgnorePercent=" + DefenseIgnorePercent +
            ", PvEDamage=" + PvEDamage +
            ", MaxHP_Percent=" + MaxHP_Percent +
            '}';
    }
}
