# StatLib
## 개요
- Minecraft ForgeMod 및 Plugin 개발에 사용할 라이브러리.
- StatLib-Processor 와 같이 사용시 컴파일러수준 Annotation 오류 제공.
   - 미사용시 오류가 발생하지 않으나, 일부 상황에서 연산 결과 문제가 발생할 수 있음.

## 사용 방법
1. IStat을 상속한 스탯 클래스 제작.
    - 주의사항 1. 매개변수가 없는 생성자가 있어야됨.
    - 주의사항 2. IntegerStatMember 또는 FloatStatMember 어노테이션이 붙은 Field 만 스탯으로 간주됨.
```java
class SimpleStat implements IStat {
   @IntegerStatMember
   public Long STR = 0L;

   @IntegerStatMember
   public Long DEX = 0L;

   @IntegerStatMember
   public Long INT = 0L;

   @IntegerStatMember
   public Long LUK = 0L;
    
    public SimpleStat() {
        
    }
}
```

2. StatCalculator.getInstance(SimpleStat.class) 방식으로 Calculator 를 불러올 수 있음.
3. 스탯 끼리 연산 시 (장비 스탯 덧셈 등) StatCalculator<T>::sumToLeft 또는 StatCalculator<T>::sumToNewOne 사용.
    - sumToLeft(item1, item2) : item1 객체에 item2 스탯값을 더함. (item1 데이터가 수정됨)
    - sumToNewOne(item1, item2) : item1 객체와 item2 객체의 스탯을 더한 값을 새로운 객채로 만들어 반환함. (item1, item2 두 객체 모두 수정되지 않음.)

## 예제
### 코드
```java

public class Main {

    public static void main(String[] args) {
        Test test = new Test();
        test.STR = 1L;
        test.DEX = 2L;
        test.INT = 3L;
        test.LUK = 4L;
        Test test2 = new Test();
        test2.STR = 4L;
        test2.DEX = 3L;
        test2.INT = 2L;
        test2.LUK = 1L;

        System.out.println("Test!");
        System.out.println(test);
        System.out.println(test2);

        StatCalculator<Test> calculator = StatCalculator.getInstance(Test.class);
        Test test3 = calculator.safeSumToNewOne(test, test2);

        System.out.println(test3);
    }

    public static class Test implements IStat {
        @IntegerStatMember
        public Long STR = 0L;

        @IntegerStatMember
        public Long DEX = 0L;

        @IntegerStatMember
        public Long INT = 0L;

        @IntegerStatMember
        public Long LUK = 0L;

        public Test() {

        }

        @Override
        public String toString() {
            return "Test{" +
                "STR=" + STR +
                ", DEX=" + DEX +
                ", INT=" + INT +
                ", LUK=" + LUK +
                '}';
        }
    }
}
```
### 결과
```
Test!
Test{STR=1, DEX=2, INT=3, LUK=4}
Test{STR=4, DEX=3, INT=2, LUK=1}
Test{STR=5, DEX=5, INT=5, LUK=5}
```

## 비고
- IStat 을 상속해 만든 클래스에 hashCode, equals, toString 를 오버라이드해 재정의 하기.