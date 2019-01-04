# 입력스트림

단일바이트 read()
--------------
```Java
// OutputStream과 달리 Flushable을 구현하지 않음
public abstract class InputStream implements Closeable {
    // 입력 스트림의 매체로부터 단일 바이트를 읽고, 0~255사이의 값을 정수 타입으로 반환.
    // 스트림의 끝에 도달할 경우 -1을 반환한다.
    // 1바이트도 읽을 것이 없는 경우 프로그램의 실행을 중단하고 기다린다.
    public abstract int read() throws IOException;
}
```

* TelnetInputStream
TelnetInputStream은 네트워크 연결로부터 데이터를 읽는데 read() 메서드를 사용한다.
이 클래스의 경우 sun.net 패키지 안에 숨겨져 있고 문서화조차 되어 있지 않음.
```Java
public final class URL implements java.io.Serializable {
    // 여기서 실제로 반환되는 인스턴스는 TelnetInputStream 인스턴스다.
    public final InputStream openStream() throws java.io.IOException {
        return openConnection().getInputStream();
    }
}
```

* ByteArrayInputStream
바이트 배열로부터 데이터를 읽는데 read() 메서드를 사용한다.
```Java
public class ByteArrayInputStream extends InputStream {
    // 순수 자바 코드를 사용하여 배열로부터 바이트를 복사하도록 구현되어 있다.
    public synchronized int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }
}
```
멀티바이트 read()
--------------
```Java
// read()메서드를 오버로드한 두개의 메서드

// 배열 input 크기만큼 읽기를 시도함.
// 실제로 읽은 바이트 수를 반환한다.
public int read(byte b[]) throws IOException {
    return read(b, 0, b.length);
}

// 배열의 offset위치부터 length길이만큼 읽기를 시도함.
public int read(byte b[], int off, int len) throws IOException {
    Objects.checkFromIndexSize(off, len, b.length);

    // 임시적으로 비어 있는 열린 네트워크 버퍼에 대해 읽기를 시도할 경우 일반적으로 0을 반환한다.
    // 읽을 데이터는 없지만 스트림은 여전히 열려 있는 상태임을 나타낸다.
    // 이러한 동작 방식은 같은 상황에서 스레드의 실행을 중지시키는 단일 바이트 read()보다 종종 유용하게 사용된다
    if (len == 0) {
        return 0;
    }

    int c = read();
    if (c == -1) {
        return -1;
    }
    b[off] = (byte)c;

    int i = 1;
    try {
        for (; i < len ; i++) {
            c = read();
            if (c == -1) {
                break;
            }
            b[off + i] = (byte)c;
        }
    } catch (IOException ee) {
    }
    return i;
}
```
아직 읽지 않은 데이터가 남아 있는 상태에서 스트림이 종료될 경우 멀티바이트 read()메서드는 버퍼를 비울 때까지 데이터를 모두 읽어 반환한다.


available()
--------------
필요한 데이터를 모두 읽을 수 있을 때까지 실행이 대기되는 상황을 원하지 않을 경우,
대기 없이 즉시 읽을 수 있는 바이트 수를 반환하는 available() 메서드를 사용하여 읽을 바이트 수를 결정할 수 있다.
```Java
// 이 메서드는 읽을 수 있는 최소 바이트 수를 반환한다.
// 좀 더 많은 바이트를 읽을 수도 있겠지만, 최소한 available()메서드가 제안하는 만큼은 읽을 수 있음을 의미
public int available() throws IOException {
    return 0;
}
```

skip()
--------------
종종 일부 데이터를 읽지 않고 건너뛰어야 할 경우가 있다.
네트워크 연결보다는 파일로부터 읽을 때 더 유용하다.
> 네트워크 연결은 순차적이며 일반적으로 상당히 느리다. 그러므로 데이터를 건너뛴다고 해서 성능에 크게 영향을 주지 않는다.
> 파일을 읽을 때 조차도 네트워크와 달리 임의 위치 접근이 가능하기 때문에 이 경우에도 건너뛰는 방식보다는 파일 포인터의 위치를 재지정하는 편이 낫다.

