# this

```java
public class A {
  private static int x = 100;
  class B {
    private int x = 200;
    public void display(){
      System.out.println("x1 : "+A.x);
      System.out.println("x2 : "+this.x);
    }
  }
}
```

```java
public class C {
  public static void main(String[] args){
    A aaa = new A();
    aaa.Inner bbb = aaa.new Inner();

    bbb.display();
  }
}
```

Output
> x1 : 100
> x2 : 200

#정규화된 this?
정규화된 this란 클래스명.this 형태로 바깥 클래스의 이름을 명시하는 용법을 말한다.
