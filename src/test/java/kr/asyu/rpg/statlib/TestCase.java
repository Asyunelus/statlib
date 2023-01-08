package kr.asyu.rpg.statlib;

import org.junit.jupiter.api.Test;

public class TestCase {
    @Test
    public void Test_StatCalculator_GetInstance_Check_NotNull() {
        StatCalculator<SimpleStat> calculator = StatCalculator.getInstance(SimpleStat.class);
        assert calculator != null;
    }

    @Test
    public void Test_StatCalculator_GetInstance_Argument_NotIStatClass() {
        StatCalculator<Long> calculator = StatCalculator.getInstance(Long.class);
        assert calculator == null;
    }

    @Test
    public void Test_StatCalculator_SumToLeft() {
        StatCalculator<SimpleStat> calculator = StatCalculator.getInstance(SimpleStat.class);

        assert calculator != null;

        SimpleStat stat1 = new SimpleStat();
        stat1.STR = 1L;
        stat1.DEX = 2L;
        stat1.INT = 3L;
        stat1.LUK = 4L;

        SimpleStat stat2 = new SimpleStat();
        stat2.STR = 4L;
        stat2.DEX = 3L;
        stat2.INT = 2L;
        stat2.LUK = 1L;

        calculator.safeSumToLeft(stat1, stat2);

        assert stat1.STR == 5L;
        assert stat1.DEX == 5L;
        assert stat1.INT == 5L;
        assert stat1.LUK == 5L;

        assert stat2.STR == 4L;
        assert stat2.DEX == 3L;
        assert stat2.INT == 2L;
        assert stat2.LUK == 1L;
    }

    @Test
    public void Test_StatCalculator_SumToNewOne() {
        StatCalculator<SimpleStat> calculator = StatCalculator.getInstance(SimpleStat.class);

        assert calculator != null;

        SimpleStat stat1 = new SimpleStat();
        stat1.STR = 1L;
        stat1.DEX = 2L;
        stat1.INT = 3L;
        stat1.LUK = 4L;

        SimpleStat stat2 = new SimpleStat();
        stat2.STR = 4L;
        stat2.DEX = 3L;
        stat2.INT = 2L;
        stat2.LUK = 1L;

        SimpleStat stat3 = calculator.safeSumToNewOne(stat1, stat2);

        assert stat3 != null;

        assert stat3.STR == 5L;
        assert stat3.DEX == 5L;
        assert stat3.INT == 5L;
        assert stat3.LUK == 5L;

        assert stat1.STR == 1L;
        assert stat1.DEX == 2L;
        assert stat1.INT == 3L;
        assert stat1.LUK == 4L;

        assert stat2.STR == 4L;
        assert stat2.DEX == 3L;
        assert stat2.INT == 2L;
        assert stat2.LUK == 1L;
    }

    @Test
    public void Test_StatCalculator_FloatTypeTest() {
        StatCalculator<SimpleStat> calculator = StatCalculator.getInstance(SimpleStat.class);

        assert calculator != null;

        SimpleStat stat1 = new SimpleStat();
        stat1.DefenseIgnorePercent = 0.1d;
        stat1.MaxHP_Percent = 0.1d;
        stat1.PvEDamage = 0.2d;

        SimpleStat stat2 = new SimpleStat();
        stat2.DefenseIgnorePercent = 0.3d;
        stat2.MaxHP_Percent = 0.3d;
        stat2.PvEDamage = 0.3d;

        SimpleStat stat3 = calculator.safeSumToNewOne(stat1, stat2);

        assert stat3 != null;

        assert stat3.PvEDamage == 0.56d;
        assert stat3.MaxHP_Percent == 0.4d;
        assert stat3.DefenseIgnorePercent == 0.37d;
    }
}
