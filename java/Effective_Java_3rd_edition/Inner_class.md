# Inner Class 내부클래스

```
중첩 클래스의 하나로, 클래스나 인터페이스 내부에서 선언한다.
코드를 읽기 쉽고, 오래 유지하기 위해, 논리적인 그룹과 인터페이스들에서 inner class를 사용한다.
inner class는 개인적인 데이터 멤버와 메서드를 포함하는 외부 클래스의 모든 멤버들에 접근할 수 있다.
```

### inner class 의 이점

* 중첩 클래스는 개인적인 것을 포함하는 외부 클래스의 모든 멤버(데이터 멤버와 메서드등)에 접근할 수 있다
* 중첩 클래스는 논리 그룸 클래스와 인터페이스 내부에 있기 때문에 더 읽기 쉽고, 유지 가능한 코드 개발에 사용됨.
* 코드 최적화 : 작성하는데 더 적은 코드가 요구됨.

### nested class 와 inner class 의 차이
inner class는 nested class 의 한 부분임.
그중 static nested class 는 inner 클래스로 알려져 있다. 