```Java
// 이 메서드는 읽을 수 있는 최소 바이트 수를 반환한다.
// 좀 더 많은 바이트를 읽을 수도 있겠지만,
// 최소한 available()메서드가 제안하는 만큼은 읽을 수 있음을 의미
public int available() throws IOException {
    return 0;
}
```
```Java
protected int pos;
protected int count;
// ByteArrayInputStream에서 구현한 available()
public synchronized int available() {
    return count - pos;
}
```

close()
--------------
파일 핸들 또는 포트 같은 스트림과 관련된 리소스를 해제한다
입력 스트림을 닫은 후에 추가적인 읽기 시도가 있는 경우 IOException이 발생한다.

`그러나 몇몇 종류의 스트림은 닫은 후에도 해당 스트림의 객체에 대해서 일부 작업이 허용된다.`
```Java
// java.security.DigestInputStream의 경우 일반적으로 데이터를 읽고
// 스트림을 종료한 후에야 메시지 다이제스트(digest)를 얻을 수 있다.
// 메시지 다이제스트에 대한 getter와 setter메서드가 정의되어 있다.
public class DigestInputStream extends FilterInputStream {
    protected MessageDigest digest;

    public MessageDigest getMessageDigest() {
        return digest;
    }

    public void setMessageDigest(MessageDigest digest) {
        this.digest = digest;
    }
}
```
위치 표시와 위치 재설정
------------
실제로는 표시와 재설정을 지원하는 스트림보다 지원하지 않는 스트림이 더 많다.
대부분의 서브클래스에서 사용할 수 없는 기능을 추상화된 슈퍼클래스에 추가하는 것은 좋은 방법이 아니다
이런 메서드의 경우, 각각을 인터페이스에 포함시켜 그런 기능을 제공하도록 구현하는 편이 더 좋을 것이다
=> 물론 이와 같은 방식으로는 입력 스트림의 타입을 알지 못하는 경우, 위에서 말한 메서드를 실행할 수 없는 문제가 발생함
(해당 메서드를 구현한 입력스트림이 있고, 구현하지 않은 입력스트림이 있을 수 있으므로)


```Java
// 다음 세 가지 메서드는 일반적으로 잘 사용되지 않는다.
// 스트림의 위치를 표시(mark)하거나 이미 읽은 데이터를 다시 읽을 수 있다

public void mark(int readAheadLimit)
public void reset() throws IOException
public boolean markSupported()
```

* 데이터를 다시 읽으려면,
1. 먼저 mark() 메서드를 사용하여 스트림의 현재 위치를 표시
2. 나중에 필요한 시점에서 reset()메서드를 호출하여 표시된 위치로 스트림을 재설정
3. 그 다음에 읽기를 시도하면 표시된 위치에서 읽은 데이터가 반환된다
    * 스트림의 위치 재설정이 항상 성공하는 것은 아님!!
      > 표시된 위치로부터 읽을 수 있는 바이트 수와 여전히 재설정 가능한지 여부는 mark()메서드 호출시 제공한 readAheadLimit인자에 의해 결정된다. 표시된 위치로부터 너무 많이 읽은 후에 재설정을 시도하면 IOException이 발생한다.
      > 게다가 스트림은 동시에 하나의 위치만 표시할 수 있다. 두 번재 위치를 표시하면 첫 번쨰 위치는 사라진다.

스트림 위치의 표시와 재설정은 일반적으로 표시된 위치에서부터 읽은 모든 데이터를 내부버퍼에 저장하는 방식으로 구현된다.
그러나 모든 입력 스트림이 이 기능을 지원하는 것은 아니다.
이 기능을 사용하기 전에 markSupported() 메서드를 사용하여 지원 유무를 확인해야 한다.
이 기능을 지원하지 않는 입력 스트림에 mark()메서드를 호출하면 아무런 일도 발생하지 않지만 reset() 메서드는 IOException을 발생시킨다.

java.io내에서 스트림 위치의 표시를 항상 지원하는 유일한 입력 스트림 클래스는 BufferedInputStream과 ByteArrayInputStream이다.
TelnetInputStream과 같은 다른 입력스트림이 스트림의 위치의 표시를 지원하기 위해서는 먼저 버퍼 입력 스트림과의 연결이 필요하다.
