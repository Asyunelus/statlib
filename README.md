# StatLib
## 개요
- Minecraft ForgeMod 및 Plugin 개발에 사용할 라이브러리.
- StatLib-Processor 와 같이 사용시 컴파일러수준 Annotation 경고 제공.

## 사용 방법
1. IStat을 상속한 스탯 클래스 제작.
    - 주의사항 1. 매개변수가 없는 생성자가 있어야됨.
    - 주의사항 2. IntegerStatMember 또는 FloatStatMember 어노테이션이 붙은 Field 만 스탯으로 간주됨.
```java
class SimpleStat implements IStat {
    @IntegerStatMember
    public Integer STR = 0;

    @IntegerStatMember
    public Integer DEX = 0;

    @IntegerStatMember
    public Integer INT = 0;

    @IntegerStatMember
    public Integer LUK = 0;
    
    public SimpleStat() {
        
    }
}
```

2. StatCalculator.getInstance(SimpleStat.class) 방식으로 Calculator 를 불러올 수 있음.
3. 스탯 끼리 연산 시 (장비 스탯 덧셈 등) StatCalculator<T>::sumToLeft 또는 StatCalculator<T>::sumToNewOne 사용.
    - sumToLeft(item1, item2) : item1 객체에 item2 스탯값을 더함. (item1 데이터가 수정됨)
    - sumToNewOne(item1, item2) : item1 객체와 item2 객체의 스탯을 더한 값을 새로운 객채로 만들어 반환함. (item1, item2 두 객체 모두 수정되지 않음.)