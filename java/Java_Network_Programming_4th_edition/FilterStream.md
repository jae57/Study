# 필터 스트림
필터링은 순수하게 스트림 내부에서 이뤄지며 public으로 선언된 어떠한 인터페이스도 제공하지 않는다.

그러나 대부분의 경우 필터스트림은 부가적인 목적으로 public메서드를 추가하여 제공한다.
```java
// PushbackInputStream의 unread()같은 메서드가 일반적인 read(), write()메서드와 함께 사용될 목적으로 추가된다.
public class PushbackInputStream extends FilterInputStream {

    public void unread(int b) throws IOException {
        ensureOpen();
        if (pos == 0) {
            throw new IOException("Push back buffer is full");
        }
        buf[--pos] = (byte)b;
    }

    public void unread(byte[] b, int off, int len) throws IOException {
        ensureOpen();
        if (len > pos) {
            throw new IOException("Push back buffer is full");
        }
        pos -= len;
        System.arraycopy(b, off, buf, pos, len);
    }

    public void unread(byte[] b) throws IOException {
        unread(b, 0, b.length);
    }

}
```

경우에 따라, 추가된 메서드가 기존의 인터페이스를 대체하기도 한다.
```java
// PrintStream의 경우, write()메서드보다는 상대적으로 print()나 println()메서드가 더 자주 쓰인다.
public class PrintStream extends FilterOutputStream implements Appendable, Closeable{
    public void print(boolean b) {
        write(String.valueOf(b));
    }

    public void print(char c) {
        write(String.valueOf(c));
    }

    public void print(int i) {
        write(String.valueOf(i));
    }

    public void print(long l) {
        write(String.valueOf(l));
    }

    ...

    public void println() {
        newLine();
    }

    public void println(boolean x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    private void newLine() {
        try {
            synchronized (this) {
                ensureOpen();
                textOut.newLine();
                textOut.flushBuffer();
                charOut.flushBuffer();
                if (autoFlush)
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }
}
```
